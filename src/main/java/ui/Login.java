package ui;

import bean.CourseBean;
import bean.DocBean;
import bean.ResultBean;
import bean.User;
import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import controller.DownloadController;
import controller.HttpController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.UUID;

public class Login {

	String checkCodeUrl = "https://live.dmtz.com.cn/ucenter/checkSmsCode";

	String loginUrl = "https://live.azavt.com/chatroom/new/login";
	String getCodeUrl = "https://live.dmtz.com.cn/ucenter/sendSms?isLogin=true&phoneNumber=";
	JFrame frame = new JFrame("搜索页面");
	JPanel panel,paneltop,panelcenter,paneldown;
	JLabel l_phone,l_code,l_tel;
	JTextField j_phone,j_code,j_tel;
	JButton getCode,JLogin,getOne,getAll;
	DefaultTableModel tablemodel;
	JTable table;
	JScrollPane sp;
	List<User> lists;
	public JFrame getFrame(){
		return frame;
	}
	public Login(){
		init();
	}
	private void init() {
		frame.setSize(700,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		panel = new JPanel(new BorderLayout());
		paneltop = new JPanel(new FlowLayout());
		l_phone = new JLabel("电话：");
		j_phone = new JTextField(18);
		l_code = new JLabel("验证码：");
		j_code = new JTextField(6);

		getCode = new JButton("获取验证码");
		getCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phone = j_phone.getText();
				String codeResult = HttpController.doPostOrGet(getCodeUrl+phone,"","GET");
			}
		});

		JLogin = new JButton("登录");
		JLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phone = j_phone.getText();
				String code = j_code.getText();
				String checkUrl = checkCodeUrl+"?phoneNumber="+phone+"&code="+code;
				String checkSmsCodeResult = HttpController.doPostOrGet(checkUrl,"","GET");
				String loginResult = HttpController.doPostOrGet(loginUrl+"?mobile="+phone,"","POST");
				ResultBean resultBean = JSON.parseObject(loginResult,ResultBean.class);

				String id = String.valueOf(UUID.randomUUID());
				String name = "";
				String roomId = "";
				String memberId = "";
				CourseBean courseBean = new CourseBean(id,name,roomId,memberId,"1","0");
				if (!DataCache.loginCourseList.contains(courseBean)){
					DataCache.loginCourseList.add(courseBean);
					tableAdd(courseBean.bean2Object());
				}
			}
		});
		
		paneltop.add(l_phone);
		paneltop.add(j_phone);
		paneltop.add(l_code);
		paneltop.add(j_code);
		paneltop.add(getCode);
		paneltop.add(JLogin);
		panelcenter = new JPanel();
		tablemodel = new DefaultTableModel();
		String[] str ={"备注","roomId","memberId","状态"};
		for(int i=0;i<str.length;i++){
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
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
//				if(rowIndex < 0){
//					JOptionPane.showMessageDialog(null, "未选中将要采集的数据");//弹出框
//				}
//				String chatRoomId = table.getValueAt(rowIndex, 1).toString();
//				String name = table.getValueAt(rowIndex,0).toString();
//				String newUrl = getDocListUrl + chatRoomId;
//				String result = HttpController.doPostOrGet(newUrl, "","POST");
//				ResultBean resultBean = JSON.parseObject(result, ResultBean.class);
//				List<DocBean> docBeanList = (List<DocBean>) resultBean.getData();
//				DownloadController downloadController = new DownloadController();
//				JOptionPane.showMessageDialog(null,"下载中");
//				for (DocBean docBean : docBeanList) {
//					String docUrl = getDocUrl + docBean.getId();
//					downloadController.downloadFile(docUrl, fileUrl+name, docBean.getName());
//				}
//				JOptionPane.showMessageDialog(null,name+"下载完成");
			}
		});
		getAll = new JButton("采集全部");
		paneldown.add(getAll);
		getAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		panel.add(paneltop,BorderLayout.NORTH);
		panel.add(panelcenter,BorderLayout.CENTER);
		panel.add(paneldown,BorderLayout.SOUTH);
		frame.add(panel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int i = JOptionPane.showConfirmDialog(frame, "确定退出管理员界面，返回登录界面吗", "？", JOptionPane.YES_NO_OPTION);
				if(i == JOptionPane.YES_OPTION){
					frame.setVisible(false);
					Start l = new Start();
				} else {
				}
			}
		});
		frame.setVisible(true);
		tableInit();
	}

	public void tableInit() {
		for (CourseBean courseBean : DataCache.loginCourseList) {
			Object[] in = {courseBean.getName(), courseBean.getRoomId(), courseBean.getMemberId(), "1".equals(courseBean.getType()) ? "连接成功" : "连接失败"};
			tablemodel.insertRow(0, in);
		}
	}

	public void tableAdd(Object[] in) {
		tablemodel.insertRow(0, in);
	}
}
