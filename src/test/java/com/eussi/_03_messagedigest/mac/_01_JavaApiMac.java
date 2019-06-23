package com.eussi._03_messagedigest.mac;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _01_JavaApiMac {

    @Test
    public void test() {
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacMD5：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            SecretKey secretKey1 = new SecretKeySpec(key, "HmacMD5");

            byte[] input = "test".getBytes();
            //获取mac
            Mac mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            byte[] output = mac.doFinal(input);
            System.out.println("test摘要HmacMD5：" + Arrays.toString(output));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacSHA1：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacSHA1");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacSHA1：" + Arrays.toString(output));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacSHA256：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacSHA256");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacSHA256：" + Arrays.toString(output));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacSHA384");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacSHA384：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacSHA384");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacSHA384：" + Arrays.toString(output));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacSHA512");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacSHA512：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacSHA512");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacSHA512：" + Arrays.toString(output));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
