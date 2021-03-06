<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <style type="text/css">

    </style>
</head>
<body>
<h1>Add a User</h1>
<c:url var="addAction" value="/users/add"/>
<c:set var="stringSize" value="50"/>

<form:form action="${addAction}" commandName="user">
    <table width="100%">
        <c:if test="${!empty user.id}">
            <%--USER CAN'T CHANGE ID--%>
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id"
                                size="${stringSize}"
                                readonly="true"
                                disabled="true"/>
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
                <form:input path="name"
                            size="${stringSize}"
                            required="true"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="age">
                    <spring:message text="Age"/>
                </form:label>
            </td>
                <%--CHECK FOR NUMBERS STARTS FROM 1-9 --%>
            <td>
                <form:input path="age"
                            size="${stringSize}"
                            pattern="[1-9]+\d*"
                            required="true"
                            title="positive number"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="createdDate">
                    <spring:message text="Created"/>
                </form:label>
            </td>
                <%--USER CAN'T CHANGE DATE--%>
            <td>
                <fmt:formatDate value="${user.createdDate}"
                                pattern="yyyy/MM/dd hh:mm:ss"
                                var="formattedDate"/>

                <form:input path="createdDate"
                            size="${stringSize}"
                            readonly="true"
                            disabled="true"
                            value="${formattedDate}"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="admin">
                    <spring:message text="Admin"/>
                </form:label>
            </td>
            <td>
                <form:checkbox path="admin"/>
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
</body>
</html>