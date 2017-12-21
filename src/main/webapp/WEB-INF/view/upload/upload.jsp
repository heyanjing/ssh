<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>文件上传</title>
</head>
<body>
<h1>Spring MVC - File Upload Example</h1>
<hr />

<h3>Single file Upload</h3>
<form action="${CTX}/file/upload/singleFileUpload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Select File</td>
            <td><input type="file" name="file"></td>
            <td><button type="submit">Upload</button></td>
        </tr>
    </table>
</form>
<br />
<hr />
<h3>Multiple file Upload</h3>
<form action="${CTX}/file/upload/multipleFileUpload" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Select Files</td>
            <td><input type="file" name="files" multiple="multiple"></td>
            <td><button type="submit">Upload</button></td>
        </tr>
    </table>
</form>
<br>
<hr />
<span style="color: red; font-size: 14px;">${msg}</span>
</body>
</html>