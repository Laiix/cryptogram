package com.eussi._05_asymmetric.Elgamal;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_BouncyCastleEIGamal {

    @Test
    public void test() {
        try {
            //加入BC支持
            Security.addProvider(new BouncyCastleProvider());
            //实例化算法参数生成器
            AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("ElGamal");
            //初始化算法参数生成器
            algorithmParameterGenerator.init(256);
            //生成算法参数
            AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
            //构建参数材料
            DHParameterSpec dhParameterSpec = algorithmParameters.getParameterSpec(DHParameterSpec.class);
            //实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ElGamal");
            //初始化密钥生成器
            keyPairGenerator.initialize(dhParameterSpec, new SecureRandom());
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            //公钥
            PublicKey aPublic =  keyPair.getPublic();
            //私钥
            PrivateKey aPrivate = keyPair.getPrivate();

            System.out.println("公钥：" + Base64.encodeBase64String(aPublic.getEncoded()));
            System.out.println("私钥：" + Base64.encodeBase64String(aPrivate.getEncoded()));

            //密钥还原
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(aPublic.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("ElGamal");
            //甲方公钥还原
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //私钥还原
            //初始化私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(aPrivate.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory1 = KeyFactory.getInstance("ElGamal");
            //产生私钥
            PrivateKey privateKey = keyFactory1.generatePrivate(pkcs8EncodedKeySpec);

            //公钥加密，私钥解密
            //公钥加密
            Cipher cipher = Cipher.getInstance(keyFactory1.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal("test".getBytes());
            System.out.println("公钥加密test结果：" + Base64.encodeBase64String(output));
            //私钥解密
            Cipher cipher2 = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher2.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] origin = cipher2.doFinal(output);
            System.out.println("私钥解密结果：" + new String(origin));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
