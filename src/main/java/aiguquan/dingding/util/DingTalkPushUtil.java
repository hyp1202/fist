package aiguquan.dingding.util;

import aiguquan.bean.DingBot;
import aiguquan.bean.ForumBean;
import aiguquan.bean.MessageBean;
import aiguquan.service.MessageService;
import aiguquan.service.WatermarkService;
import bean.DocBean;
import cache.DataCache;
import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import controller.DownloadController;
import org.apache.commons.codec.binary.Base64;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 钉钉推送工具类
 */
@Async
@Component
public class DingTalkPushUtil {

    /**
     * 按照钉钉API处理内容格式
     *
     * @param content
     */
    public void pushText(String content, String webhook, String secret) {
        try {
            DingTalkClient client = pushOutBot(webhook,secret );
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("text");
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(content);
            request.setText(text);
            OapiRobotSendResponse response = client.execute(request);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
//        MessageText message = new MessageText();
//        MessageContent messageContent = new MessageContent();
//        message.setMsgtype("text");
//        messageContent.setContent(content);
//        message.setText(messageContent);
//        push(message,webhook);
    }

    /**
     * 推送消息
     *
     * @param obj
     */
    private void push(Object obj, String webhook) {
        try {
//            webhook = "https://oapi.dingtalk.com/robot/send?access_token=ec2ea3730be73ede31e4f095638cf7c6d1db1bfd231f79beb693836110543893";
            //自定义钉钉机器人生成链接  access_token钉钉自动生成
            URL url = new URL(webhook);
            //打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // Post 请求不能使用缓存
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String jsonMessage = JSONObject.toJSONString(obj);
            byte[] data = jsonMessage.getBytes();
            // 发送请求参数
            out.write(data);
            // flush输出流的缓冲
            out.flush();
            out.close();
            //开始获取数据
            InputStream in = conn.getInputStream();
            byte[] content = new byte[in.available()];
            in.read(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pushImage(String webhook, String appKey, String appSecret, List<String> imagePaths, String secret,String content) throws Exception {
//        webhook = "https://oapi.dingtalk.com/robot/send?access_token=ec2ea3730be73ede31e4f095638cf7c6d1db1bfd231f79beb693836110543893";
        // 创建一个DingTalkClient对象
        DingTalkClient client = pushOutBot(webhook, secret);
        // 创建一个OapiRobotSendRequest对象，用来设置消息的内容和类型
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 设置消息类型为markdown
        request.setMsgtype("markdown");
        // 创建一个OapiRobotSendRequest.Markdown对象，用来设置markdown消息的标题和文本
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        // 钉钉机器人的安全签名，需要替换成你自己的

        Sample sample = new Sample();

        String token = sample.getAccessToken(appKey, appSecret);
        StringBuilder contentBuilder = new StringBuilder(content);
        for (String imagePath:imagePaths) {
            String mediaId = uploadImage.uploadImage(imagePath, token, "image");
            contentBuilder.append("![screenshot](").append(mediaId).append(")");
        }
        content = contentBuilder.toString();
        // 调用钉钉的媒体文件上传接口，上传本地图片，获取media_id

        // 设置标题
        markdown.setTitle("图片消息");
        // 设置文本，使用!alt text的语法来引用图片
        markdown.setText(content);
        // 将markdown对象设置到request对象中
        request.setMarkdown(markdown);
        // 调用client的execute方法，发送消息，并获取响应
        OapiRobotSendResponse response = client.execute(request);
        // 打印响应的结果
        System.out.println(response.getErrcode());
        System.out.println(response.getErrmsg());
    }

    public void pushFile(String webhook, String appKey, String appSecret, DocBean docBean,String secret) throws Exception {
        DingTalkClient client = pushOutBot(webhook,secret);
        // 创建一个OapiRobotSendRequest对象，用来设置消息的内容和类型
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 设置消息类型为file
        request.setMsgtype("link");
        // 创建一个OapiRobotSendRequest.Markdown对象，用来设置markdown消息的标题和文本
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        // 钉钉机器人的安全签名，需要替换成你自己的

//        Sample sample = new Sample();

//        String secret = sample.getAccessToken(appKey, appSecret);
        // 调用钉钉的媒体文件上传接口，上传本地图片，获取media_id
//        String mediaId = uploadImage.uploadImage(imagePath, secret,"file");

        // 设置标题
        link.setTitle("文件消息");
//        link.setText("1.4盘面梳理.pdf");
        link.setText(docBean.getFileName());
//        String fileUrl = "https://oapi.dingtalk.com/media/downloadFile?access_token=%s&media_id=%s";
//        link.setMessageUrl("https://img1-cdn-picsh.stock-ring.com/download/pdf/2024-1-4/pdf_17043732495821660.pdf");
        link.setMessageUrl(docBean.getLink());
        // 将markdown对象设置到request对象中
        request.setLink(link);
        // 调用client的execute方法，发送消息，并获取响应
        OapiRobotSendResponse response = client.execute(request);
        // 打印响应的结果
        System.out.println(response.getErrcode());
        System.out.println(response.getErrmsg());
    }

    public static DingTalkClient pushOutBot(String webhook, String secret) throws ApiException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
//        secret = dingBot.getSecret();

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
//        webhook = "https://oapi.dingtalk.com/robot/send?access_token=0d60a46e7c9a2446a2be268d187663c56ebeff39e064bfa415c62152a8cdff70";
        DingTalkClient client = new DefaultDingTalkClient(webhook + "&timestamp=" + timestamp + "&sign=" + sign);
//        OapiRobotSendRequest request = new OapiRobotSendRequest();
//        request.setMsgtype("text");
//        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//        text.setContent("测试文本消息");
//        request.setText(text);

//        request.setMsgtype("link");
//        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//        link.setMessageUrl("https://www.dingtalk.com/");
//        link.setPicUrl("");
//        link.setTitle("时代的火车向前开");
//        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//        request.setLink(link);

//        request.setMsgtype("markdown");
//        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
//        markdown.setTitle("杭州天气");
//        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
//                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
//                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
//                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
//        request.setMarkdown(markdown);
//        OapiRobotSendResponse response = client.execute(request);
        return client;
    }

    //批量发送消息
    public boolean pushMessageList(List<MessageBean> messageList, String forumId) throws Exception {
        ForumBean forumBean = DataCache.forumBeanMap.get(forumId);
        List<DingBot> dingBotList = forumBean.getDingBotList();
        MessageService messageService = new MessageService();
        for (DingBot dingBot : dingBotList) {
//            long timeStamp = new Date().getTime();
//            pushImage(dingBot.getWebhook(),dingBot.getAppKey(),dingBot.getAppSecret(),"D:\\code\\code\\JFrame\\aa\\20200804-申万宏源-2014年A股复盘.pdf");

            for (MessageBean messageBean : messageList) {
                if (!StringUtils.isEmpty(messageBean.getImage().replace("[", "").replace("]", ""))) {
                    if (!StringUtils.isEmpty(messageBean.getImage())) {
                        String[] imageArray = messageBean.getImage().replace("[", "").replace("]", "").split(",");
                        List<String> urlList = new ArrayList<>();
                        for (String imageUrl : imageArray) {
                            imageUrl = imageUrl.replace("\"", "");
                            //获取当前系统时间戳
                            long timeStamp = new Date().getTime();
                            DownloadController.downloadFile(imageUrl, "image\\" + forumId, timeStamp + ".jpg");
                            //添加水印
                            try {
                                WatermarkService.waterMarkPhoto("image\\" + forumId + "\\" + timeStamp + ".jpg", "image\\" + forumId + "\\" + timeStamp + "-水印.jpg", forumBean.getWatermarkContent(),"12");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            urlList.add("image\\" + forumId + "\\" + timeStamp + "-水印.jpg");

                        }
                        //上传图片到钉钉
                        try {
                            pushImage(dingBot.getWebhook(), dingBot.getAppKey(), dingBot.getAppSecret(), urlList, dingBot.getSecret(),messageBean.getContent());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (!StringUtils.isEmpty(messageBean.getContent())) {
                    pushText(messageBean.getContent(), dingBot.getWebhook(), dingBot.getSecret());
                } if (messageBean.getDocBeans() != null && messageBean.getDocBeans().size() > 0) {
                    List<DocBean> docBeanList = messageBean.getDocBeans();
                    for (DocBean docBean : docBeanList) {
                        try {
                            pushFile(dingBot.getWebhook(), dingBot.getAppKey(), dingBot.getAppSecret(), docBean,dingBot.getSecret());
                            System.out.println("发送文件成功");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                messageBean.setSend(true);
                messageService.messageSave(messageBean, forumId);
            }
        }
        return true;
    }

//    public static void main(String[] args)  {
//        try {
//            pushOutBot();
//        } catch (ApiException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
//    }


}

