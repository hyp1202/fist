
import aiguquan.ui.NoLogin;
import bean.CourseBean;
import bean.MainBean;
import cache.DataCache;
import com.alibaba.fastjson2.JSON;
import controller.DataController;
//import ui.NoLogin;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JFrameApplication {

    public static void main(String[] args) {

        String path = System.getProperty("exe.path");
        File file = new File("data.json");
        //如果文件不存在，则新建一个
        if(!file.exists()){
            MainBean mainBean = new MainBean();
            mainBean.setSystemInformation("数据采集");
            mainBean.setFilePath(path);
            DataCache.filePath = path;
            DataController.saveDataToFile(JSON.toJSONString(mainBean));
        }else {
            String dataStr = DataController.getDataFromFile();
            MainBean mainBean = JSON.parseObject(dataStr, MainBean.class);
            List<CourseBean> noLoginCourse = new ArrayList<>();
            List<CourseBean> loginCourse = new ArrayList<>();
            if (mainBean.getCourseBeans() != null && (!mainBean.getCourseBeans().isEmpty()) ) {
                noLoginCourse.addAll(mainBean.getCourseBeans().stream()
                        .filter((CourseBean b) -> "1".equals(b.getType()))
                        .collect(Collectors.toList()));
                loginCourse.addAll(mainBean.getCourseBeans().stream()
                        .filter((CourseBean b) -> "0".equals(b.getType()))
                        .collect(Collectors.toList()));
            }

            DataCache.loginCourseList = new ArrayList<>(loginCourse);
            DataCache.noLoginCourseList = new ArrayList<>(noLoginCourse);
            DataCache.filePath = mainBean.filePath;
            DataCache.systemInformation = mainBean.systemInformation;
            DataCache.forumBeanMap = mainBean.forumBeans == null ? new LinkedHashMap<>() : mainBean.forumBeans;
            DataCache.dingBotMap = mainBean.dingBotMap == null ? new LinkedHashMap<>() : mainBean.dingBotMap;;

        }
        NoLogin l = new NoLogin();
    }
}
