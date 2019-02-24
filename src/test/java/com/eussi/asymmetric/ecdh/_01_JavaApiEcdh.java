package com.eussi.asymmetric.ecdh;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_JavaApiEcdh {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //实例化密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDH");
            //初始化密钥对生成器
            keyPairGenerator.initialize(256);
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //甲方公钥
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            //甲方私钥
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();

            //通过甲方公钥构建乙方密钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("ECDH");
            //甲方公钥还原
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //由甲方公钥构建乙方密钥
            ECParameterSpec ecParameterSpec = ((ECPublicKey)publicKey).getParams();
            //实例化密钥生成器
            KeyPairGenerator keyPairGenerator1 = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
            //初始化密钥对生成器
            keyPairGenerator1.initialize(ecParameterSpec);
            //产生密钥对
            KeyPair keyPair1 = keyPairGenerator1.genKeyPair();
            //乙方公钥
            ECPublicKey ecPublicKey1 = (ECPublicKey) keyPair1.getPublic();
            //乙方私钥
            ECPrivateKey ecPrivateKey1 = (ECPrivateKey) keyPair1.getPrivate();

            //以上已经演示甲方公钥还原，这里演示一下乙方私钥还原
            //初始化私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey1.getEncoded());
            //实例化密钥工厂
            KeyFactory keyFactory1 = KeyFactory.getInstance("ECDH");
            //产生私钥
            PrivateKey privateKey = keyFactory1.generatePrivate(pkcs8EncodedKeySpec);

            //构建甲方本地密钥
            //实例化
            KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory1.getAlgorithm());
            //初始化
            keyAgreement.init(ecPrivateKey);//甲方私钥
            keyAgreement.doPhase(ecPublicKey1, true);//乙方公钥
            //生成本地密钥
            SecretKey secretKey = keyAgreement.generateSecret("Blowfish");//AES DES RC2等均可

            //构建乙方本地密钥
            //实例化
            KeyAgreement keyAgreement1 = KeyAgreement.getInstance(keyFactory.getAlgorithm());
            //初始化
            keyAgreement1.init(privateKey);//乙方还原私钥
            keyAgreement1.doPhase(publicKey, true);//甲方还原的公钥
            //生成本地密钥
            SecretKey secretKey1 = keyAgreement1.generateSecret("Blowfish");

            //打印各个密钥信息
            System.out.println("甲方公钥：" + Base64.encodeBase64String(ecPublicKey.getEncoded()));
            System.out.println("甲方私钥：" + Base64.encodeBase64String(ecPrivateKey.getEncoded()));
            System.out.println("乙方公钥：" + Base64.encodeBase64String(ecPublicKey1.getEncoded()));
            System.out.println("乙方私钥：" + Base64.encodeBase64String(ecPrivateKey1.getEncoded()));
            System.out.println("甲方本地私钥：" + Base64.encodeBase64String(secretKey.getEncoded()));
            System.out.println("乙方本地私钥：" + Base64.encodeBase64String(secretKey1.getEncoded()));

            //还原本地密钥
            SecretKey secretKey2 = new SecretKeySpec(secretKey.getEncoded(), "Blowfish");
            //数据加密
            Cipher cipher = Cipher.getInstance(secretKey2.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey2);
            byte[] output = cipher.doFinal("test".getBytes());
            System.out.println("甲方本地私钥加密test结果：" + Base64.encodeBase64String(output));

            //还原本地密钥
            SecretKey secretKey3 = new SecretKeySpec(secretKey1.getEncoded(), "Blowfish");
            //数据加密
            Cipher cipher2 = Cipher.getInstance(secretKey3.getAlgorithm());
            cipher2.init(Cipher.DECRYPT_MODE, secretKey3);
            byte[] origin = cipher2.doFinal(output);
            System.out.println("乙方本地私钥解密结果：" + new String(origin));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
