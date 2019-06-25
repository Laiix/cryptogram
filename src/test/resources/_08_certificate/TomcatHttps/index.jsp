<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.Enumeration" %>
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
    </body>
</html>

