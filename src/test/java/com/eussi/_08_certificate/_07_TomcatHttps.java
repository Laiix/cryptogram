package com.eussi._08_certificate;

import com.eussi._08_certificate.base.HTTPSCoder;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by wangxueming on 2019/6/25.
 */
public class _07_TomcatHttps {
    /**
     Tomcat 7.0.68

     单项认证配置：
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



     双向认证配置：





     */

    private String httpsURL = "https://localhost:8443/TomcatHttps/";
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
                trustStorePass);
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
    }

}
