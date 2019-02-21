package com.eussi.securitypkg;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangxueming on 2019/2/20.
 */
public class _07_AlgorithmParamtersGenerator {

    //EXCEPTION: java.security.NoSuchAlgorithmException: DES AlgorithmParameterGenerator not available
    @Test
    public void test() {
        //实例化
        try {
            AlgorithmParameterGenerator apg = AlgorithmParameterGenerator.getInstance("DES");
            //初始化
            apg.init(56);

            AlgorithmParameters ap = apg.generateParameters();

            //获得参数字节组
            byte[] b = ap.getEncoded();
            //打印字符串，
            System.out.println(new BigInteger(b).toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
