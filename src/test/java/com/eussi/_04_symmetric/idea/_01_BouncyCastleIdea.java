package com.eussi._04_symmetric.idea;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_BouncyCastleIdea {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("IDEA");
            //初始化
            keyGenerator.init(128);
            //产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            byte[] key = secretKey.getEncoded();
            System.out.println("生成秘钥IDEA：" + Arrays.toString(key));
            //还原密钥,可直接用secretKey，这里主要演示还原
            Key secretKey1 = new SecretKeySpec(key, "IDEA");

            byte[] input = "test".getBytes();
            //实例化
            //加密：加解密算法/工作模式/填充方式
            Cipher cipher = Cipher.getInstance("IDEA/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密IDEA：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("IDEA/ECB/PKCS5Padding");
            //初始化，设置为加密模式
            cipher2.init(Cipher.DECRYPT_MODE, secretKey);
            //执行操作
            byte[] origin = cipher2.doFinal(output);
            System.out.println("test解密IDEA：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
