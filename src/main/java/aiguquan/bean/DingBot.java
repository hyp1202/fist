package aiguquan.bean;

public class DingBot {
    public String appKey;
    public String appSecret;
    public String name;
    public String webhook;

    public String secret;

    public DingBot(String appKey, String appSecret, String name, String webhook,String secret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.name = name;
        this.webhook = webhook;
        this.secret = secret;
    }

    public DingBot() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebhook() {
        return webhook;
    }

    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public Object[] bean2Object(){
        Object[] objects = {this.getAppKey(),this.getAppSecret(),this.getName(),this.getWebhook(),this.getSecret()};
        return objects;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
