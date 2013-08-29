<%-- 
    Document   : users
    Created on : Aug 18, 2013, 11:42:49 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <c:if test="${not empty sessionScope.uSessionBean}">
        Logged in as: ${sessionScope.uSessionBean.getUser().getName()}
    </c:if>
    <body>
        <c:forEach items="${requestScope.users}" var="item">
            <div>
                ${item.getName()} <br />
                ${item.getEmail()} <br />
                ${item.getPassword()} <br /> 
                <a href="/users?id=${item.getId()}">Blog entries</a> <br />
            </div>    
        </c:forEach>
        <a href="/logout">
            logout
        </a>
    </body>
</html>
