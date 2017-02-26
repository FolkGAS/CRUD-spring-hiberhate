<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Users Page</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>

<table>
    <tr>
        <td width="40"></td>
        <td valign="top">
            <jsp:include page="add.jsp"/>
        </td>
        <td width="40"></td>
        <td valign="top">
            <jsp:include page="filter.jsp"/>
        </td>
    </tr>
</table>

<h1>List of users</h1>

<jsp:include page="paging.jsp"/>

<c:if test="${!empty listUsers}">
    <table class="tg">
        <tr>
            <th width="40">ID</th>
            <th width="200">Name</th>
            <th width="40">Age</th>
            <th width="20">Admin</th>
            <th width="150">Date</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listUsers}"
                   var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>
                    <c:if test="${true == user.admin}">
                        <img src="/resources/check.png"/>
                    </c:if>
                    <c:if test="${false == user.admin}">
                        <img src="/resources/uncheck.png"/>
                    </c:if>
                </td>
                <td><fmt:formatDate value="${user.createdDate}"
                                    pattern="yyyy/MM/dd '&nbsp' hh:mm:ss"/> </td>
                <td><a href="<c:url value='/edit/${user.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/remove/${user.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
