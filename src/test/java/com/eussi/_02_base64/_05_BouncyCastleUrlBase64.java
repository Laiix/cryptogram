package com.eussi._02_base64;

import org.bouncycastle.util.encoders.UrlBase64;
import org.junit.Test;


/**
 * Created by wangxueming on 2019/2/21.
 */
public class _05_BouncyCastleUrlBase64 {
    @Test
    public void test() {
        try {
            String text = "密";
            byte[] textByte;
            textByte = text.getBytes("GBK");
            //编码
            byte[] encodeBytes = UrlBase64.encode(textByte);
            String encodedText = new String(encodeBytes, "GBK");
            System.out.println(encodedText);
            //解码
            System.out.println(new String(UrlBase64.decode(encodedText), "GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
