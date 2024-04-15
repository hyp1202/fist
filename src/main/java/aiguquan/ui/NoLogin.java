package aiguquan.ui;

import aiguquan.bean.ForumBean;
import aiguquan.bean.MessageBean;
import aiguquan.webSocket.JavaClient;
import bean.DocBean;
import bean.MainBean;
import bean.ResultBean;
import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import controller.DataController;
import controller.DownloadController;
import controller.HttpController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoLogin {

    final String getDocUrl = "https://live.azavt.com/chatroom/file/download?fileId=";
    final String getDocListUrl = "https://live.azavt.com/chatroom/file/list?chatRoomId=";

    final String fileUrl = DataCache.filePath;

    JFrame frame = new JFrame("页面");
    JPanel panel, paneltop, panelcenter, paneldown,panel_top_south,panel_top_north;
    JLabel l_id, l_username, l_tel,l_appKey,l_appSecret;
    JTextField j_username, j_id,j_appKey,j_appSecret;
    static JButton ok, getOne, getAll;
    static DefaultTableModel tablemodel;
    JTable table;
    JScrollPane sp;

    public JFrame getFrame() {
        return frame;
    }

    public NoLogin() {
        init();
    }

    private void init() {
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        panel = new JPanel(new BorderLayout());
        paneltop = new JPanel(new BorderLayout());
        panel_top_north = new JPanel(new FlowLayout());
        panel_top_south = new JPanel(new FlowLayout());
        l_id = new JLabel("直播间id：");
        j_id = new JTextField(6);
        l_username = new JLabel("备注：");
        j_username = new JTextField(6);
        l_appKey = new JLabel("机器人appKey：");
        j_appKey = new JTextField(24);
        l_appSecret = new JLabel("机器人appSecret：");
        j_appSecret = new JTextField(24);
        ok = new JButton("添加");
        panel_top_north.add(l_id);
        panel_top_north.add(j_id);
        panel_top_north.add(l_username);
        panel_top_north.add(j_username);
//        panel_top_north.add(l_appKey);
//        panel_top_north.add(j_appKey);
//        panel_top_north.add(l_appSecret);
//        panel_top_north.add(j_appSecret);
        panel_top_north.add(ok);
        JLabel  label = new JLabel("（操作说明：表格内容可双击修改。右键可查看详细消息。过滤词可用输入多个内容，用英文逗号隔开。例如：例子1,例子2,例子3）");
        //设置label字体为红色
        label.setForeground(Color.red);
        panel_top_south.add(label);
        paneltop.add(panel_top_south, BorderLayout.SOUTH);
        paneltop.add(panel_top_north, BorderLayout.NORTH);

        //添加按钮监听
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                String forumId = j_id.getText();
                String remake = j_username.getText();
//                String appKey = j_appKey.getText();
//                String appSecret = j_appSecret.getText();
                if (forumId.equals("") || remake.equals("") ) {
                    JOptionPane.showMessageDialog(null, "请输入完整信息");
                    return;
                }
                //添加table
                ForumBean forumBean = new ForumBean(forumId, new LinkedList<MessageBean>(), new String[0], "", remake,new String[0]);
                if (DataCache.forumBeanMap.get(forumBean.getId()) == null) {
                    DataCache.forumBeanMap.put(forumBean.getId(), forumBean);
                    tableAdd(forumBean.bean2Object());
                } else {
                    JOptionPane.showMessageDialog(frame, "数据重复，请重新添加");
                }
            }
        });
        panelcenter = new JPanel();
        tablemodel = new DefaultTableModel();
        String[] str = {"直播间id", "备注","绑定机器人", "过滤词", "水印内容", "状态","聊天室采集目标名称"};
        for (int i = 0; i < str.length; i++) {
            tablemodel.addColumn(str[i]);
        }
        table = new JTable();

        table.setSelectionMode (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        table.setModel(tablemodel);//给表格空间设置模板样式
        //让表格无法双击修改
        table.setDefaultEditor(Object.class, null);
        //让表格的第三列无法编辑和最后一列无法编辑，其他列可编辑
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
        table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()));
        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()));
//        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()));
        sp = new JScrollPane(table);//给表格空间设置滚动条
        sp.setPreferredSize(new Dimension(1100, 500));//设置滚动条空间大小
        panelcenter.add(sp);

        //设置右键菜单
        JPopupMenu menu = new JPopupMenu();
        JMenuItem checkMessage = new JMenuItem("查看消息详情");
        JMenuItem delete = new JMenuItem("删除");
        menu.add(checkMessage);
        menu.add(delete);

        // 为JTable对象添加一个鼠标监听器，当检测到右键点击时，调用JPopupMenu的show方法，将菜单显示在鼠标位置
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (row >= 0 && column >= 0) {
                        table.setRowSelectionInterval(row, row);
                    }
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
                if(e.getClickCount() == 2) {
                    if(row != -1) {
                        if (column == 2) {
                            String id = (String) table.getValueAt(row, 0);
                            ForumBean forumBean = DataCache.forumBeanMap.get(id);
                            if (forumBean != null) {
                                //弹出消息
//                                JOptionPane.showMessageDialog(null, "双击事件");
                                String forumId = (String) table.getValueAt(row, 0);
                                new DingBotUI(forumId);
                                frame.setVisible(false);
                            }
                        }
                    }
                }
            }
        });

        // 菜单项的点击事件
        checkMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // 在这里处理查看消息详情的事件
                int row = table.getSelectedRow();
                // 获取id和备注
                String forumId = (String) table.getValueAt(row, 0);
                String remark = (String) table.getValueAt(row, 1);
                frame.setVisible(false);
                // 创建MessageUI对象并显示
                new MessageUI(forumId,remark);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // 在这里处理删除事件
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // 获取选中行的数据
                    Object value = table.getValueAt(row, 0);
                    System.out.println("删除的数据：" + value);
                    DataCache.forumBeanMap.remove(value);
                    // 删除数据
                    tablemodel.removeRow(row);
                }
            }
        });

        paneldown = new JPanel();
