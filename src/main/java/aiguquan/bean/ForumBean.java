package aiguquan.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 圈子对象
 */
public class ForumBean {

    public String id;
    public LinkedList<MessageBean> messageBeanList;//消息对象列表

    public String[] KeywordsFilter;//关键字过滤

    public String WatermarkContent;//水印

    public String remake;//备注

    public String appKey;

    public String appSecret;

    public String type;//链接状态

    public LinkedList<MessageBean>  filterMessage;

    public List<DingBot> dingBotList;//钉钉机器人

    public List<ChatBean> chatBeanList;//聊天室内容

    public String isGetChat;//是否获取聊天室内容

    public String[] validUserName;//聊天室有效用户名

    public ForumBean(String id, LinkedList<MessageBean> messageBeanList, String[] keywordsFilter, String watermarkContent, String remake, String appKey, String appSecret, String type, LinkedList<MessageBean> filterMessage, List<DingBot> dingBotList,List<ChatBean> chatBeanList,String isGetChat,String[] validUserName) {
        this.id = id;
        this.messageBeanList = messageBeanList;
        KeywordsFilter = keywordsFilter;
        WatermarkContent = watermarkContent;
        this.remake = remake;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.type = type;
        this.filterMessage = filterMessage;
        this.dingBotList = dingBotList;
        this.chatBeanList = chatBeanList;
        this.isGetChat = isGetChat;
        this.validUserName = validUserName;
    }

    public ForumBean(String id, LinkedList<MessageBean> messageBeanList, String[] keywordsFilter, String watermarkContent, String remake,String[] validUserName) {
        this.id = id;
        this.messageBeanList = messageBeanList;
        KeywordsFilter = keywordsFilter;
        WatermarkContent = watermarkContent;
        this.remake = remake;
        validUserName = validUserName;
    }

    public ForumBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<MessageBean> getMessageBeanList() {
        return messageBeanList;
    }

    public void setMessageBeanList(LinkedList<MessageBean> messageBeanList) {
        this.messageBeanList = messageBeanList;
    }

    public String[] getKeywordsFilter() {
        return KeywordsFilter;
    }

    public void setKeywordsFilter(String[] keywordsFilter) {
        KeywordsFilter = keywordsFilter;
    }

    public String getWatermarkContent() {
        return WatermarkContent;
    }

    public void setWatermarkContent(String watermarkContent) {
        WatermarkContent = watermarkContent;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinkedList<MessageBean> getFilterMessage() {
        return filterMessage;
    }

    public void setFilterMessage(LinkedList<MessageBean> filterMessage) {
        this.filterMessage = filterMessage;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public List<DingBot> getDingBotList() {
        return dingBotList;
    }

    public void setDingBotList(List<DingBot> dingBotList) {
        this.dingBotList = dingBotList;
    }

    public List<ChatBean> getChatBeanList() {
        return chatBeanList;
    }

    public void setChatBeanList(List<ChatBean> chatBeanList) {
        this.chatBeanList = chatBeanList;
    }

    public String getIsGetChat() {
        return isGetChat;
    }

    public void setIsGetChat(String isGetChat) {
        this.isGetChat = isGetChat;
    }

    public String[] getValidUserName() {
        return validUserName;
    }

    public void setValidUserName(String[] validUserName) {
        this.validUserName = validUserName;
    }

    public Object[] bean2Object(){
        String keyWords = this.getKeywordsFilter() == null ? "" : String.join(",",this.getKeywordsFilter());
        String validUserName = this.getValidUserName() == null ? "" : String.join(",",this.getValidUserName());
        String typeStr = "0".equals(this.getType()) ? "未连接" : "1".equals(this.getType()) ? "连接成功" : "连接失败";
        String botNames = "";
        if (getDingBotList() != null && !getDingBotList().isEmpty()) {
            botNames = getDingBotList().stream()
                    .map(DingBot::getName)
                    .collect(Collectors.joining(","));
        }
        Object[] objects = {this.getId(), this.getRemake(), botNames,keyWords, this.getWatermarkContent(), typeStr,validUserName};
        return objects;
    }


}
