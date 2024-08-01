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
        <input type="text" id="title" value="${board.title}">
        <br>
        <label for="writer">작성자 : </label>
        <input type="text" id="writer" value="${board.user_name}" disabled="disabled">
        <br>
        <label for="file">첨부파일 : </label>
        <input type="file" id="file" name="file">
        <c:if test="${board.file.file_name != null}">
            ${board.file.file_name}
            <button type="button" onclick="setFileStatus(this)">삭제</button>
        </c:if>
        <input hidden="hidden" id="deleteStatus">

        <textarea
                name="editor"
                id="editor"
                rows="20"
                cols="10"
                placeholder="내용을 입력해주세요"
                style="width:500px"
        >
            ${board.content}
        </textarea>
    </form>
    <button type="button" onclick="updateBoard()">수정하기</button>
    <button type="button" onclick="location.href='/board/${board.no}'">취소</button>

    <script>
        let deleteStatus = false;
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

        const setFileStatus = function(btn) {
            deleteStatus = !deleteStatus;
            if(deleteStatus) {
                $(btn).html("취소");
            } else {
                $(btn).html("삭제");
            }
        }

        const updateBoard = function() {
            oEditors.getById["editor"].exec("UPDATE_CONTENTS_FIELD", [])
            let content = document.getElementById("editor").value;

            const param =  JSON.stringify({
                no: "${board.no}",
                title: $("#title").val(),
                user_name: $("#writer").val(),
                content: content
            });

            $.ajax({
                url: "/board/update",
                type: "PUT",
                data: param,
                contentType : "application/json",
                success: function () {
                    let data = $("input[name='file']");
                    let file = data[0].files;

                    if(file.length > 0) {
                        updateFile("${board.no}", file[0]);
                    } else {
                        if(deleteStatus) {
                            deleteFile("${board.no}");
                        }
                    }

                    alert("게시글이 수정되었습니다.");
                    location.href="/board/${board.no}";
                }
            })
        }

        const updateFile = function(no, file) {
            let formData = new FormData();
            formData.append("updateFile", file);

            $.ajax({
                url: "/board/update/" + no + "/file",
                type: "PUT",
                data: formData,
                async: true,
                contentType: false,
                processData: false
            })
        }

        const deleteFile = function(no) {
            $.ajax({
                url: "/board/delete/" + no + "/file",
                type: "DELETE",
                async: true,
                contentType: false,
                processData: false,
                success: function() {
                    console.log("삭제 완료");
                    location.href="/board";
                }
            })
        }
    </script>
</body>
</html>
