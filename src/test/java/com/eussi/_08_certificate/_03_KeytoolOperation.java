package com.eussi._08_certificate;

import com.eussi._08_certificate.base.CertificateCoder;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _03_KeytoolOperation {
    private String passwd = "123456";
    private String alias = "www.eussi.top";
    private String certificatePath = "src/test/resources/_08_certificate/keytool/eussi.cer";
    private String keyStorePath = "src/test/resources/_08_certificate/keytool/eussi.keystore";

    /**
     * 以下在无需知晓密钥和算法的前提下，完成了数据加密解密，签名验证操作
     */

    @Test
    public void Test() throws Exception{
        System.out.println("公钥加密，私钥解密:");
        String inputStr = "数字证书";
        byte[] data = inputStr.getBytes();

        //公钥加密
        byte[] encrypt = CertificateCoder.encryptByPublicKey(data, certificatePath);
        //私钥解密
        byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt, keyStorePath, alias, passwd);

        String outputStr = new String(decrypt);
        System.out.println("加密前：" + inputStr);
        System.out.println("解密后：" + outputStr);

    }

    @Test
    public void Test1() throws Exception{
        System.out.println("私钥加密，公钥解密:");
        String inputStr = "数字证书";
        byte[] data = inputStr.getBytes();

        //私钥加密
        byte[] encrypt = CertificateCoder.encryptByPrivateKey(data, keyStorePath, alias, passwd);
        //公钥解密
        byte[] decrypt = CertificateCoder.decryptByPublicKey(encrypt, certificatePath);

        String outputStr = new String(decrypt);
        System.out.println("加密前：" + inputStr);
        System.out.println("解密后：" + outputStr);

    }

    @Test
    public void Test2() throws Exception{
        System.out.println("私钥签名，公钥验证:");
        String inputStr = "签名";
        byte[] data = inputStr.getBytes();

        //产生签名
        byte[] sign = CertificateCoder.sign(data, keyStorePath, alias, passwd);
        System.out.println("签名：\n" + Hex.encodeHexString(sign));

        //验证签名
        boolean status = CertificateCoder.verify(data, sign, certificatePath);
        System.out.println("验证结果：\n" + status);

        Assert.assertTrue(status);
    }

}
