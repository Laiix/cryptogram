package com.eussi.jca_jce.securitypkg;

import java.io.FileInputStream;
import java.security.CodeSigner;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.util.Date;

/**
 * demo待完善，后面看到Https时在完善
 * Created by wangxueming on 2019/4/1.
 */
public class _14_CodeSigner {//封装了代码签名者的信息，且他是不可变的，我们称之为代码签名，他和数字时间戳紧密相连

    public static void main(String[] args) {
        try {
            //构建certificateFactory对象，并指定证书类型为X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            //生成CertPath对象
            //java.security.cert.CertificateException: Could not parse certificate: java.io.IOException: Empty input
            //待解决问题
            CertPath certPath = certificateFactory.generateCertPath(new FileInputStream("src/test/resources/aliyun.cer"));
            //实例化数字时间戳
            Timestamp timestamp = new Timestamp(new Date(), certPath);
            System.out.println(timestamp.toString());

            //实例化CodeSigner对象
            CodeSigner codeSigner = new CodeSigner(certPath, timestamp);
            //获得对比结果
            boolean status = codeSigner.equals(new CodeSigner(certPath, timestamp));
            System.out.println("对比结果：" + status);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
