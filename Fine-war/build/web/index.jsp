<%-- 
    Document   : index
    Created on : Aug 9, 2013, 10:45:59 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testan</title>
    </head>
    <body>
        <c:forEach items="${requestScope.news}" var="item">
            <div>
                ${item.getTitle()} <br />
                ${item.getBody()}
            </div>    
        </c:forEach>
        w${requestScope.no}
    </body>
    
</html>
