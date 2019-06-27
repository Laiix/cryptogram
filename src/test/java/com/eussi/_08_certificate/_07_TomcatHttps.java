package com.eussi._08_certificate;

import com.eussi._08_certificate.base.HTTPSCoder;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.net.URL;

/**
 * Created by wangxueming on 2019/6/25.
 */
public class _07_TomcatHttps {
    /**
     Tomcat 7.0.68

     单向认证配置：
        <Connector
            port="8443"
            protocol="org.apache.coyote.http11.Http11Protocol"
            maxThreads="150"
            SSLEnabled="true"
            scheme="https"
            secure="true"
            clientAuth="false"
            sslProtocol="TLS"
            keystoreFile="conf/eussi.keystore"
            keystorePass="123456"
        />

        为使得 Https协议配置生效,我们需要将密钥库文件参数 keystoreFile指向密钥库文件,并
        设定密钥库密码参数 keystorePass,密钥库类型参数 keystoreType默认值为“JKS”。
        如果不显示配置信任库参数,信任库文件参数 truststoreFile默认将指向密钥库文件,信任
        库密码参数truststorePass默认指向密钥库密码,信任库类型参数 truststoreType默认值为
        “JKS”。
        这里我们需要注意客户端验证参数clientauth,当前默认值为“false”。构建双向认证服务
        时需将其置为“true”,并修改密钥库参数和信任库参数。

        将resources目录下TomcatHtts目录放在tomcat的webapp目录下，访问：
            https://localhost:8443/TomcatHttps/
        显示如下内容：
             request属性信息
             javax.servlet.request.ssl_session
             = 5d120ade1350377b40e1f9d86f69b13e5a6439a3bceff5f5cb63e5bfbc01e0c2
             javax.servlet.request.ssl_session_id
             = 5d120ade1350377b40e1f9d86f69b13e5a6439a3bceff5f5cb63e5bfbc01e0c2
             javax.servlet.request.ssl_session_mgr
             = org.apache.tomcat.util.net.jsse.JSSESupport@23aa7439
             javax.servlet.request.key_size
             = 128
             javax.servlet.request.cipher_suite
             = TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
             org.apache.tomcat.util.net.secure_protocol_version
             = TLSv1.2


            javax.servlet.request.ssl_session:当前SSL/TLS协议的会话ID。
            javax.servlet.request.key_size:当前加密算法所使用的密钥长度。
            javax.servlet.request.cipher_suite:当前SSL/TLS协议所使用的加密套件
            通过javax.servlet.request.cipher_suite属性值(TLS_RSA_WITH_AES_128_CBC_SHA),我
            们获知夲次连接所使用的协议为TLS协议,非对称加密算法为RSA算法,对称加密算法为AES
            算法,消息摘要算法为SHA1。其中,对称加密算法要求密钥长度为128位,且工作模式为CBC
            模式。
            对于使用何种协议、对称加密算法以及消息摘要算法,需由客户端与服务器端交互通过
            SSL/TLS握手协议确定。不同的客户端同一时间访问同一服务时,将有可能使用不同的协议或
            算法,唯一可以确定的是通过数字证书确定的非对称加密算法

     程序验证：
        注意程序访问的URL，需要使用证书中设置的域名（可配置hosts文件进行测试）进行访问，否则会失败

     */

    //private String httpsURL = "https://localhost:8443/TomcatHttps/";
    private String httpsURL = "https://www.eussi.top:8443/TomcatHttps/";
    private String keyStorePath = "src/test/resources/_08_certificate/keytool/eussi.keystore";
    private String keyStorePass = "123456";
    private String trustStorePath = "src/test/resources/_08_certificate/keytool/eussi.keystore";
    private String trustStorePass = "123456";

