package com.eussi.base64;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangxueming on 2019/2/21.
 */
public class _01_javaapi {
    @Test
    public void test() {
        try {
            final BASE64Encoder encoder = new BASE64Encoder();
            final BASE64Decoder decoder = new BASE64Decoder();
            final String text = "密";
            final byte[] textByte;
            textByte = text.getBytes("UTF-8");
            //编码
            final String encodedText = encoder.encode(textByte);
            System.out.println(encodedText);
            //解码
            System.out.println(new String(decoder.decodeBuffer(encodedText), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
