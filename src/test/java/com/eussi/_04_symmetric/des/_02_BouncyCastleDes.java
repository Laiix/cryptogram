package com.eussi._04_symmetric.des;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _02_BouncyCastleDes {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //初始化
            keyGenerator.init(64);
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥DES：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            DESKeySpec dks = new DESKeySpec(key);
            //实例化密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            //生成秘钥
            SecretKey secretKey1 = secretKeyFactory.generateSecret(dks);

            byte[] input = "test".getBytes();
            //实例化
            //加密：加解密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密DES：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] origin = cipher.doFinal(output);
            System.out.println("test解密DES：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", new BouncyCastleProvider());
            //初始化
            keyGenerator.init(64);
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥DES：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            DESKeySpec dks = new DESKeySpec(key);
            //实例化密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            //生成秘钥
            SecretKey secretKey1 = secretKeyFactory.generateSecret(dks);

            byte[] input = "test".getBytes();
            //实例化
            //加密：加解密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密DES：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher2.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] origin = cipher2.doFinal(output);
            System.out.println("test解密DES：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
