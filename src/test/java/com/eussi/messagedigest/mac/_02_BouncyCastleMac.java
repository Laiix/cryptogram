package com.eussi.messagedigest.mac;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _02_BouncyCastleMac {

    @Test
    public void test() {
        try {
            //添加提供者
            Security.addProvider(new BouncyCastleProvider());

            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD2");
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacMD2：" + Arrays.toString(key));
            System.out.println("生成秘钥HmacMD2：" + new String(Hex.encode(key)));
            //还原密钥,可直接用secretKey，这里主要演示还原
            SecretKey secretKey1 = new SecretKeySpec(key, "HmacMD2");

            byte[] input = "test".getBytes();
            //获取mac
            Mac mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            byte[] output = mac.doFinal(input);
            System.out.println("test摘要HmacMD2：" + Arrays.toString(output));
            System.out.println("test摘要HmacMD2：" + new String(Hex.encode(output)));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacMD4");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacMD4：" + Arrays.toString(key));
            System.out.println("生成秘钥HmacMD4：" + new String(Hex.encode(key)));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacMD4");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacMD4：" + Arrays.toString(output));
            System.out.println("test摘要HmacMD4：" + new String(Hex.encode(output)));

            //初始化KeyGenerator
            keyGenerator = KeyGenerator.getInstance("HmacSHA224");
            //产生密钥
            secretKey = keyGenerator.generateKey();
            //获得密钥
            key = secretKey.getEncoded();
            System.out.println("生成秘钥HmacSHA224：" + Arrays.toString(key));
            System.out.println("生成秘钥HmacSHA224：" + new String(Hex.encode(key)));
            //还原密钥,可直接用secretKey，这里主要演示还原
            secretKey1 = new SecretKeySpec(key, "HmacSHA224");

            input = "test".getBytes();
            //获取mac
            mac = Mac.getInstance(secretKey1.getAlgorithm());
            //初始化mac
            mac.init(secretKey1);
            //执行消息摘要
            output = mac.doFinal(input);
            System.out.println("test摘要HmacSHA224：" + Arrays.toString(output));
            System.out.println("test摘要HmacSHA224：" + new String(Hex.encode(output)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
