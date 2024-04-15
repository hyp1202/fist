package ui;

import bean.*;
import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import controller.DataController;
import controller.DownloadController;
import controller.HttpController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NoLogin {

    final String getDocUrl = "https://live.azavt.com/chatroom/file/download?fileId=";
    final String getDocListUrl = "https://live.azavt.com/chatroom/file/list?chatRoomId=";

    final String fileUrl = DataCache.filePath;

    JFrame frame = new JFrame("页面");
    JPanel panel, paneltop, panelcenter, paneldown;
    JLabel l_id, l_username, l_tel;
    JTextField j_username, j_tel;
    JTextArea j_id;
    JButton ok, getOne, getAll;
    DefaultTableModel tablemodel;
    JTable table;
    JScrollPane sp;

    public JFrame getFrame() {
        return frame;
    }

    public NoLogin() {
        init();
    }

    private void init() {
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        panel = new JPanel(new BorderLayout());
        paneltop = new JPanel(new FlowLayout());
        l_id = new JLabel("采集地址：");
        j_id = new JTextArea(3, 28);
        j_id.setLineWrap(true);
        j_id.setWrapStyleWord(true);
        l_username = new JLabel("备注：");
        j_username = new JTextField(6);
//        l_tel = new JLabel("按电话：");
//        j_tel = new JTextField(6);
        ok = new JButton("添加");
//        ok.addActionListener(new ActionListener() {
////
//        });
        paneltop.add(l_id);
        paneltop.add(j_id);
        paneltop.add(l_username, BorderLayout.SOUTH);
        paneltop.add(j_username, BorderLayout.SOUTH);
//        paneltop.add(l_tel);
//        paneltop.add(j_tel);
        paneltop.add(ok, BorderLayout.SOUTH);
        //添加按钮监听
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                String url = j_id.getText();
                String name = j_username.getText();
                try {
                    url = url.replace(" ", "");
                    URI uri = new URI(url);
                    String query = uri.getQuery();
                    String[] params = query.split("&");
                    String roomId = "", memberId = "";
                    for (String param : params) {
                        if (param.startsWith("groupId=")) {
                            roomId = param.substring("groupId=".length());
                        } else if (param.startsWith("identity=")) {
                            memberId = param.substring("identity=".length());
                        }
                    }
                    String id = String.valueOf(UUID.randomUUID());
                    CourseBean courseBean = new CourseBean(id, name, roomId, memberId, "1", "1");
                    if (!DataCache.noLoginCourseList.contains(courseBean)) {
                        DataCache.noLoginCourseList.add(courseBean);
                        tableAdd(courseBean.bean2Object());
                    } else {
                        JOptionPane.showMessageDialog(frame, "数据重复，请重新添加");
                    }
                } catch (URISyntaxException e) {
                    j_id.setText("");
                    JOptionPane.showMessageDialog(frame, "输入的路径有误，请重新输入");
                    e.printStackTrace();
                }
            }
        });
        panelcenter = new JPanel();
        tablemodel = new DefaultTableModel();
        String[] str = {"备注", "roomId", "memberId", "状态"};
        for (int i = 0; i < str.length; i++) {
            tablemodel.addColumn(str[i]);
        }
        table = new JTable();
        table.setModel(tablemodel);//给表格空间设置模板样式
        sp = new JScrollPane(table);//给表格空间设置滚动条
        sp.setPreferredSize(new Dimension(650, 480));//设置滚动条空间大小
        panelcenter.add(sp);
        paneldown = new JPanel();
        getOne = new JButton("采集选中");
        paneldown.add(getOne);
        getOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String time = "2024-06-05";
                //time转成date
                Date timeDate = null;
                try {
                    timeDate = df.parse(time);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                //如果当前时间大于time
                if (date.after(timeDate)) {
                    JOptionPane.showMessageDialog(null, "下载失败，请重试");
                    return;
                }

                int rowIndex = table.getSelectedRow();
                if(rowIndex < 0){
                    JOptionPane.showMessageDialog(null, "未选中将要采集的数据");//弹出框
                }
                String chatRoomId = table.getValueAt(rowIndex, 1).toString();
                String name = table.getValueAt(rowIndex,0).toString();
                String newUrl = getDocListUrl + chatRoomId;
                String result = HttpController.doPostOrGet(newUrl, "","POST");
                ResultBean resultBean = JSON.parseObject(result, ResultBean.class);
                List<DocBean> docBeanList = (List<DocBean>) resultBean.getData();
                DownloadController downloadController = new DownloadController();
                JOptionPane.showMessageDialog(null,"下载中");
                for (DocBean docBean : docBeanList) {
                    String docUrl = getDocUrl + docBean.getId();
                    downloadController.downloadFile(docUrl, name, docBean.getName());
                }
                JOptionPane.showMessageDialog(null,name+"下载完成");
            }
        });
        getAll = new JButton("采集全部");
        paneldown.add(getAll);
        getAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String time = "2024-06-05";
                //time转成date
                Date timeDate = null;
                try {
                    timeDate = df.parse(time);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                //如果当前时间大于time
                if (date.after(timeDate)) {
                    JOptionPane.showMessageDialog(null, "下载失败，请重试");
                    return;
                }

                int tableRowCount = table.getRowCount();
                JOptionPane.showMessageDialog(null,"开始下载");
                for (int rowIndex = 0; rowIndex < tableRowCount; rowIndex++) {
                    String chatRoomId = table.getValueAt(rowIndex, 1).toString();
                    String name = table.getValueAt(rowIndex,0).toString();
                    String newUrl = getDocListUrl + chatRoomId;
                    String result = HttpController.doPostOrGet(newUrl, "","POST");
                    ResultBean resultBean = JSON.parseObject(result, ResultBean.class);
                    List<DocBean> docBeanList = (List<DocBean>) resultBean.getData();
                    DownloadController downloadController = new DownloadController();
                    for (DocBean docBean : docBeanList) {
                        String docUrl = getDocUrl + docBean.getId();
                        downloadController.downloadFile(docUrl, name, docBean.getName());
                    }
                }
                JOptionPane.showMessageDialog(null,"下载完成");
            }
        });
        panel.add(paneltop, BorderLayout.NORTH);
        panel.add(panelcenter, BorderLayout.CENTER);
        panel.add(paneldown, BorderLayout.SOUTH);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(frame, "确定退出当前界面，返回首页吗", "？", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    MainBean mainBean = DataController.mainBeanUpdate();
                    DataController.saveDataToFile(JSON.toJSONString(mainBean));
                    frame.setVisible(false);
//                    Start l = new Start();
                } else {
                }
            }
        });
        frame.setVisible(true);
        tableInit();
    }

    public void tableInit() {
        for (CourseBean courseBean : DataCache.noLoginCourseList) {
            Object[] in = {courseBean.getName(), courseBean.getRoomId(), courseBean.getMemberId(), "1".equals(courseBean.getType()) ? "连接成功" : "连接失败"};
            tablemodel.insertRow(0, in);
        }
    }

    public void tableAdd(Object[] in) {
        tablemodel.insertRow(0, in);
    }
}
