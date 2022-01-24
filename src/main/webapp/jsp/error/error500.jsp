<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 19.12.2021
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<hr>
<head>
    <title>Error 500</title>
</head>
<br>
Request From -> ${pageContext.errorData.requestURI}
<hr/>
Exception -> ${pageContext.exception}
<hr/>
Exception Status -> ${pageContext.errorData.statusCode}
<hr/>
Servlet Name -> ${pageContext.errorData.servletName}
<hr/>
<a href="${pageContext.request.contextPath}/index.jsp">backToStartPage</a>
</body>
</html>