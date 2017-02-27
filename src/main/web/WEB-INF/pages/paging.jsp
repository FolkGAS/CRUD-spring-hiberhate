<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <style type="text/css">
        .otherPage {
            width: 30px;
            background-color: white;
            border: black;
            border-style: solid;
            border-width: 1px;
        }

        .thisPage {
            width: 30px;
            background-color: darkgray;
            border-style: solid;
            border-width: 1px;
        }

        .longPage {
            width: 50px;
            background-color: white;
            border: black;
            border-style: solid;
            border-width: 1px;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <c:url var="pages" value="/users"/>

        <form:form action="${pages}" commandName="filter">
            <td>
                <form:select path="usersPerPage"
                             size="1"
                             onchange="if (this.selectedIndex) this.form.submit()">
                    <option value="${filter.usersPerPage}">${filter.usersPerPage}</option>
                    <option value="1">1</option>
                    <option value="5">5</option>
                    <option value="10">10</option>
                    <option value="20">20</option>
                    <option value="${pagesCount*filter.usersPerPage}">all</option>
                    <input type="hidden"
                           name="page"
                           value="${1}"/>
                </form:select>
            </td>
        </form:form>
        <form:form action="${pages}" commandName="filter">
            <td>
                <input type="hidden"
                       name="page"
                       value="${1}"/>
                <input class="longPage" type="submit"
                       name="first"
                       value="<spring:message text="first"/>"/>
            </td>
        </form:form>
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
                <input class="longPage" type="submit"
                       name="prev"
                       value="<spring:message text="prev"/>"/>
            </td>
        </form:form>
        <form:form action="${pages}" commandName="filter">
            <c:if test="${pagesCount < 12}">
                <c:forEach begin="1"
                           end="${pagesCount}"
                           step="1"
                           varStatus="i">
                    <td>
                        <c:if test="${i.index == filter.page}">
                            <input class="thisPage" type="submit"
                                   name="page"
                                   value="${i.index}"/>
                        </c:if>
                        <c:if test="${i.index != filter.page}">
                            <input class="otherPage" type="submit"
                                   name="page"
                                   style="width:30px"
                                   value="${i.index}"/>
                        </c:if>
                    </td>
                </c:forEach>
            </c:if>
            <c:if test="${pagesCount > 11}">
                <c:if test="${filter.page < 6}">
                    <c:forEach begin="${1}"
                               end="${11}"
                               step="1"
                               varStatus="i">
                        <td>
                            <c:if test="${i.index == filter.page}">
                                <input class="thisPage" type="submit"
                                       name="page"
                                       value="${i.index}"/>
                            </c:if>
                            <c:if test="${i.index != filter.page}">
                                <input class="otherPage" type="submit"
                                       name="page"
                                       style="width:30px"
                                       value="${i.index}"/>
                            </c:if>
                        </td>
                    </c:forEach>
                </c:if>
                <c:if test="${filter.page > 5 && filter.page < pagesCount-4}">
                    <c:forEach begin="${filter.page-5}"
                               end="${filter.page+5}"
                               step="1"
                               varStatus="i">
                        <td>
                            <c:if test="${i.index == filter.page}">
                                <input class="thisPage" type="submit"
                                       name="page"
                                       value="${i.index}"/>
                            </c:if>
                            <c:if test="${i.index != filter.page}">
                                <input class="otherPage" type="submit"
                                       name="page"
                                       style="width:30px"
                                       value="${i.index}"/>
                            </c:if>
                        </td>
                    </c:forEach>
                </c:if>
                <c:if test="${filter.page > pagesCount-5}">
                    <c:forEach begin="${pagesCount-10}"
                               end="${pagesCount}"
                               step="1"
                               varStatus="i">
                        <td>
                            <c:if test="${i.index == filter.page}">
                                <input class="thisPage" type="submit"
                                       name="page"
                                       value="${i.index}"/>
                            </c:if>
                            <c:if test="${i.index != filter.page}">
                                <input class="otherPage"
                                       type="submit"
                                       name="page"
                                       style="width:30px"
                                       value="${i.index}"/>
                            </c:if>
                        </td>
                    </c:forEach>
                </c:if>
            </c:if>
        </form:form>
        </td>
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
                <input class="longPage"
                       type="submit"
                       name="next"
                       value="<spring:message text="next"/>"/>
            </td>
        </form:form>
        <form:form action="${pages}" commandName="filter">
            <td>
                <input type="hidden"
                       name="page"
                       value="${pagesCount}"/>
                <input class="longPage"
                       type="submit"
                       name="last"
                       value="<spring:message text="last"/>"/>
            </td>
        </form:form>
    </tr>
</table>
</body>
</html>