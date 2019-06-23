package com.eussi._08_certificate.base;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.File;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

/**
 * PEM文件提取密钥对
 * Created by wangxueming on 2019/6/23.
 */
public class PEMCoder {
    /**
     * 获取KeyPair
     * @param pemFile
     * @param password
     * @return
     * @throws Exception
     */
    public static KeyPair readKeyPair(File pemFile, char[] password) throws Exception {
        //构建PEMParser
        PEMParser pemParser = new PEMParser(new FileReader(pemFile));
        //获得PEM密钥对对象
        Object object = pemParser.readObject();
        //关闭PEMParser
        pemParser.close();
        //构建PEMDecryptorProvider实例
        PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password);
        //构建JcaPEMKeyConverter实例
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        //获得密钥对
        KeyPair kp = null;
        if(object instanceof PEMEncryptedKeyPair) {
            kp = converter.getKeyPair(((PEMEncryptedKeyPair)object).decryptKeyPair(decProv));
        }
        else {
            kp = converter.getKeyPair((PEMKeyPair)object);
        }

        return kp;
    }

    /**
     * 获取KeyPair
     * @param pemFilePath
     * @param password
     * @return
     * @throws Exception
     */
    public static KeyPair readKeyPair(String pemFilePath, char[] password) throws Exception {
        return readKeyPair(new File(pemFilePath), password);
    }

}
