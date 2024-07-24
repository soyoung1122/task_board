<%--
  Created by IntelliJ IDEA.
  User: soyou
  Date: 2024-07-11
  Time: 오후 1:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="<c:url value='/resources/img/favicon.png' />" type="image/x-icon" />
</head>
<body>
    <%@include file="./include.jsp" %>
    <h3>제목 : ${board.title}</h3>
    <h5>작성자 : ${board.user_name}</h5>
    <h5>작성일 : ${board.reg_date}</h5>
    <c:if test="${board.file != null}">
        <h5>첨부파일 :</h5>
        <a href="/board/download/${board.no}" download="${board.file.file_name}">${board.file.file_name}</a>
    </c:if>
    <div id="data_content"></div>

    <button type="button" onclick="location.href='/board/update/${board.no}'">수정하기</button>
<script>
    $(document.body).ready(function() {
        createElementFromString();
    });

    const createElementFromString = function() {
        const content = '${board.content}';
        const div = document.getElementById('data_content');

        div.insertAdjacentHTML('beforeEnd', content);
    }
</script>
</body>
</html>
