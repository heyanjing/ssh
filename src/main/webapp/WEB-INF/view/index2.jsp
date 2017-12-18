<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CTX" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
    <title>首页title中的内容</title>
    <meta name='description' content='首页head中的内容'>

    <link rel="stylesheet" type="text/css" href="${CTX}/static/css/index.css">
</head>
<body>
<div>首页body中的内容</div>

<div class="index">divdivdivdivdivdiv</div>
<img id="kaptcha" alt=" " src="${CTX}/static/img/index.jpg">
<script type="text/javascript" src="${CTX}/static/js/index.js"></script>
</body>
</html>