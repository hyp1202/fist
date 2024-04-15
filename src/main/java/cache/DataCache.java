package cache;

import aiguquan.bean.DingBot;
import aiguquan.bean.ForumBean;
import bean.CourseBean;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.task.support.ExecutorServiceAdapter;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataCache {

    public static Map<String, CourseBean> loginCourseBean = new HashMap<>();

    public static Map<String,CourseBean> noLoginCourseBean = new HashMap<>();

    public static List<CourseBean> loginCourseList = new ArrayList<>();

    public static List<CourseBean> noLoginCourseList = new ArrayList<>();

    public static String systemInformation;

    public static String filePath;

    public static Map<String, ForumBean> forumBeanMap = new HashMap<>();

    public static Map<String,DingBot> dingBotMap = new HashMap<>();

    public static Map<String,JSONObject> accessTokenMap = new HashMap<>();

    public static ExecutorService executor = Executors.newCachedThreadPool();

}