//        getOne = new JButton("采集选中");
//        paneldown.add(getOne);
//        getOne.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                int rowIndex = table.getSelectedRow();
//                if (rowIndex < 0) {
//                    JOptionPane.showMessageDialog(null, "未选中将要采集的数据");//弹出框
//                }
//                String chatRoomId = table.getValueAt(rowIndex, 1).toString();
//                String name = table.getValueAt(rowIndex, 0).toString();
//                String newUrl = getDocListUrl + chatRoomId;
//                String result = HttpController.doPostOrGet(newUrl, "", "POST");
//                ResultBean resultBean = JSON.parseObject(result, ResultBean.class);
//                List<DocBean> docBeanList = (List<DocBean>) resultBean.getData();
//                DownloadController downloadController = new DownloadController();
//                JOptionPane.showMessageDialog(null, "下载中");
//                for (DocBean docBean : docBeanList) {
//                    String docUrl = getDocUrl + docBean.getId();
//                    downloadController.downloadFile(docUrl, name, docBean.getName());
//                }
//                JOptionPane.showMessageDialog(null, name + "下载完成");
//            }
//        });
        getAll = new JButton("开始采集");
        paneldown.add(getAll);
        getAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tableRowCount = table.getRowCount();
                // 创建一个可缓存的线程池
//                DataCache.executor.shutdown();
//                DataCache.executor = Executors.newCachedThreadPool();
                // 定义一个线程任务，模拟出现异常的情况
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
                    }
                };
                for (int rowIndex = 0; rowIndex < tableRowCount; rowIndex++) {
                    int finalRowIndex = rowIndex;
                    if (!"连接成功".equals(table.getValueAt(finalRowIndex, 5).toString())) {
                        Thread t1 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
                                String id = table.getValueAt(finalRowIndex, 0).toString();
                                JavaClient javaClient = new JavaClient();
                                javaClient.clientWebSocket(id);
//                                JavaClient javaClient1 = new JavaClient();
//                                javaClient1.clientWebSocket1(id);
                                // 模拟出现异常，抛出一个运行时异常
//                                throw new RuntimeException("线程" + Thread.currentThread().getName() + "出现异常");
                            }
                        });
                        Thread t2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("线程" + Thread.currentThread().getName() + "开始执行");
                                String id = table.getValueAt(finalRowIndex, 0).toString();
//                                JavaClient javaClient = new JavaClient();
//                                javaClient.clientWebSocket(id);
                                JavaClient javaClient1 = new JavaClient();
                                javaClient1.clientWebSocket1(id);
                                // 模拟出现异常，抛出一个运行时异常
//                                throw new RuntimeException("线程" + Thread.currentThread().getName() + "出现异常");
                            }
                        });
                        DataCache.executor.submit(t1);
                        DataCache.executor.submit(t2);
                    }
                }
                // 关闭线程池
//                DataCache.executor.shutdown();
            }
        });
        panel.add(paneltop, BorderLayout.NORTH);
        panel.add(panelcenter, BorderLayout.CENTER);
        panel.add(paneldown, BorderLayout.SOUTH);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(frame, "确定退出系统吗", "？", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    MainBean mainBean = DataController.mainBeanUpdate();
                    DataController.saveDataToFile(JSON.toJSONString(mainBean));
                    frame.setVisible(false);
                    System.exit(0);
                } else {
                    return;
                }
            }
        });
        frame.setVisible(true);
        tableInit();
        //监听表格修改事件
        table.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                // 判断是否是单元格编辑事件
                if ("tableCellEditor".equals(e.getPropertyName())) {
                    // 判断是否是编辑结束
                    if (!table.isEditing()) {
                        // 获取变化的行、列
                        int row = table.getEditingRow();
                        int col = table.getEditingColumn();
                        // 获取变化前的数据
                        Object oldValue = e.getOldValue();
                        // 获取变化后的数据
                        Object newValue = table.getValueAt(row, col);
                        // 根据不同的数据做出相应的处理
                        if (!Objects.equals(oldValue, newValue)) {
                            // 数据发生了变化
                            String id = (String) table.getValueAt(row, 0);
                            ForumBean forumBean = DataCache.forumBeanMap.get(id);
                            forumBean.setRemake((String) table.getValueAt(row, 1));
                            forumBean.setKeywordsFilter(((String) table.getValueAt(row, 3)).split(","));
                            forumBean.setWatermarkContent((String) table.getValueAt(row, 4));
                            forumBean.setValidUserName(((String) table.getValueAt(row, 6)).split(","));
                            DataCache.forumBeanMap.put(id,forumBean);
                        }
                    }
                }
            }
        });
    }

    public static void tableInit() {
        if (DataCache.forumBeanMap != null && DataCache.forumBeanMap.size() > 0) {
            for (String key : DataCache.forumBeanMap.keySet()) {
                ForumBean forumBean = DataCache.forumBeanMap.get(key);
                Object[] in = forumBean.bean2Object();
                tablemodel.insertRow(0, in);
            }
        }
    }
    //更新表格字段
    public static synchronized void tableUpdate(String id,int column,Object value) {
        int rowCount = tablemodel.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            String rowId = (String) tablemodel.getValueAt(row,0);
            if (rowId.equals(id)) {
                tablemodel.setValueAt(value, row, column);
            }
        }
    }

    public void tableAdd(Object[] in) {
        tablemodel.insertRow(0, in);
    }

    public static void clickGetAll() {
        getAll.doClick();
    }
}
