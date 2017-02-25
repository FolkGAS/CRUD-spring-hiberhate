<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
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

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>

<table>
    <td valign="top">
        <h1>Add a User</h1>
        <c:url var="addAction" value="/users/add"/>

        <form:form action="${addAction}" commandName="user">
            <table>
                <c:if test="${!empty user.id}">
                    <tr>
                        <td>
                            <form:label path="id">
                                <spring:message text="ID"/>
                            </form:label>
                        </td>
                        <td>
                            <form:input path="id" readonly="true" disabled="true"/>
                            <form:hidden path="id"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <form:label path="name">
                            <spring:message text="Name"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="name"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="age">
                            <spring:message text="Age"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="age"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="createdDate">
                            <spring:message text="Created"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="createdDate" readonly="true" disabled="true" value = "${user.createdDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="admin">
                            <spring:message text="Admin"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="admin"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${!empty user.name}">
                            <input type="submit"
                                   value="<spring:message text="Edit User"/>"/>
                        </c:if>
                        <c:if test="${empty user.name}">
                            <input type="submit"
                                   value="<spring:message text="Add User"/>"/>
                        </c:if>
                    </td>
                </tr>
            </table>
        </form:form>
    </td>
    <td width="20"></td>
    <td valign="top">
        <h1>Filter</h1>

        <c:url var="filterUsers" value="/users"/>

        <form:form action="${filterUsers}" commandName="filter">
            <table>
                <tr>
                    <td>
                        <form:label path="idStart">
                            <spring:message text="ID from"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="idStart" size="10" placeholder=""/>
                    </td>
                    <td>
                        <form:label path="idEnd">
                            <spring:message text="to"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="idEnd" size="10" placeholder=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="name">
                            <spring:message text="Name contains"/>
                        </form:label>
                    </td>
                    <td colspan="3">
                        <form:input path="name" size="28" placeholder=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="ageStart">
                            <spring:message text="Age from"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="ageStart" size="10" placeholder=""/>
                    </td>
                    <td>
                        <form:label path="ageEnd">
                            <spring:message text="to"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="ageEnd" size="10" placeholder=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="dateStart">
                            <spring:message text="Created from"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="dateStart" size="10" placeholder="2000/01/01"/>
                    </td>
                    <td>
                        <form:label path="dateEnd">
                            <spring:message text="to"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="dateEnd" size="10" placeholder="3000/01/01"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="admin">
                            <spring:message text="Admin"/>
                        </form:label>
                    </td>
                    <td colspan="3">
                        <form:checkbox path="admin"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit"
                               value="<spring:message text="Filter Users"/>"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </td>
</table>

<h1>List of users</h1>

<script type="text/javascript">
    for (var i = 1; i <= ${pagesCount}; i++)
        document.write(" <a href='/users/" + i + "'>" + i + "</a> &emsp;");
</script>

<c:url var="pages" value="/users"/>

<%--<form:form action="${pages}" commandName="pages">--%>
<%--<c--%>
<%--<c:forEach items="${pagesCount}" var ="page">--%>
<%--<a href="/users">${page}</a>--%>
<%--</c:forEach>--%>
<%--<input type="submit"--%>
<%--value="<spring:message text="page"/>"/>--%>
<%--</form:form>--%>

<br/>
<c:if test="${!empty listUsers}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Name</th>
            <th width="120">Age</th>
            <th width="120">Admin</th>
            <th width="120">Date</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><a href="/userData/${user.id}" target="_blank">${user.name}</a></td>
                <td>${user.age}</td>
                <td>${user.admin}</td>
                <td>${user.createdDate}</td>
                <td><a href="<c:url value='/edit/${user.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/remove/${user.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
