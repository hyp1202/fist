package bean;

public class DocBean {
    String id;
    String name;
    String extension;
    String chatroomId;
    String createTime;
    String fileName;

    String link;

    public DocBean(String id, String name, String extension, String chatroomId, String createTime, String fileName) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.chatroomId = chatroomId;
        this.createTime = createTime;
        this.fileName = fileName;
    }

    public DocBean(String id, String name, String extension, String chatroomId, String createTime, String fileName, String link) {
        this.id = id;
        this.name = name;
        this.extension = extension;
        this.chatroomId = chatroomId;
        this.createTime = createTime;
        this.fileName = fileName;
        this.link = link;
    }

    public DocBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
