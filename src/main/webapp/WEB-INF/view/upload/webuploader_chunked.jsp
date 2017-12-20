<%@ page language="java" pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="/WEB-INF/include/jspTaglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>文件上传</title>
    <link rel="stylesheet" type="text/css" href="${WEBUPLOADER}/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
</head>
<body>
    <div id="uploader" class="wu-example">
        <!--用来存放文件信息-->
        <div id="thelist" class="uploader-list"></div>
        <div class="btns">
            <div id="picker">选择文件</div>
            <button id="ctlBtn" class="btn btn-default">开始上传</button>
        </div>
    </div>
    <div id="item1"></div>
    <script type="text/javascript" src="${JQUERY}/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="${WEBUPLOADER}/webuploader.js"></script>
    <script type="text/javascript" src="${JS}/upload/webuploader_chunked.js"></script>
</body>
</html>