package aiguquan.webSocket;

import aiguquan.bean.DingBot;
import aiguquan.bean.ForumBean;
import aiguquan.bean.MessageBean;
import aiguquan.dingding.util.DingTalkPushUtil;
import aiguquan.service.MessageService;
import aiguquan.service.WatermarkService;
import aiguquan.ui.NoLogin;
import bean.DocBean;
import cache.DataCache;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import controller.DownloadController;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cache.DataCache.forumBeanMap;

@Component
public class JavaClient {

    public WebSocketClient getClient(String uri, String id) {
        try {
            WebSocketClient client = new WebSocketClient(new URI(uri),new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    System.out.println("接受到来自服务端的消息：" + s);
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    if (bytes.hasArray()) {
                        String s = null; // 使用utf-8编码创建字符串
                        try {
                            s = new String(bytes.array(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
//                        s = "{\"kind\":\"forum_article_publish\",\"data\":{\"forum_id\":\"629\",\"msg_id\":181261,\"article_detail\":{\"video_album_id\":0,\"id\":181261,\"forum_id\":629,\"state\":41,\"title\":\"\",\"brief\":\"\",\"content\":\"\\u5149\\u4f0f\\u53ef\\u4ee5\\u4f4e\\u5438\\u4e00\\u70b9\\u505a\\u65e5\\u5185\\u56de\\u6d41\\u7684\\u9884\\u671f\\uff0c\\u6211\\u9009\\u7684\\u9526\\u6d6a\\u79d1\\u6280\",\"user_id\":130579,\"forum_user_id\":126326,\"is_free\":1,\"is_charge\":0,\"price\":\"0.00\",\"type\":\"discuss\",\"is_selected\":0,\"is_top\":0,\"ask_id\":0,\"create_at\":\"10:02\",\"is_forward\":0,\"is_push\":0,\"avatar\":\"https:\\/\\/img1-cdn-picsh.stock-ring.com\\/circle_main_avatar\\/2023-2-23\\/teacher_avatar_16771339696566055=640X640.jpeg\",\"nick_name\":\"92\\u4e00\\u9e23\",\"top_time\":\"0000-00-00 00:00:00\",\"group_id\":0,\"is_updated\":0,\"live_id\":0,\"is_allow_preview\":0,\"in_file_list_show\":1,\"is_send_time\":0,\"is_list_show\":1,\"forward_info\":[],\"ask\":{},\"nickname\":\"92\\u4e00\\u9e23\",\"forum_name\":\"\\u4e00\\u53e3\\u9e1f\\u79c1\\u4eab\\u4f1a\",\"posts_abstract\":\"\\u5149\\u4f0f\\u53ef\\u4ee5\\u4f4e\\u5438\\u4e00\\u70b9\\u505a\\u65e5\\u5185\\u56de\\u6d41\\u7684\\u9884\\u671f\\uff0c\\u6211\\u9009\\u7684\\u9526\\u6d6a\\u79d1\\u6280\",\"brief_html\":\"\",\"can_read\":1,\"image\":[],\"file\":[],\"video\":[],\"voice\":[],\"share_info\":[],\"video_album\":[],\"is_support\":0,\"read_number\":0,\"support_number\":0,\"comment_number\":0,\"create_at_time_stamp\":\"2024-01-19 10:02:40\",\"share_url\":\"https:\\/\\/m.aiguquan.com\\/detail\\/181261\",\"forum_master\":0,\"share_message\":{\"share_logo\":\"https:\\/\\/img1-cdn-picsh.stock-ring.com\\/cover_126326_1676956969\",\"share_moments_logo\":\"https:\\/\\/cdn.stock-ring.com\\/static\\/wap\\/img\\/icon_share_logo.png\",\"share_title\":\"[\\u4e00\\u53e3\\u9e1f\\u79c1\\u4eab\\u4f1a]\\u7684\\u5e16\\u5b50\",\"share_moments_title\":\"[\\u4e00\\u53e3\\u9e1f\\u79c1\\u4eab\\u4f1a]\\u7684\\u5e16\\u5b50\",\"share_content\":\"\\u6765\\u81ea[\\u4e00\\u53e3\\u9e1f\\u79c1\\u4eab\\u4f1a]\\u5708\\u4e3b\\u7684\\u5e16\\u5b50\",\"share_link\":\"https:\\/\\/m.aiguquan.com\\/detail\\/181261\"},\"wx_share\":{},\"stock_info\":[],\"user_role\":\"forum_user\",\"content_wrap\":\"\\u5149\\u4f0f\\u53ef\\u4ee5\\u4f4e\\u5438\\u4e00\\u70b9\\u505a\\u65e5\\u5185\\u56de\\u6d41\\u7684\\u9884\\u671f\\uff0c\\u6211\\u9009\\u7684\\u9526\\u6d6a\\u79d1\\u6280\",\"is_audit_server_live_msg\":0}}}\n";
//                        s = "{\"Kind\":\"chat_room_send_message\",\"Id\":\"1119\",\"Data\":{\"Id\":437002,\"ForumId\":662,\"UserId\":37152,\"OriginUserId\":37152,\"Content\":\"因为它只有石油，有色周期股进攻，而单靠这两个方向是不行的\",\"ContentType\":1,\"OriginId\":0,\"State\":0,\"CreateTime\":\"2024-04-07 20:07:09\",\"UpdateTime\":\"\",\"SynForumArticleId\":0,\"origin_msg_user_id\":0,\"nick_name\":\"摸鱼小队\",\"avatar\":\"https://img1-cdn-picsh.stock-ring.com/live_default_cover/2024-3-1/1709268244934=1181X1181.jpg\",\"origin_nickname\":\"\",\"origin_avatar\":\"\",\"OriginData\":[]}}";
                        System.out.println(s); // 输出字符串
                        String result = s;
                        if ("pong".equals(result)) {
                            NoLogin.tableUpdate(id, 5, "连接成功");
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        String kind = jsonObject.getString("kind");
                        if (StringUtils.isEmpty(kind))
                            kind = jsonObject.getString("Kind");
                        DingTalkPushUtil dingTalkPushUtil = new DingTalkPushUtil();
                        MessageService messageService = new MessageService();
                        if ("forum_article_publish".equals(kind)) {
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            JSONObject jsonArticleDetail = jsonData.getJSONObject("article_detail");
                            String content1 = jsonArticleDetail.getString("content"); // 创建一个Unicode编码的字符串
                            String imageUrl = jsonArticleDetail.getString("image").replace("watermark/","").replace("[\"","").replace("\"]","");//去除原系统水印
                            JSONArray files = jsonArticleDetail.getJSONArray("file");
                            String forumId = jsonArticleDetail.getString("forum_id");
                            String nickName =  jsonArticleDetail.getString("nick_name");
                            String createTime = jsonArticleDetail.getString("create_at_time_stamp");
                            String content = nickName + "【" + createTime + "发言】" + content1;
                            System.out.println(content);
                            System.out.println(imageUrl);
                            List<DocBean> docBeanList = new ArrayList<>();
                            for (Object obj : files) {
                                JSONObject job = (JSONObject) obj;
                                System.out.println(job.get("link"));
                                DocBean docBean = new DocBean();
                                docBean.setLink(job.getString("link"));
                                docBean.setFileName(job.getString("name"));
                                docBean.setExtension(job.getString("type"));//后缀
                                docBeanList.add(docBean);
                            }
                            int count = forumBeanMap.get(id).getMessageBeanList().size();
                            MessageBean messageBean = new MessageBean();
                            messageBean.setId(count);
                            messageBean.setContent(content);
                            messageBean.setImage(imageUrl);
                            messageBean.setDocBeans(docBeanList);
                            //获取当前时间
                            messageBean.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            ForumBean forumBean = forumBeanMap.get(forumId);
                            if (forumBean == null) {
                                forumBean = new ForumBean();
                                forumBean.setId(forumId);
                            }
                            //如果消息中包含关键词，则放到过滤消息列表中
                            if (Arrays.stream(forumBean.getKeywordsFilter()).anyMatch(x -> content.contains(x))) {
                                messageBean.setSend(false);
                            }
                            LinkedList<MessageBean> messageBeanList = forumBean.getMessageBeanList();
                            if (messageBeanList == null || messageBeanList.isEmpty()) {
                                messageBeanList = new LinkedList<>();
                            }
                            //发送前先保存
                            messageService.messageSave(messageBean,forumId);
                            System.out.println("保存消息成功");

                            List<DingBot> dingBotList = forumBean.getDingBotList();
                            for (DingBot dingBot:dingBotList) {
                                // 发送消息到钉钉
                                System.out.println(forumId+"开始发送消息到钉钉："+ dingBot.getName());
                                if (!StringUtils.isEmpty(imageUrl.replace("[","").replace("]",""))) {
                                    System.out.println(forumId+"开始发送图片到钉钉："+ dingBot.getName());
                                    //获取当前系统时间戳
                                    long timeStamp = new Date().getTime();
                                    String[] imageUrls = imageUrl.replace("[","").replace("]","").split(",");
                                    List<String> urlList = new ArrayList<>();
                                    for (String url : imageUrls) {
                                        url = url.replace("\"","");
                                        DownloadController.downloadFile(url, "image\\" + id, timeStamp + ".jpg");
                                        System.out.println("下载图片成功");
                                        //添加水印
                                        try {
                                            String[] watermark = forumBean.WatermarkContent.split("/");
                                            String watermarkContent = watermark[0];
                                            String watermarkSize = watermark[1];
                                            WatermarkService.waterMarkPhoto("image\\" + id + "\\" + timeStamp + ".jpg", "image\\" + id + "\\" + timeStamp + "-水印.jpg",watermarkContent,watermarkSize);
                                            System.out.println("水印添加成功");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        urlList.add("image\\" + id + "\\" + timeStamp + "-水印.jpg");
                                    }
                                    //上传图片到钉钉
                                    try {
                                        dingTalkPushUtil.pushImage(dingBot.getWebhook(), dingBot.getAppKey(), dingBot.getAppSecret(),urlList,dingBot.getSecret(),content);
                                        System.out.println("上传图片成功");
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }else if (!StringUtils.isEmpty(content)) {
                                        dingTalkPushUtil.pushText(content,dingBot.getWebhook(),dingBot.getSecret());
                                        System.out.println(forumId+"发送消息到钉钉成功："+ dingBot.getName());
                                }

                                if (files.size() > 0){
                                    System.out.println(forumId+"开始发送文件到钉钉："+ dingBot.getName());
                                    for (DocBean docBean:docBeanList) {
                                        try {
                                            dingTalkPushUtil.pushFile(dingBot.getWebhook(), dingBot.getAppKey(), dingBot.getAppSecret(),docBean,dingBot.getSecret());
                                            System.out.println("发送文件成功");
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            }
                            //循环完成再保存一遍
                            messageBean.setSend(true);
                            messageService.messageSave(messageBean,forumId);
                        }else if ("chat_room_send_message".equals(kind)){
                            //聊天室内容
                            JSONObject jsonData = jsonObject.getJSONObject("Data");
                            String content = jsonData.getString("Content");
                            String forumId = jsonData.getString("ForumId");
                            String nickName = jsonData.getString("nick_name");
                            String createTime = jsonData.getString("CreateTime");
                            System.out.println(content);
                            System.out.println(forumId);
                            System.out.println(nickName);
                            System.out.println(createTime);
                            if (forumBeanMap.get(id).getValidUserName() == null || Arrays.stream(forumBeanMap.get(id).getValidUserName()).anyMatch(x -> nickName.contains(x))) {
                                content = nickName + "【" + createTime + "发言】" + content;
                                //保存消息
                                int count = forumBeanMap.get(id).getMessageBeanList().size();
                                MessageBean messageBean = new MessageBean();
                                messageBean.setId(count);
                                messageBean.setContent(content);
                                //获取当前时间
                                messageBean.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                                ForumBean forumBean = forumBeanMap.get(forumId);
                                if (forumBean == null) {
                                    forumBean = new ForumBean();
                                    forumBean.setId(forumId);
                                }
                                LinkedList<MessageBean> messageBeanList = forumBean.getMessageBeanList();
                                if (messageBeanList == null || messageBeanList.isEmpty()) {
                                    messageBeanList = new LinkedList<>();
                                }
                                //发送前先保存
                                messageService.messageSave(messageBean, forumId);
                                System.out.println("保存消息成功");

                                List<DingBot> dingBotList = forumBean.getDingBotList();
                                for (DingBot dingBot : dingBotList) {
                                    // 发送消息到钉钉
                                    System.out.println(forumId + "开始发送消息到钉钉：" + dingBot.getName());
                                    if (!StringUtils.isEmpty(content)) {
                                        dingTalkPushUtil.pushText(content, dingBot.getWebhook(), dingBot.getSecret());
                                        System.out.println(forumId + "发送消息到钉钉成功：" + dingBot.getName());
                                    }
                                }
                                //循环完成再保存一遍
                                messageBean.setSend(true);
                                messageService.messageSave(messageBean, forumId);
                            }
                        }
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("关闭链接：：" + "i = " + i + ":::s=" + s + ":::b=" + b);
                    NoLogin.tableUpdate(id, 5, "断开连接");
                    close();
                    try {
                        Thread.sleep(5000);
                        NoLogin.clickGetAll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    System.out.println("报错信息：" + e);
                }
            };
            System.out.println("链接成功:"+client);
            client.connect();

            while (client.getReadyState().ordinal() == 0) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {

                }
                System.out.println("++++++++++++++++连接中+++++++++");
                NoLogin.tableUpdate(id, 5, "连接中");
            }
            if (client.getReadyState().ordinal() == 1) {
                System.out.println("=========链接成功=============");
//                ForumBean forumBean = forumBeanMap.get(id);
//                forumBean.setType("1");
//                forumBeanMap.put(id, forumBean);
                //更新表格
                NoLogin.tableUpdate(id, 5, "连接成功");
                return client;
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //连接websocket方法
    public void clientWebSocket(String id) {
        WebSocketClient client = getClient("wss://im.stock-ring.com/ws/desktop_push", id);
        if (client != null) {
            int i = 0;
            while (true) {
                //判断当前时间是否比指定时间大于三个月
                Date nowDate = new Date();
                String date = "2024-04-10 00:00:00";
                //格式化时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = sdf.parse(date);
                    if (nowDate.getTime() - date1.getTime() > 5600000000L) {
                        NoLogin.tableUpdate(id, 5, "连接失败");
                        return;
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                //发送消息

                JSONObject jsonObject = new JSONObject();
                if (i == 0) {
                    jsonObject.put("kind", "subscribe");
                    jsonObject.put("data", "private_letter_121910,refresh_private_letter_list_121910,forum_" + id);
                } else {
                    jsonObject.put("kind", "ping");
                }
                System.out.println(new Date() + "," + jsonObject.toJSONString());
                client.send(jsonObject.toJSONString());
                if (i >= 1000) {
                    i = 2;
                }
                i++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
    }

    public void clientWebSocket1(String id) {

        WebSocketClient client = getClient("wss://im.stock-ring.com/ws/chat?forum_id="+id+"&user_id=177738&source=pc", id);
        if (client != null) {
            int i = 0;
            while (true) {
                //判断当前时间是否比指定时间大于三个月
                Date nowDate = new Date();
                String date = "2024-04-10 00:00:00";
                //格式化时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date1 = sdf.parse(date);
                    if ((nowDate.getTime() - date1.getTime()) > 5600000000L) {
                        NoLogin.tableUpdate(id, 5, "连接失败");
                        return;
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                //发送消息

                JSONObject jsonObject = new JSONObject();
                if (i == 0) {
                    jsonObject.put("kind", "joinForumGroup");
                    jsonObject.put("data", id);
                } else {
                    jsonObject.put("kind", "ping");
                }
                System.out.println(new Date() + "," + jsonObject.toJSONString());
                client.send(jsonObject.toJSONString());
                if (i >= 1000) {
                    i = 2;
                }
                i++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        }

    }
}




