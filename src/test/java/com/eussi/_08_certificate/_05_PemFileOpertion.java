package com.eussi._08_certificate;

import com.eussi._08_certificate.base.PEMCoder;
import junit.framework.Assert;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.*;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _05_PemFileOpertion {
    /**
     .DER .CER，文件是二进制格式，只保存证书，不保存私钥。
     .PEM，一般是文本格式，可保存证书，可保存私钥。
     .CRT，可以是二进制格式，可以是文本格式，与 .DER 格式相同，不保存私钥。
     .PFX .P12，二进制格式，同时包含证书和私钥，一般有密码保护。
     .JKS，二进制格式，同时包含证书和私钥，一般有密码保护。

     DER
     该格式是二进制文件内容，Java 和 Windows 服务器偏向于使用这种编码格式。
     OpenSSL 查看
        openssl x509 -in certificate.der -inform der -text -noout
     转换为 PEM：
        openssl x509 -in cert.crt -inform der -outform pem -out cert.pem

     PEM
     Privacy Enhanced Mail，一般为文本格式，以 -----BEGIN... 开头，以 -----END... 结尾。中间的内容是 BASE64 编码。这种格式可以保存证书和私钥，有时我们也把PEM 格式的私钥的后缀改为 .key 以区别证书与私钥。具体你可以看文件的内容。
     这种格式常用于 Apache 和 Nginx 服务器。
     OpenSSL 查看：
        openssl x509 -in certificate.pem -text -noout
     转换为 DER：
        openssl x509 -in cert.crt -outform der -out cert.der

     CRT
     Certificate 的简称，有可能是 PEM 编码格式，也有可能是 DER 编码格式。如何查看请参考前两种格式。

     PFX
     Predecessor of PKCS#12，这种格式是二进制格式，且证书和私钥存在一个 PFX 文件中。一般用于 Windows 上的 IIS 服务器。改格式的文件一般会有一个密码用于保证私钥的安全。
     OpenSSL 查看：
        openssl pkcs12 -in for-iis.pfx
     转换为 PEM：
        openssl pkcs12 -in for-iis.pfx -out for-iis.pem -nodes

     JKS
     Java Key Storage，很容易知道这是 JAVA 的专属格式，利用 JAVA 的一个叫 keytool 的工具可以进行格式转换。一般用于 Tomcat 服务器。


     # 操作
     [root@app2 TestCa]# openssl pkcs12 -in certs/ca.p12 -out certs/ca.pem -nodes
     Enter Import Password:
     MAC verified OK

     [root@app2 TestCa]# openssl pkcs12 -in certs/server.p12 -out certs/server.pem -nodes
     Enter Import Password:
     MAC verified OK

     [root@app2 TestCa]# openssl pkcs12 -in certs/client.p12 -out certs/client.pem -nodes
     Enter Import Password:
     MAC verified OK


     */

    private String passwd = "123456";
    private String pemFilePath = "src/test/resources/_08_certificate/openssl/TestCa/private/ca.key.pem";
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte[] data;

    @Before
    public void intiKeyPair() {
        data = "PEM".getBytes();
        //若未在java.security中配置BC,添加以下代码
        Security.addProvider(new BouncyCastleProvider());

        KeyPair kp;
        try {
            kp = PEMCoder.readKeyPair(pemFilePath, passwd.toCharArray());
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥加密，私钥解密
     */
    @Test
    public void encryptAndDecrypt() {
        try {
            //私钥加密
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypt = cipher.doFinal(data);
            //公钥加密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypt = cipher.doFinal(encrypt);
            System.out.println(new String(decrypt));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 私钥加密，公钥解密
     */
    @Test
    public void encryptAndDecrypt2() {
        try {
            //私钥加密
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encrypt = cipher.doFinal(data);
            //公钥加密
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decrypt = cipher.doFinal(encrypt);
            System.out.println(new String(decrypt));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void sign() {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(data);
            byte[] sign = signature.sign();

            //使用公钥对签名校验
            signature.initVerify(publicKey);
            signature.update(data);
            //验证
            boolean status = signature.verify(sign);
            System.out.println(status);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
