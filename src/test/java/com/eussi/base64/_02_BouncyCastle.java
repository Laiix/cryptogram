package com.eussi.base64;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

/**
 * Created by wangxueming on 2019/2/21.
 */
public class _02_BouncyCastle {
    @Test
    public void test() {
        try {
            final String text = "密";
            final byte[] textByte;
            textByte = text.getBytes("UTF-8");
            //编码
            byte[] encodeBytes = Base64.encode(textByte);
            final String encodedText = new String(encodeBytes, "UTF-8");
            System.out.println(encodedText);
            //解码
            System.out.println(new String(Base64.decode(encodedText), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
