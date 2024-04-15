package aiguquan.service;

import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//水印服务层
public class WatermarkService {

    //水印算法
    public static void waterMarkPhoto(String oldPath,String newPath,String waterMarkContent,String waterMarkSize) throws IOException {
        File srcImgFile = new File(oldPath);
        // 将文件对象转化为图片对象
        Image srcImg = ImageIO.read(srcImgFile);
        // 获取图片的宽
        int srcImgWidth = srcImg.getWidth(null);
        // 获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        System.out.println("图片的宽:" + srcImgWidth);
        System.out.println("图片的高:" + srcImgHeight);

        // 创建一个画布对象
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

        // 加水印
        // 创建画笔对象
        Graphics2D g = bufImg.createGraphics();
        // 绘制原始图片
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

        // 设置水印的颜色
        g.setColor(new Color(255, 0, 0, 77));
        // 设置字体
        // 画笔字体样式为微软雅黑，加粗，文字大小为60pt
        int size = StringUtils.isEmpty(waterMarkSize) ? 60 : Integer.parseInt(waterMarkSize);
        g.setFont(new Font("微软雅黑", Font.BOLD, size));
        // 水印内容
//        waterMarkContent = "文字\n水印";
        // 设置水印的坐标
        // (为原图片中间位置)
        String[] strArr =  waterMarkContent.split(" ");
        for (int i = 0; i < strArr.length; i++) {
            int x = (srcImgWidth - getWatermarkLength(strArr[i], g)) / 2;
            int y = srcImgHeight / 2 + (60 * i);
            g.drawString(strArr[i], x, y);
        }
        // 画出水印
        // 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
        g.dispose();

        // 待存储的地址
        String tarImgPath = newPath;
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);
        System.out.println("添加水印完成");
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 获取水印文字的长度
     *
     * @param waterMarkContent
     * @param g
     * @return
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
