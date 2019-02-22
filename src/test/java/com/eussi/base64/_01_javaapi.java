package com.eussi.base64;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangxueming on 2019/2/21.
 */
public class _01_JavaApi {
    @Test
    public void test() {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            BASE64Decoder decoder = new BASE64Decoder();
            String text = "密";
            byte[] textByte;
            textByte = text.getBytes("UTF-8");
            //编码
            String encodedText = encoder.encode(textByte);
            System.out.println(encodedText);
            //解码
            System.out.println(new String(decoder.decodeBuffer(encodedText), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
