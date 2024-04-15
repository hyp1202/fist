package aiguquan.ui;

import aiguquan.bean.DingBot;
import aiguquan.bean.ForumBean;
import aiguquan.bean.MessageBean;
import aiguquan.dingding.util.DingTalkPushUtil;
import bean.MainBean;
import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import controller.DataController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//消息界面
public class DingBotUI {

    String forumId;
    String remake;

    JLabel l_name, l_appKey, l_appSecret, l_webhook,l_secret;

    JTextField j_name, j_appKey, j_appSecret, j_webhook,j_secret;

    JPanel panel, paneltop, panelcenter, paneldown;

    JScrollPane sp;

    DefaultTableModel dingBotTableModel;


    public DingBotUI() {
        // 初始化界面
        init();
    }

    public DingBotUI(String forumId) {
        this.forumId = forumId;
        init();
    }

    // 创建一个JFrame窗口
    public void init() {
        JFrame frame = new JFrame("机器人管理");
        // 初始化界面
        frame.setSize(1600, 660);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        panel = new JPanel(new BorderLayout());
        paneltop = new JPanel(new FlowLayout());

        l_name = new JLabel("机器人名称：");
        j_name = new JTextField(6);
        l_appKey = new JLabel("appKey：");
        j_appKey = new JTextField(12);
        l_appSecret = new JLabel("secret：");
        j_appSecret = new JTextField(24);
        l_webhook = new JLabel("机器人webhook：");
        j_webhook = new JTextField(24);
        l_secret = new JLabel("机器人secret：");
        j_secret = new JTextField(12);
        JButton add = new JButton("添加");

        paneltop.add(l_name);
        paneltop.add(j_name);
        paneltop.add(l_appKey);
        paneltop.add(j_appKey);
        paneltop.add(l_appSecret);
        paneltop.add(j_appSecret);
        paneltop.add(l_webhook);
        paneltop.add(j_webhook);
        paneltop.add(l_secret);
        paneltop.add(j_secret);
        paneltop.add(add);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加机器人
                String name = j_name.getText();
                String appKey = j_appKey.getText();
                String appSecret = j_appSecret.getText();
                String webhook = j_webhook.getText();
                String secret = j_secret.getText();
                if (name.isEmpty() || appKey.isEmpty() || appSecret.isEmpty() || webhook.isEmpty() || secret.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "请输入完整信息");
                    return;
                }
                DingBot dingBot;
                if (!DataCache.dingBotMap.isEmpty() && DataCache.dingBotMap.get(secret) != null) {
                    JOptionPane.showMessageDialog(frame, "数据重复，请重新添加");
                } else {
                    dingBot = new DingBot(name, appKey, appSecret, webhook,secret);
                    DataCache.dingBotMap.put(secret, dingBot);
                    tableAdd(dingBot.bean2Object());
                }

            }
        });

        panelcenter = new

                JPanel();

        dingBotTableModel = new

                DefaultTableModel();

        String[] str = {"名称", "appKey", "appSecret", "webhook","secret", "是否选中"};
        for (int i = 0; i < str.length; i++) {
            dingBotTableModel.addColumn(str[i]);
        }

        JTable table = new JTable(dingBotTableModel);
        table.setModel(dingBotTableModel);//给表格空间设置模板样式
        //让表格无法双击修改
        table.setDefaultEditor(Object.class, null);
        JCheckBox jCheckBox = new JCheckBox();
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                jCheckBox.setSelected(isSelected);
                jCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
                return jCheckBox;
            }
        });

        sp = new JScrollPane(table);//给表格空间设置滚动条
        sp.setPreferredSize(new

                Dimension(1500, 500));//设置滚动条空间大小
        panelcenter.add(sp);
        //删除按钮
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里处理删除事件
                int row = table.getSelectedRow();
                String secret = (String) table.getValueAt(row, 4);
                if (row >= 0) {
                    // 获取选中行的数据
                    Object value = table.getValueAt(row, 0);
                    System.out.println("删除的数据：" + value);
//                    DataCache.forumBeanMap.remove(value);
                    // 删除数据
                    dingBotTableModel.removeRow(row);
                    DataCache.dingBotMap.remove(secret);
                }

            }
        });
        //绑定按钮
        JButton bingButton = new JButton("绑定");
        bingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<DingBot> dingBotList = new ArrayList<>();
                int[] selectRows = table.getSelectedRows();
                for (int i = 0; i < selectRows.length; i++) {
                    DingBot dingBot = new DingBot();
                    dingBot.setAppKey((String) table.getValueAt(selectRows[i], 1));
                    dingBot.setName((String) table.getValueAt(selectRows[i], 0));
                    dingBot.setAppSecret((String) table.getValueAt(selectRows[i], 2));
                    dingBot.setWebhook((String) table.getValueAt(selectRows[i], 3));
                    dingBot.setSecret((String) table.getValueAt(selectRows[i], 4));
                    dingBotList.add(dingBot);
                }
                ForumBean forumBean = DataCache.forumBeanMap.get(forumId);
                forumBean.setDingBotList(dingBotList);
                MainBean mainBean = DataController.mainBeanUpdate();
                DataController.saveDataToFile(JSON.toJSONString(mainBean));
                JOptionPane.showMessageDialog(frame, "绑定成功");
            }
        });
        paneldown = new JPanel();
        paneldown.add(bingButton);
        paneldown.add(deleteButton);

        panel.add(paneltop, BorderLayout.NORTH);
        panel.add(panelcenter, BorderLayout.CENTER);
        panel.add(paneldown, BorderLayout.SOUTH);

        frame.add(panel);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(frame, "确定退出当前界面，返回首页吗", "？", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    frame.setVisible(false);
                    new NoLogin();
                } else {
                }
            }
        });
        frame.setVisible(true);
        tableInit();
//        ForumBean forumBean = DataCache.forumBeanMap.get(forumId);
//        List<DingBot> dingBotList = forumBean.getDingBotList();
//        for (int i = 0; i < table.getRowCount(); i++) {
//            String appKey = (String) table.getValueAt(i, 1);
//            if (dingBotList.stream().anyMatch(dingBot -> dingBot.getAppKey().equals(appKey))) {
//                table.setRowSelectionInterval(i, i);
//            }
//        }

    }

    public void tableInit() {
        if (DataCache.dingBotMap != null) {
            for (String key : DataCache.dingBotMap.keySet()) {
                Object[] in = DataCache.dingBotMap.get(key).bean2Object();
                dingBotTableModel.insertRow(0, in);
            }
        }
    }

    public void tableAdd(Object[] in) {
        dingBotTableModel.insertRow(0, in);
    }

}
