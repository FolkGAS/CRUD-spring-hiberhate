<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>--%>
<%--<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Manager for JavaRush</title>
</head>
<body>
<h3>USER MANAGER</h3>
<br/>
<a href="<c:url value="/users"/>">Users list</a>



<%--<c:url var="test" value="/users"/>--%>

<%--<form:form action="${test}" commandName="filter">--%>
    <%--<td>--%>
        <%--<form:label path="idStart">--%>
            <%--<spring:message text="ID from"/>--%>
        <%--</form:label>--%>
    <%--</td>--%>
    <%--<td>--%>
        <%--<form:input path="idStart" size="10" placeholder=""/>--%>
    <%--</td>--%>
<%--</form:form>--%>

<br/>
</body>
</html>