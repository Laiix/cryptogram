package com.eussi.securitypkg;

import org.junit.Test;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _02_SecurityTest {

    @Test
    public void test() {
        for(int i=1; i<=Security.getProviders().length; i++) {
            Provider provider = Security.getProviders()[i-1];
            System.out.println("NO." + i);

            //当前提供者信息
            System.out.println("Provider：" + provider);

            //遍历提供者set实体
            System.out.println("\tKey:");
            for(Map.Entry<Object, Object> entry : provider.entrySet()) {
                System.out.println("\t\t" + entry.getKey());
            }
        }

    }
}
