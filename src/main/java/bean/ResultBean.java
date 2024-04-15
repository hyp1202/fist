package bean;

import java.util.List;

public class ResultBean {

    String success;
    String msg;
    Object data;

    public ResultBean(String success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(List<DocBean> data) {
        this.data = data;
    }
}
