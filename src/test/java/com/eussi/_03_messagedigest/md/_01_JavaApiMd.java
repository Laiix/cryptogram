package com.eussi._03_messagedigest.md;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _01_JavaApiMd {

    @Test
    public void test() {
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest md2 = MessageDigest.getInstance("MD2");
            //获取摘要结果
            byte[] output = md2.digest(input);
            System.out.println("test摘要MD2：" + Arrays.toString(output));
            //初始化摘要对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //获取摘要结果
            output = md5.digest(input);
            System.out.println("test摘要MD5：" + Arrays.toString(output));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
