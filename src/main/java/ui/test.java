package ui;

import com.alibaba.fastjson2.JSONObject;
import org.java_websocket.client.WebSocketClient;
import aiguquan.webSocket.JavaClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class test {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public test() {
        prepareGUI();
    }

    public static void main(String[] args) {
//        test swingControlDemo = new test();
//        swingControlDemo.showProgressBarDemo();
//        WebSocketClient client = JavaClient.getClient("wss://im.stock-ring.com/ws/desktop_push");
//        if (client != null) {
//            int i = 0;
//            while (true) {
//                JSONObject jsonObject = new JSONObject();
//                if (i == 0) {
//                    jsonObject.put("kind", "subscribe");
//                    jsonObject.put("data", "private_letter_140494,refresh_private_letter_list_140494,forum_662");
//                } else {
//                    jsonObject.put("kind", "ping");
//                }
//                System.out.println(new Date() + "," + jsonObject.toJSONString());
//                client.send(jsonObject.toJSONString());
//                i++;
//                try {
//                    Thread.sleep(20000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        // 创建一个DefaultTableModel对象，指定列名和初始数据
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
                {"Alice", 20},
                {"Bob", 25},
                {"Charlie", 30}
        }, new Object[]{"Name", "Age"});

        // 创建一个JTable对象，并将DefaultTableModel作为参数传递给它的构造函数
        JTable table = new JTable(model);

        // 创建一个JPopupMenu对象，并添加两个菜单项
        JPopupMenu menu = new JPopupMenu();
        JMenuItem add = new JMenuItem("Add");
        JMenuItem delete = new JMenuItem("Delete");
        menu.add(add);
        menu.add(delete);

        // 为JTable对象添加一个鼠标监听器，当检测到右键点击时，调用JPopupMenu的show方法，将菜单显示在鼠标位置
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                    menu.show(table, e.getX(), e.getY());
                }
            }
        });

        // 为JPopupMenu的菜单项添加相应的动作监听器，实现添加和删除行的功能
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 添加一行空数据到表格末尾
                model.addRow(new Object[]{"", ""});
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 删除表格中选中的行
                int row = table.getSelectedRow();
                if (row != -1) {
                    model.removeRow(row);
                }
            }
        });

        // 创建一个JFrame对象，将JTable对象放入一个JScrollPane中，并添加到JFrame中
        JFrame frame = new JFrame("Example");
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private void prepareGUI() {
        mainFrame = new JFrame("Swing显示标准进度条示例");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private JProgressBar progressBar;
    private Task task;
    private JButton startButton;
    private JTextArea outputTextArea;

    private void showProgressBarDemo() {
        headerLabel.setText("Control in action: JProgressBar");
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        startButton = new JButton("开始...");
        outputTextArea = new JTextArea("", 5, 20);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task = new Task();
                task.start();
            }
        });
        controlPanel.add(startButton);
        controlPanel.add(progressBar);
        controlPanel.add(scrollPane);
        mainFrame.setVisible(true);
    }

    private class Task extends Thread {
        public Task() {
        }

        public void run() {
            for (int i = 0; i <= 100; i += 10) {
                final int progress = i;

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {

                        progressBar.setValue(progress);
                        outputTextArea.setText(
                                outputTextArea.getText() + String.format("任务已完成 %d%% .\n", progress));
                    }
                });
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
            }
        }
    }


}
