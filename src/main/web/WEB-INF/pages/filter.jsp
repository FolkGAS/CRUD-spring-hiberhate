<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>

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
                <form:input path="idStart" size="10" pattern="\d*"/>
            </td>
            <td>
                <form:label path="idEnd">
                    <spring:message text="to"/>
                </form:label>
            </td>
            <td>
                <form:input path="idEnd" size="10" pattern="\d*"/>
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
                <form:input path="ageStart" size="10" pattern="\d*"/>
            </td>
            <td>
                <form:label path="ageEnd">
                    <spring:message text="to"/>
                </form:label>
            </td>
            <td>
                <form:input path="ageEnd" size="10" pattern="\d*"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="dateStart">
                    <spring:message text="Created from"/>
                </form:label>
            </td>
            <td>
                <form:input path="dateStart" size="10" placeholder="2000/01/01" pattern="\d{4}/\d{2}/\d{2}"/>
            </td>
            <td>
                <form:label path="dateEnd">
                    <spring:message text="to"/>
                </form:label>
            </td>
            <td>
                <form:input path="dateEnd" size="10" placeholder="3000/01/01" pattern="\d{4}/\d{2}/\d{2}"/>
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
            <td>
                <input type="hidden" name="page" value="${1}"/>
                <input type="submit"
                       value="<spring:message text="Filter Users"/>"/>
            </td>
        </tr>
    </table>
</form:form>

