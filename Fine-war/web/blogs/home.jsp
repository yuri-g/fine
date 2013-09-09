<%-- 
    Document   : home
    Created on : Sep 8, 2013, 3:59:40 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../partial/resources.html" flush="true" />
        <title>Fine</title>
    </head>
    <body id="content">
        <div id="logo">
            <span id="fine-part">
                Fine
            </span>
            <span id="blogs-part">
                blogs
            </span>
        </div>
        <div class="row">
            <div class="blog-entry col-lg-6 col-lg-offset-2">
                <c:forEach items="${requestScope.popular}" var="item" varStatus = "status">
                    <div class="row">

                        <div class="title">
                             ${item.getTitle()}
                        </div>
                        <div class="author">
                            <a href="/users?id=${item.getUser().getId()}">
                                ${item.getUser().getName()}
                            </a>
                        </div>
                            <div class="blog-body">
                                ${item.getBody()}
                            </div>
                            <div class="votes">
                                <c:choose>
                                     <c:when test="${requestScope.voted[status.index] == false}">
                                        <a href="#" id="${item.getId()}" class="upvote active-link">
                                            <span class="glyphicon glyphicon-heart-empty"></span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="glyphicon glyphicon-heart"></span>
                                    </c:otherwise>
                                </c:choose> 
                                <span class="number">
                                    ${item.getRating()}
                                </span>

                            </div>




                    </div>

                </c:forEach>
            </div>
            <div class="col-lg-4 user-panel">
                <c:if test="${not empty sessionScope.uSessionBean}">
                    ${sessionScope.uSessionBean.getUser().getName()}
                    <div>
                        <a href="/blog">
                            Blog
                        </a>
                    </div>
                    <div>
                        <a href="/new_entry">
                            New post
                        </a>
                        
                    </div>
                    <div>
                        <a href="/settings">
                            Settings
                        </a>
                    </div>
                        

                </c:if>
            </div>
        </div>
        
    </body>
</html>
