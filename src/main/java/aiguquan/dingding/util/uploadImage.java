package aiguquan.dingding.util;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class uploadImage {

    // 定义钉钉的媒体文件上传接口地址
    private static final String UPLOAD_URL = "https://oapi.dingtalk.com/media/upload?access_token=%s&type=%s";
    // 定义图片文件的最大大小为10MB
    private static final long MAX_FILE_SIZE = 500 * 1024 * 1024;

    // 上传本地图片到钉钉的媒体文件上传接口，返回media_id
    public static String uploadImage(String imagePath, String accessToken,String type) throws Exception {
        // 检查图片文件是否存在
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            throw new FileNotFoundException("图片文件不存在：" + imagePath);
        }
        // 检查图片文件是否超过最大大小
        if (imageFile.length() > MAX_FILE_SIZE) {
            throw new IOException("图片文件过大，不能超过500MB");
        }
        // 创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建一个HttpPost对象，用来发送上传请求
        HttpPost httpPost = new HttpPost(String.format(UPLOAD_URL, accessToken, type));
        // 创建一个MultipartEntityBuilder对象，用来设置上传的文件和参数
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 设置上传的文件
        builder.addBinaryBody("media", imageFile);
        // 设置上传的参数
        builder.addTextBody("access_token", accessToken);
        builder.addTextBody("type", type);
        // 设置请求的实体
        httpPost.setEntity(builder.build());
        // 执行请求，获取响应
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 获取响应的实体
        HttpEntity entity = response.getEntity();
        // 将实体转换为JSON字符串
        String result = EntityUtils.toString(entity, "UTF-8");
        // 将JSON字符串转换为JSON对象
        JSONObject jsonObject = JSONObject.parseObject(result);
        // 获取响应的错误码
        int errcode = jsonObject.getIntValue("errcode");
        // 如果错误码不为0，表示请求失败，抛出异常
        if (errcode != 0) {
            throw new RuntimeException("上传图片失败：" + result);
        }
        // 如果错误码为0，表示请求成功，返回media_id
        return jsonObject.getString("media_id");
    }

}
