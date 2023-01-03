package test;

import com.hyjk.im.common.utils.Base64;

import java.io.*;

/**
 * @author yangzl 2021.06.02
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class Base64_img {


    public static void main(String[] args) {
        String str = "";
        str = GetImageStr();
        GenerateImage(str);
    }

    // 图片转化成base64字符串
    public static String GetImageStr() {
        String imgFile = "C:\\logs\\22.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        System.out.println(Base64.encode(data));
        return Base64.encode(data);// 返回Base64编码过的字节数组字符串
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static boolean GenerateImage(String imgStr) {
        if (imgStr == null) {
            // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] b = Base64.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "C:\\logs\\33.jpg";// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
