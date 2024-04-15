package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始页面
 */
public class Start {

    public Start(){
        final JFrame frame = new JFrame("数据采集");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); //居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        frame.add(panel);

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel);
        JButton login = new JButton("登录");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Login login1 = new Login();
            }
        });
        JButton reset = new JButton("不登陆");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                NoLogin noLogin = new NoLogin();
            }
        });
        buttonPanel.add(login);
        buttonPanel.add(reset);
        frame.setVisible(true);
    }
}
