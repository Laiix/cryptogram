package com.eussi._03_messagedigest.md;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _03_CommonsCodecMd {

    @Test
    public void test() {
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //MD2
            byte[] output = DigestUtils.md2(input);
            System.out.println("test摘要MD2：" + Arrays.toString(output));
            System.out.println("test摘要MD2：" + DigestUtils.md2Hex("test"));
            //MD5
            output = DigestUtils.md5(input);
            System.out.println("test摘要MD5：" + Arrays.toString(output));
            System.out.println("test摘要MD5：" + DigestUtils.md5Hex("test"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
