<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: 11916
  Date: 2019/8/21
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>今天是</h1><%=new Date()%><h1>然而你又404</h1>
<h1>你的IP</h1><%=request.getLocalAddr()%>
<h1><strong>你已经很成熟了，要学会自己摆脱我，明白不</strong></h1>
</body>
</html>
