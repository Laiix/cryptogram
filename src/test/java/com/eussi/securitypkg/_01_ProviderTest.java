package com.eussi.securitypkg;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.Provider;
import java.security.Security;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _01_ProviderTest {

    @Test
    public void test() {

        for(int i=1; i<=Security.getProviders().length; i++) {
            Provider provider = Security.getProviders()[i-1];

            System.out.println("NO." + i);

            //当前提供者信息
            System.out.println("Provider：" + provider);
            System.out.println("\tInfo：" + provider.getInfo());
        }

        //添加一个提供者
        Security.addProvider(new BouncyCastleProvider());

        for(int i=1; i<=Security.getProviders().length; i++) {
            Provider provider = Security.getProviders()[i-1];

            System.out.println("NO." + i);

            //添加后当前提供者信息
            System.out.println("Provider：" + provider);
            System.out.println("\tInfo：" + provider.getInfo());
        }

    }
}
