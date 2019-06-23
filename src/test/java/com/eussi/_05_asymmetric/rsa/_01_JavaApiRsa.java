package com.eussi._05_asymmetric.rsa;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_JavaApiRsa {

    @Test
    public void test() {
        try {
            //实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器
            keyPairGenerator.initialize(512);
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            System.out.println("公钥：" + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
            System.out.println("私钥：" + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

            //密钥还原
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //甲方公钥还原
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //私钥还原
            //初始化私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
            //产生私钥
            PrivateKey privateKey = keyFactory1.generatePrivate(pkcs8EncodedKeySpec);

            //私钥加密，公钥解密
            //私钥加密
            Cipher cipher = Cipher.getInstance(keyFactory1.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal("test".getBytes());
            System.out.println("私钥加密test结果：" + Base64.encodeBase64String(output));
            //公钥解密
            Cipher cipher2 = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher2.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] origin = cipher2.doFinal(output);
            System.out.println("公钥解密结果：" + new String(origin));

            //公钥加密，私钥解密
            //公钥加密
            Cipher cipher3 = Cipher.getInstance(keyFactory1.getAlgorithm());
            cipher3.init(Cipher.ENCRYPT_MODE, publicKey);
            output = cipher3.doFinal("test".getBytes());
            System.out.println("公钥加密test结果：" + Base64.encodeBase64String(output));
            //私钥解密
            Cipher cipher4 = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher4.init(Cipher.DECRYPT_MODE, privateKey);
            origin = cipher4.doFinal(output);
            System.out.println("私钥解密结果：" + new String(origin));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
