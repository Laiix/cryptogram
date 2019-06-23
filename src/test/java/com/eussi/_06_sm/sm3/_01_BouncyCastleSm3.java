package com.eussi._06_sm.sm3;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Security;

/**
 * Created by wangxueming on 2019/2/24.
 */
public class _01_BouncyCastleSm3 {

    @Test
    public void test() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            //原文
            byte[] input = "test".getBytes();
            SM3Digest digest = new SM3Digest();
            digest.update(input, 0, input.length);
            byte[] hash = new byte[digest.getDigestSize()];
            digest.doFinal(hash, 0);
            System.out.println("testSM3算法Hash："+ Base64.toBase64String(hash));

            //带有密钥的SM3
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            //随意产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            KeyParameter keyParameter = new KeyParameter(secretKey.getEncoded());
            digest = new SM3Digest();
            HMac mac = new HMac(digest);
            mac.init(keyParameter);
            mac.update(input, 0, input.length);
            byte[] result = new byte[mac.getMacSize()];
            mac.doFinal(result, 0);
            System.out.println("testSM3算法Mac：" + Base64.toBase64String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
