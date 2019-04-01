package com.eussi.jca_jce.securitypkg;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _03_MessageDigestTest {
    @Test
    public void test() {
        try {
            //原始信息
            byte[] input = "test".getBytes();
            //初始化摘要对象
            MessageDigest sha = MessageDigest.getInstance("sha");
            //更新摘要信息
            sha.update(input);
            //获取摘要结果
            byte[] output = sha.digest();
            System.out.println("test摘要：" + Arrays.toString(output));
//---------------------------------------------------------------------------------------
            //原始信息
            input = "test1".getBytes();
            //初始化摘要对象
            sha = MessageDigest.getInstance("sha");
            //更新摘要信息
            sha.update(input);
            //获取摘要结果
            output = sha.digest();
            System.out.println("test1摘要：" + Arrays.toString(output));
//---------------------------------------------------------------------------------------
            //原始信息
            input = "test".getBytes();
            //初始化摘要对象
            sha = MessageDigest.getInstance("sha");
            //更新摘要信息
            sha.update("t".getBytes());
            sha.update("e".getBytes());
            sha.update("s".getBytes());
            sha.update("t".getBytes());
            //获取摘要结果
            output = sha.digest();
            System.out.println("test摘要：" + Arrays.toString(output));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
