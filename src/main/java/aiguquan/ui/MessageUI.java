package aiguquan.ui;

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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//消息界面
public class MessageUI {

    String forumId;
    String remake;

    JPanel panel, paneltop, panelcenter, paneldown;

    JScrollPane sp;

    static DefaultTableModel messageTableModel;


    public MessageUI() {
        // 初始化界面
        init();
    }

    public MessageUI(String forumId, String remake) {
        this.forumId = forumId;
        this.remake = remake;
        init();
    }

    // 创建一个JFrame窗口
    public void init() {
        JFrame frame = new JFrame(forumId + "，" + remake + "的消息");
        // 初始化界面
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        panel = new JPanel(new BorderLayout());
        paneltop = new JPanel(new FlowLayout());
        panelcenter = new JPanel();
        messageTableModel = new DefaultTableModel();
        String[] str = {"id", "是否已发送", "消息内容", "图片","文件", "创建时间", "是否选中"};
        for (int i = 0; i < str.length; i++) {
            messageTableModel.addColumn(str[i]);
        }
        JTable table = new JTable(messageTableModel);
        table.setModel(messageTableModel);//给表格空间设置模板样式
        //让表格无法双击修改
        table.setDefaultEditor(Object.class, null);
//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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
        sp.setPreferredSize(new Dimension(1100, 600));//设置滚动条空间大小
        panelcenter.add(sp);

        paneldown = new JPanel();
        JButton btn = new JButton("发送");
        paneldown.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                List<MessageBean> messageBeans = new LinkedList<>();
                Map<String, MessageBean> messageBeanMap = new HashMap<>();
                for (MessageBean bean : DataCache.forumBeanMap.get(forumId).getMessageBeanList()) {
                    if (messageBeanMap.put(String.valueOf(bean.getId()), bean) != null) {
                        throw new IllegalStateException("Duplicate key");
                    }
                }
                for (int i = 0; i < rows.length; i++) {
                    String id = (String.valueOf(table.getValueAt(rows[i], 0)));
//                    MessageBean messageBean = new MessageBean();
//                    messageBean.setId((Integer) table.getValueAt(rows[i], 0));
//                    messageBean.setContent((String) table.getValueAt(rows[i], 2));
//                    messageBean.setImage((String) table.getValueAt(rows[i], 3));
//                    String docNames = (String) table.getValueAt(rows[i], 4);

                    messageBeans.add(messageBeanMap.get(id));
                }
                DingTalkPushUtil dingTalkPushUtil = new DingTalkPushUtil();
                try {
                    dingTalkPushUtil.pushMessageList(messageBeans, forumId);
                    JOptionPane.showMessageDialog(frame, "发送成功");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "发送失败");
                }
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
                    frame.setVisible(false);
                    NoLogin noLogin = new NoLogin();
                } else {
                }
            }
        });
        frame.setVisible(true);

        tableInit();
    }

    public void tableInit() {
        ForumBean forumBean = DataCache.forumBeanMap.get(forumId);
        LinkedList<MessageBean> messageBeans = forumBean.getMessageBeanList();
        for (MessageBean messageBean : messageBeans) {
            Object[] in = messageBean.bean2Object();
            messageTableModel.insertRow(0, in);
        }
    }

    public static void tableUpdate(int id,int column,Object value) {
        if (messageTableModel != null) {
            int rowCount = messageTableModel.getRowCount();
            for (int row = 0; row < rowCount; row++) {
                int rowId = (int) messageTableModel.getValueAt(row, 0);
                if (rowId == id) {
                    messageTableModel.setValueAt(value, row, column);
                }
            }
        }
    }

}
