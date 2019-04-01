package com.eussi.jca_jce.specpkg;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wangxueming on 2019/4/1.
 */
public class _04_DESKeySpec {//指定算法为DES，而SecretKeySpec兼容所有算对称算法
    //另外，此类只是一个代表，还有类似PBEKeySpec等类似实现
    @Test
    public void test() {
        //实例化
        try {
            //实例化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");//如果使用DESede --> DESedeKeySpec

            //生成SecretKey
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("secretyKey：" + Arrays.toString(secretKey.getEncoded()));

            //实例化DESKeySpec
            DESKeySpec desKeySpec = new DESKeySpec(secretKey.getEncoded());

            //实例化KeyFactory
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            //获得SecretyKey

            SecretKey secretKey1 = secretKeyFactory.generateSecret(desKeySpec);
            System.out.println("new secretyKey：" + Arrays.toString(secretKey1.getEncoded()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
