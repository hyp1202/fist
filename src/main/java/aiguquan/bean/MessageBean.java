package aiguquan.bean;

import bean.DocBean;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MessageBean {

    public int id;
    public String content;//消息主体
    public String image;//图片路径
    public List<DocBean> docBeans;//附件集合
    public String video;//视频
    public String voice;//语音
    public String forumId;//圈子id

    public String createTime;//创建时间

    public boolean isSend;//是否发送过

    public MessageBean(int id, String content, String image, List<DocBean> docBeans, String video, String voice, String forumId, String createTime, boolean isSend) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.docBeans = docBeans;
        this.video = video;
        this.voice = voice;
        this.forumId = forumId;
        this.createTime = createTime;
        this.isSend = isSend;
    }

    public MessageBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<DocBean> getDocBeans() {
        return docBeans;
    }

    public void setDocBeans(List<DocBean> docBeans) {
        this.docBeans = docBeans;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object[] bean2Object(){
        String docNames = (getDocBeans() == null || getDocBeans().isEmpty()) ? "" :getDocBeans().stream().map(docBean -> docBean.getFileName()).collect(Collectors.joining(","));
        Object[] objects = {this.getId(),this.isSend(),this.getContent(),this.getImage(),docNames,this.getCreateTime()};
        return objects;
    }
}
