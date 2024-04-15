package controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadController {

    @SuppressWarnings("finally")
    public static File downloadFile(String urlPath, String downloadDir,String fileName) {
        File file = null;
        try {

            URL url = new URL(urlPath);

            URLConnection urlConnection = url.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;// http的连接类

            //String contentType = httpURLConnection.getContentType();//请求类型,可用来过滤请求，

            httpURLConnection.setConnectTimeout(1000*5);//设置超时

//            httpURLConnection.setRequestMethod("POST");//设置请求方式，默认是GET

            httpURLConnection.setRequestProperty("Charset", "UTF-8");// 设置字符编码


            httpURLConnection.connect();// 打开连接

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileName;// 指定存放位置
            file = new File(path);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(file);
            int size = 0;

            byte[] b = new byte[2048];
            //把输入流的文件读取到字节数据b中，然后输出到指定目录的文件
            while ((size = bin.read(b)) != -1) {
                out.write(b, 0, size);
            }
            // 关闭资源
            bin.close();
            out.close();
            System.out.println("文件下载成功！");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("文件下载失败！");
        } finally {
            return file;
        }

    }

}
