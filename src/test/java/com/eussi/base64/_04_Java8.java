package com.eussi.base64;

import org.junit.Test;

import java.util.Base64;

/**
 * Created by wangxueming on 2019/2/21.
 */
public class _04_Java8 {
    @Test
    public void test() {
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            final Base64.Encoder encoder = Base64.getEncoder();
            final String text = "密";
            final byte[] textByte = text.getBytes("UTF-8");
            //编码
            final String encodedText = encoder.encodeToString(textByte);
            System.out.println(encodedText);
            //解码
            System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
