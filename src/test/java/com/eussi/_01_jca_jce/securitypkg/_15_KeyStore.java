package com.eussi._01_jca_jce.securitypkg;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

/**
 * demo待完善，后面看到Https时在完善
 * Created by wangxueming on 2019/4/1.
 */
public class _15_KeyStore {//秘钥库，用来管理密钥和证书的存储

    public static void main(String[] args) {
        try {
            //加载密钥库文件
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/aliyun.keystore");
            //实例化KEyStore
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());//jks
            //加载密钥库，使用密码
            keyStore.load(fileInputStream, "passwd".toCharArray());
            fileInputStream.close();

            //获得别名为alias所对应的私钥
            PrivateKey key = (PrivateKey) keyStore.getKey("alias", "passwd".toCharArray());

            //获得私钥项
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry("alias", new KeyStore.PasswordProtection("passwd".toCharArray()));
            //获得私钥
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
