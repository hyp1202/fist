package bean;

import java.util.Objects;

/**
 * 课程链接
 */
public class CourseBean {

    public String id;
    public String name;
    public String roomId;
    public String memberId;
    public String status;//状态,0连接失败1连接成功
    public String type;//类型，0登录1不登陆

    public CourseBean(String id, String name, String roomId, String memberId, String status, String type) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.memberId = memberId;
        this.status = status;
        this.type = type;
    }

    public CourseBean() {
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object[] bean2Object(){
        Object[] objects = {this.getName(), this.getRoomId(), this.getMemberId(), "1".equals(this.getType()) ? "连接成功" : "连接失败"};
        return objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseBean that = (CourseBean) o;
        return Objects.equals(name, that.name) && Objects.equals(roomId, that.roomId) && Objects.equals(memberId, that.memberId) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, roomId, memberId, type);
    }
}
