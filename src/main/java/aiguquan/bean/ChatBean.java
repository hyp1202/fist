package aiguquan.bean;

public class ChatBean {

    public int id;

    public String content;//聊天内容

    public  String time;//聊天时间

    public String userName;//用户名

    public String forumId;//圈子id

    public boolean isSend;//是否发送过

    public ChatBean() {
    }

    public ChatBean(int id, String content, String time, String userName, String forumId, boolean isSend) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.userName = userName;
        this.forumId = forumId;
        this.isSend = isSend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
