package controller;

import bean.CourseBean;
import bean.MainBean;
import cache.DataCache;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DataController {

    final

    public void test(JFrame frame){
//        saveDataToFile("save","aaa");
        JOptionPane.showMessageDialog(frame, "恭喜您数据采集完成");
    }

    public void get(JFrame frame){
//        String s = getDatafromFile("save");
//        JOptionPane.showMessageDialog(frame, s+"bbb");
    }

    public static void saveDataToFile(String data) {
        BufferedWriter writer = null;
        File file = new File("data.json");
        //如果文件不存在，则新建一个
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("文件写入成功！");
    }

    public static  String getDataFromFile() {

        String Path="data.json";
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static MainBean mainBeanUpdate(){
        MainBean mainBean = new MainBean();
        List<CourseBean> courseBeans = new ArrayList<>();
        courseBeans.addAll(DataCache.loginCourseList);
        courseBeans.addAll(DataCache.noLoginCourseList);
        mainBean.setCourseBeans(courseBeans);
        mainBean.setFilePath(DataCache.filePath);
        mainBean.setSystemInformation(DataCache.systemInformation);
        mainBean.setForumBeans(DataCache.forumBeanMap);
        mainBean.setDingBotMap(DataCache.dingBotMap);
        return mainBean;
    }
}
