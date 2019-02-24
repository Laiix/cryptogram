package com.eussi.symmetric.pbe;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_JavaApiPbe {

    @Test
    public void test() {
        try {
            //原文
            byte[] input = "test".getBytes();
            //口令
            String pwd = "123456";
            //实例化安全随机数
            SecureRandom sr = new SecureRandom();
            //产出盐
            byte[] salt = sr.generateSeed(8);

            //转换密钥
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pwd.toCharArray());
            //实例化
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            //生成秘钥
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

            //实例化PBE参数 第二个参数为迭代次数
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);

            //实例化
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            //初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
            //执行操作
            byte[] output = cipher.doFinal(input);
            System.out.println("test加密PBEWITHMD5andDES：" + Arrays.toString(output));
            //解密
            //实例化
            Cipher cipher2 = Cipher.getInstance("PBEWITHMD5andDES");
            //初始化，设置为加密模式
            cipher2.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
            //执行操作
            byte[] origin = cipher2.doFinal(output);
            System.out.println("test解密PBEWITHMD5andDES：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
