<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<title>Error</title>
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