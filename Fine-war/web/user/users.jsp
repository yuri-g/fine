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
        <title>JSP Page</title>
    </head>
    <body>
        <c:forEach items="${requestScope.users}" var="item">
            <div>
                ${item.getName()} <br />
                ${item.getEmail()} <br />
                ${item.getPassword()} <br />
            </div>    
        </c:forEach>
         
    </body>
</html>
