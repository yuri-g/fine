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
        <c:if test="${not (requestScope.pages == 0)}">
            <c:forEach begin="1" end="${requestScope.pages}" varStatus="loop">
                <c:choose>
                    <c:when test="${not(loop.current == requestScope.currentPage)}">
                        <a href="${requestScope.nextPageUrl}${loop.current}">${loop.current}</a>
                    </c:when>
                    <c:otherwise>
                         <c:out value="${loop.current}"/>
                    </c:otherwise>
                </c:choose>
               
            </c:forEach>    
        </c:if>
        <a href="/logout">
            logout
        </a>
    </body>
</html>
