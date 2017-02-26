<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url var="pages" value="/users"/>

<form:form action="${pages}" commandName="filter">
    <table>
        <c:url var="pages" value="/users"/>

        <form:form action="${pages}" commandName="filter">
            <td>
                <c:if test="${filter.page < 1}">
                    <input type="hidden"
                           name="page"
                           value="${1}"/>
                </c:if>
                <c:if test="${filter.page > 1}">
                    <input type="hidden"
                           name="page"
                           value="${filter.page - 1}"/>
                </c:if>
                <input type="submit"
                       name="prev"
                       value="<spring:message text="prev"/>"/>
                    ${filter.page}
            </td>
        </form:form>

        <form:form action="${pages}" commandName="filter">
            <td>
                <c:if test="${filter.page >= pagesCount}">
                    <input type="hidden"
                           name="page"
                           value="${pagesCount}"/>
                </c:if>
                <c:if test="${filter.page < pagesCount}">
                    <input type="hidden"
                           name="page"
                           value="${filter.page + 1}"/>
                </c:if>
                <input type="submit"
                       name="next"
                       value="<spring:message text="next"/>"/>
            </td>
        </form:form>
    </table>
</form:form>

<form:form action="${pages}" commandName="filter">
    <td>
        <form:select path="usersPerPage"
                     size="1"
                     onchange="if (this.selectedIndex) this.form.submit()">
            <option value="${filter.usersPerPage}">${filter.usersPerPage}</option>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="${pagesCount*filter.usersPerPage}">all</option>
        </form:select>
    </td>
    <c:forEach begin="1"
               end="${pagesCount}"
               step="1"
               varStatus="i">
        <td>
            <input type="submit"
                   name="page"
                   value="${i.index}"/>
        </td>
    </c:forEach>
</form:form>