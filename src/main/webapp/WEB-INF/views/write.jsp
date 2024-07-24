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
    <script type="text/javascript"
            src="/resources/smarteditor/js/HuskyEZCreator.js"
            data-cfasync="false" charset="utf-8"
        ></script>
    <%@include file="./include.jsp" %>
    <form>
        <label for="title">제목 : </label>
        <input type="text" id="title">
        <br>
        <label for="writer">작성자 : </label>
        <input type="text" id="writer">
        <br>
        <label for="file">첨부파일 : </label>
        <input type="file" name="file" id="file" >
        <textarea
                name="editor"
                id="editor"
                rows="20"
                cols="10"
                placeholder="내용을 입력해주세요"
                style="width:500px"
        >
        </textarea>
    </form>
    <button type="button" onclick="insertBoard();">작성하기</button>
    <button type="button" onclick="location.href='/board'">취소</button>

    <script>
        $(document).ready(function () {
            smartEditor();
        });

        let oEditors = [];
        const smartEditor = function() {
            nhn.husky.EZCreator.createInIFrame({
                oAppRef: oEditors,
                elPlaceHolder: "editor",
                sSkinURI: "/resources/smarteditor/SmartEditor2Skin.html",
                fCreator: "createSEditor2"
            })
        }

        const insertBoard = function() {
            oEditors.getById["editor"].exec("UPDATE_CONTENTS_FIELD", [])
            let content = document.getElementById("editor").value;

             const param =  JSON.stringify({
                 title: $("#title").val(),
                 user_name: $("#writer").val(),
                 content: content
            });

            $.ajax({
                url: "/board/write",
                type: "POST",
                data: param,
                contentType : "application/json",
                success: function (no) {
                    let data = $("input[name='file']");
                    let file = data[0].files;

                    if(file.length > 0) {
                        uploadFile(no, file[0]);
                    }

                    alert("게시글이 등록되었습니다.");
                    location.href="/board";
                }
            })
        }

        const uploadFile = function(no, file) {
            let formData = new FormData();
            formData.append("uploadFile", file);

            $.ajax({
                url: "/board/upload/" + no,
                type: "POST",
                data: formData,
                async: true,
                contentType: false,
                processData: false
            })
        }

    </script>
</body>
</html>
