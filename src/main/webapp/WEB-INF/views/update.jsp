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
<body>
    <%@include file="./include.jsp" %>
    <form>
        <label for="title">제목 : </label>
        <input type="text" id="title" value="${board.title}">
        <br>
        <label for="writer">작성자 : </label>
        <input type="text" id="writer" value="${board.user_name}" disabled="disabled">
        <br>
        <label for="file">첨부파일 : </label>
        <input type="file" id="file">
    </form>
    <button type="button" onclick="updateBoard">수정하기</button>
    <button type="button" onclick="location.href='/board/${board.no}'">취소</button>

    <script>
        const updateBoard = function() {

        }
    </script>
</body>
</html>
