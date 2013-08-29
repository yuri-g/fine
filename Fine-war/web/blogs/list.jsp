<%-- 
    Document   : list
    Created on : Aug 28, 2013, 3:30:17 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
         <c:if test="${not empty sessionScope.uSessionBean}">
        Logged in as: ${sessionScope.uSessionBean.getUser().getName()}
    </c:if>
    <body id="content">
        <c:forEach items="${requestScope.entries}" var="item">
            <div class="box">
                ${item.getTitle()} <br />
                ${item.getBody()} <br />
            </div>    
            
        </c:forEach>
       ${requestScope.pages}
        <a href="/logout">
            logout
        </a>
    </body>
</html>
