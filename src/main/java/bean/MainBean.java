package bean;

import aiguquan.bean.DingBot;
import aiguquan.bean.ForumBean;

import java.util.List;
import java.util.Map;

public class MainBean {

    public List<CourseBean> courseBeans;//已下载课程列表

    public String filePath;//文件保存路径

    public String systemInformation;//系统信息

    public Map<String, ForumBean> forumBeans;//圈子列表

    public Map<String, DingBot> dingBotMap;//机器人列表

    public MainBean(List<CourseBean> courseBeans, String filePath) {
        this.courseBeans = courseBeans;
        this.filePath = filePath;
    }

    public MainBean() {
    }

    public List<CourseBean> getCourseBeans() {
        return courseBeans;
    }

    public void setCourseBeans(List<CourseBean> courseBeans) {
        this.courseBeans = courseBeans;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSystemInformation() {
        return systemInformation;
    }

    public void setSystemInformation(String systemInformation) {
        this.systemInformation = systemInformation;
    }

    public Map<String, ForumBean> getForumBeans() {
        return forumBeans;
    }

    public void setForumBeans(Map<String, ForumBean> forumBeans) {
        this.forumBeans = forumBeans;
    }

    public Map<String, DingBot> getDingBotMap() {
        return dingBotMap;
    }

    public void setDingBotMap(Map<String, DingBot> dingBotMap) {
        this.dingBotMap = dingBotMap;
    }
}
