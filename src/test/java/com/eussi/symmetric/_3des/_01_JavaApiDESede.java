package com.eussi.symmetric._3des;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/22.
 */
public class _01_JavaApiDESede {

    @Test
    public void test() {
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            //初始化
            keyGenerator.init(112);//或者112
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥DESede：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            DESedeKeySpec deks = new DESedeKeySpec(key);
            //实例化密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //生成秘钥
            SecretKey secretKey1 = secretKeyFactory.generateSecret(deks);

            byte[] input = "test".getBytes();
            //实例化
            //加密：加解密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密DESede：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher2.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] origin = cipher2.doFinal(output);
            System.out.println("test解密DESede：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        try {
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            //初始化
            keyGenerator.init(168);//或者112
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥DESede：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            DESedeKeySpec deks = new DESedeKeySpec(key);
            //实例化密钥工厂
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
            //生成秘钥
            SecretKey secretKey1 = secretKeyFactory.generateSecret(deks);

            byte[] input = "test".getBytes();
            //实例化
            //加密：加解密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密DESede：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] origin = cipher.doFinal(output);
            System.out.println("test解密DESede：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
