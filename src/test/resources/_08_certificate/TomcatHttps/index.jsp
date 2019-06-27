<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration,java.security.cert.X509Certificate" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv=Content-Type" content="text/html; charset=utf-8">
        <title>eussi.top</title>
    </head>
    <body>
        <p>request属性信息</p>
        <pre>
            <%
                for(Enumeration en = request.getAttributeNames(); en.hasMoreElements();) {
                    String name = (String) en.nextElement();
                    out.println(name);
                    out.println(" = " + request.getAttribute(name));
                    out.println();
                }
            %>
        </pre>
        <p>数字证书信息(双向认证时有值)</p>
        <pre>
            <%
                X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
                for(X509Certificate cert : certs) {
                    out.println();
                    out.println("版本：\t\t" + cert.getVersion());
                    out.println("序列号：\t\t" + cert.getSerialNumber());
                    out.println("颁布者：\t\t" + cert.getIssuerDN().getName());
                    out.println("使用者：\t\t" + cert.getSubjectDN().getName());
                    out.println("签名算法：\t\t" + cert.getSigAlgName());
                    out.println("整数类型：\t\t" + cert.getType());
                    out.println("有效期从：\t\t" + cert.getNotBefore());
                    out.println("至：\t\t" + cert.getNotAfter());
                }
            %>
        </pre>
    </body>
</html>

