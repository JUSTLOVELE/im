package test;

/**
 * @author yangzl 2021.06.22
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class MainTest {

    public static void main(String[] args) {
        String p = "dsdsds.xxxx.aaaa.jpg";
        int lastIndex = p.lastIndexOf(".");
        String fileName = p.substring(0, lastIndex);
        String format = p.substring(lastIndex, p.length());
    }
}
