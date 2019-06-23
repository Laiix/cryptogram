package com.eussi._08_certificate.base;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;

/**
 * 证书操作
 * Created by wangxueming on 2019/6/23.
 */
public class CertificateCoder {
    public static final String CERT_TYPE = "X.509";
    /**
     * 由keystore获取私钥
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKeyByKeyStore(String keyStorePath, String alias, String password) throws Exception {
        //获取密钥库
        KeyStore ks = getKeyStore(keyStorePath, password);
        //获取私钥
        return (PrivateKey) ks.getKey(alias, password.toCharArray());
    }

    /**
     * 由证书获取公钥
     * @param certificatePath
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKeyByCertificate(String certificatePath) throws Exception {
        //获取证书
        Certificate certificate = getCertificate(certificatePath);
        //获取公钥
        return certificate.getPublicKey();
    }

    /**
     * 通过证书文件获取证书
     * @param certificatePath
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String certificatePath) throws Exception{
        //实例化证书工厂
        CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
        //获得证书输入流
        FileInputStream fileInputStream = new FileInputStream(certificatePath);
        //获得证书
        Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
        //关闭流
        fileInputStream.close();

        return certificate;
    }

    /**
     * 通过密钥库获取证书
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password) throws Exception{
        //获取密钥库
        KeyStore ks = getKeyStore(keyStorePath, password);
        //获得证书
        return ks.getCertificate(alias);
    }

    private static KeyStore getKeyStore(String keyStorePath, String password) throws Exception{
        //实例化密钥库
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        //获取密钥文件流
        FileInputStream fis = new FileInputStream(keyStorePath);
        //加载密钥库
        ks.load(fis, password.toCharArray());
        //关闭流
        fis.close();

        return ks;
    }

    /**
     * 私钥加密
     * @param data
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath, String alias, String password) throws Exception {
        //取得私钥
        PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
        //对数据加密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath, String alias, String password) throws Exception {
        //取得私钥
        PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
        //对数据解密
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     * @param data
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath) throws Exception {
        //取得公钥
        PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
        //对数据加密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     * @param data
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String certificatePath) throws Exception {
        //取得公钥
        PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
        //对数据解密
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 签名
     * @param sign
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] sign, String keyStorePath, String alias, String password) throws Exception {
        //取得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
        //构建签名，由证书指定签名算法
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        //获得私钥
        PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias, password);
        //初始化签名，由私钥创建
        signature.initSign(privateKey);
        signature.update(sign);
        return signature.sign();
    }

    public static boolean verify(byte[] data, byte[] sign, String certificatePath) throws Exception {
        //取得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        //构建签名，由证书指定签名算法
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        //初始化签名，由证书初始化，实际上使用的是证书的公钥
        signature.initVerify(x509Certificate);
        signature.update(data);
        return signature.verify(sign);
    }
}
