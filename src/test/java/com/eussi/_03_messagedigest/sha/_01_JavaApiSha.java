package com.eussi._03_messagedigest.sha;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _01_JavaApiSha {

    @Test
    public void test() {
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象，SHA与SHA-1相同
            MessageDigest sha = MessageDigest.getInstance("SHA");
            //获取摘要结果
            byte[] output = sha.digest(input);
            System.out.println("test摘要SHA-1：" + Arrays.toString(output));
            //初始化摘要对象
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            //获取摘要结果
            output = sha256.digest(input);
            System.out.println("test摘要SHA-256：" + Arrays.toString(output));
            //初始化摘要对象
            MessageDigest sha384 = MessageDigest.getInstance("SHA-384");
            //获取摘要结果
            output = sha384.digest(input);
            System.out.println("test摘要SHA-384：" + Arrays.toString(output));
            //初始化摘要对象
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            //获取摘要结果
            output = sha512.digest(input);
            System.out.println("test摘要SHA-512：" + Arrays.toString(output));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