    @Test
    public void Test() throws Exception {
        //建立HTTPS连接
        URL url = new URL(httpsURL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        //打开输入流
        httpsURLConnection.setDoInput(true);
        //为HttpsURLConnection配置SSLSocketFactory
        HTTPSCoder.configSSLSocketFacotry(httpsURLConnection,
                keyStorePath,
                keyStorePass,
                trustStorePath,
                trustStorePass,
                null);
        //鉴别内容长度
        int length = httpsURLConnection.getContentLength();
        byte[] data = null;
        //如果内容长度为-1，则放弃解析
        if(length!=-1) {
            DataInputStream dis = new DataInputStream(httpsURLConnection.getInputStream());
            data = new byte[length];
            dis.readFully(data);
            dis.close();
            System.out.println(new String(data));
        }
        //关闭连接
        httpsURLConnection.disconnect();
        //验证
        Assert.assertNotNull(data);


        /**
         结果展示：
             <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/loose.dtd">
             <html>
             <head>
             <meta http-equiv=Content-Type" content="text/html; charset=utf-8">
             <title>eussi.top</title>
             </head>
             <body>
             <p>request属性信息</p>
             <pre>
             javax.servlet.request.ssl_session
             = 5d122a2dc9470fed9b3afb469c4dcacef0068a6d7760237dfbc4c2d5840c88e5

             javax.servlet.request.ssl_session_id
             = 5d122a2dc9470fed9b3afb469c4dcacef0068a6d7760237dfbc4c2d5840c88e5

             javax.servlet.request.ssl_session_mgr
             = org.apache.tomcat.util.net.jsse.JSSESupport@a01eb4c

             javax.servlet.request.key_size
             = 256

             javax.servlet.request.cipher_suite
             = TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA

             org.apache.tomcat.util.net.secure_protocol_version
             = TLSv1


             </pre>
             </body>
             </html>
         */
    }

    /**
     Tomcat 7.0.68

         双向认证配置：
             <Connector
                 port="8443"
                 protocol="org.apache.coyote.http11.Http11Protocol"
                 maxThreads="150"
                 SSLEnabled="true"
                 scheme="https"
                 secure="true"
                 clientAuth="true"
                 sslProtocol="TLS"
                 keystoreFile="conf/server.p12"
                 keystorePass="123456"
                 keystoreType="PKCS12"
                 truststoreFile="conf/ca.p12"
                 truststorePass="123456"
                 truststoreType="PKCS12"
             />

         注意双向认证通过浏览器访问时需要导入根证书，或者客户端证书
            URL:https://www.eussi.top:8443/TomcatHttps/

        返回信息：
             request属性信息
             javax.servlet.request.ssl_session
             = 5d1472ac3f68a0039bf0650dcc6645240cd5139d6a0cd05e0fb5a4f7d4880ac3
             javax.servlet.request.ssl_session_id
             = 5d1472ac3f68a0039bf0650dcc6645240cd5139d6a0cd05e0fb5a4f7d4880ac3
             javax.servlet.request.ssl_session_mgr
             = org.apache.tomcat.util.net.jsse.JSSESupport@46916631
             javax.servlet.request.key_size
             = 128
             javax.servlet.request.X509Certificate
             = [Ljava.security.cert.X509Certificate;@73cc3f28
             javax.servlet.request.cipher_suite
             = TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
             org.apache.tomcat.util.net.secure_protocol_version
             = TLSv1.2

        此时多了一个属性，javax.servlet.request.X509Certificate，该值是一个列表，指向客户证书列表，打印出值：

         request属性信息
         javax.servlet.request.ssl_session
         = 5d1476bab0dba5301cc2269a6792a3e7bca96d65985aa564823ae69073295284
         javax.servlet.request.ssl_session_id
         = 5d1476bab0dba5301cc2269a6792a3e7bca96d65985aa564823ae69073295284
         javax.servlet.request.ssl_session_mgr
         = org.apache.tomcat.util.net.jsse.JSSESupport@49357096
         javax.servlet.request.key_size
         = 128
         javax.servlet.request.X509Certificate
         = [Ljava.security.cert.X509Certificate;@5372ad66
         javax.servlet.request.cipher_suite
         = TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA
         org.apache.tomcat.util.net.secure_protocol_version
         = TLSv1.2

         数字证书信息(双向认证时有值)
         版本：		3
         序列号：		1
         颁布者：		CN=*.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
         使用者：		CN=eussi, OU=eussi, O=eussi, ST=SH, C=CN
         签名算法：		SHA256withRSA
         整数类型：		X.509
         有效期从：		Sun Jun 23 13:46:39 CST 2019
         至：		Wed Jun 20 13:46:39 CST 2029

     */

    @Test
    public void Test1() throws Exception {
        //建立HTTPS连接
        URL url = new URL(httpsURL);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        //打开输入流
        httpsURLConnection.setDoInput(true);

        keyStorePath = "src/test/resources/_08_certificate/openssl/TestCa/certs/server.p12";
        trustStorePath = "src/test/resources/_08_certificate/openssl/TestCa/certs/ca.p12";

        //为HttpsURLConnection配置SSLSocketFactory
        HTTPSCoder.configSSLSocketFacotry(httpsURLConnection,
                keyStorePath,
                keyStorePass,
                trustStorePath,
                trustStorePass,
                "PKCS12");
        //鉴别内容长度
        int length = httpsURLConnection.getContentLength();
        byte[] data = null;
        //如果内容长度为-1，则放弃解析
        if(length!=-1) {
            DataInputStream dis = new DataInputStream(httpsURLConnection.getInputStream());
            data = new byte[length];
            dis.readFully(data);
            dis.close();
            System.out.println(new String(data));
        }
        //关闭连接
        httpsURLConnection.disconnect();
        //验证
        Assert.assertNotNull(data);


        /**
         结果展示：
             <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/loose.dtd">
             <html>
             <head>
             <meta http-equiv=Content-Type" content="text/html; charset=utf-8">
             <title>eussi.top</title>
             </head>
             <body>
             <p>request属性信息</p>
             <pre>
             javax.servlet.request.ssl_session
             = 5d147a56ce5c7c117425267af0cfbd8066e17a0075d248b9ede74f07b51cc10d

             javax.servlet.request.ssl_session_id
             = 5d147a56ce5c7c117425267af0cfbd8066e17a0075d248b9ede74f07b51cc10d

             javax.servlet.request.ssl_session_mgr
             = org.apache.tomcat.util.net.jsse.JSSESupport@23849937

             javax.servlet.request.key_size
             = 256

             javax.servlet.request.X509Certificate
             = [Ljava.security.cert.X509Certificate;@5666fc18

             javax.servlet.request.cipher_suite
             = TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA

             org.apache.tomcat.util.net.secure_protocol_version
             = TLSv1


             </pre>
             <p>数字证书信息(双向认证时有值)</p>
             <pre>
             版本：		1
             序列号：		10037069873262539164
             颁布者：		CN=*.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             使用者：		CN=www.eussi.top, OU=eussi, O=eussi, L=SH, ST=SH, C=CN
             签名算法：		SHA1withRSA
             整数类型：		X.509
             有效期从：		Sun Jun 23 13:32:23 CST 2019
             至：		Wed Jun 20 13:32:23 CST 2029

             </pre>
             </body>
             </html>
         */
    }

    /**

     以上内容中,通过 OpenSsL产生了多种数字证书,并构建了双向认证服务。那么我们
     能不能用 KeyTool构建一套双向认证服务呢?其实,这也是完全可以的。
     在Java环境中有密钥库和信任库之分。密钥库存储自身的密钥、证书等信
     息;而信任库存储来自外界的数字证书等信息。当甲乙双方完成自身密钥库初始化工作后,如
     果导入了对方的数字证书,双方就具备了互信的条件,也就可以构建双向认证服务了。

     */

}
