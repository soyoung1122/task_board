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
    <div>
        <h3>게시판</h3>
        <button type="button" onclick="location.href='/board/write'">글쓰기</button>
        <table class="tb tb_col">
            <colgroup>
                <col style="width:50px;"/><col style="width:7.5%;"/><col style="width:auto;"/><col style="width:10%;"/><col style="width:15%;"/><col style="width:7.5%;"/>
            </colgroup>
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성자</th>
                    <th scope="col">등록일</th>
                </tr>
            </thead>
            <tbody id="list">
                <c:if test="${response.list == null}">
                    조회 결과가 없습니다.
                </c:if>
                <c:if test="${response.list != null}">
                    <c:forEach items="${response.list}" var="item">
                        <tr>
                            <td>${item.rownumber}</td>
                            <td><a href="/board/${item.no}">${item.title}</a></td>
                            <td>${item.user_name}</td>
                            <td>${item.reg_date}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
        <div class="paging">
            <c:if test="${response.pagination.existPrevPage == 'true'}">
                <a href="javascript:void(0);" onclick="movePage(1)" class="page_bt first"><<</a>
                <a href="javascript:void(0);" onclick="movePage(${response.pagination.startPage - 1})" class="page_bt prev"><</a>
            </c:if>
            <c:forEach begin="${response.pagination.startPage}" end="${response.pagination.endPage}" var="i">
                <c:choose>
                    <c:when test="${i != params.page}">
                        <a href="javascript:void(0);" onclick="movePage(${i});">${i}</a>
                    </c:when>
                    <c:when test="${i == params.page}">
                        <span class="on">${i}</span>
                    </c:when>
                </c:choose>
            </c:forEach>
            <c:if test="${response.pagination.existNextPage} == 'true'">
                <a href="javascript:void(0);" onclick="movePage(${response.pagination.endPage + 1});" class="page_bt next">></a>
                <a href="javascript:void(0);" onclick="movePage(${response.pagination.totalPageCount});" class="page_bt last">>></a>
            </c:if>
        </div>
    </div>
    <script>
        function movePage(page) {

            const queryParams = {
                page: (page) ? page : 1,
                countList: "${params.countList}",
                countPage: "${params.countPage}"
            }

            location.href = location.pathname + '?' + new URLSearchParams(queryParams).toString();
        }
    </script>
</body>
</html>
