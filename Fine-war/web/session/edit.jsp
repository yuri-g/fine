<%-- 
    Document   : edit
    Created on : Sep 9, 2013, 2:29:08 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fine - Settings</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <body id="content">
        <jsp:include page="../partial/logo.html" flush="true" /> 
        <div class="row">
            <c:if test="${not empty requestScope.errors}">
                <div class="row">
                    <div class="col-lg-4 col-lg-2 col-lg-offset-5 alert alert-danger">
                    ${requestScope.errors}    
                </div>
                </div>
            </c:if>
            <div class="col-lg-4 col-lg-offset-2">
                <form action="/settings" method="post" id="edit_users">
                    <fieldset> 
                         <div class="form-group">
                            <label for="name">Change name:</label>
                            <input type="text" class="form-control" id="name" name="name">
                        </div>
                        <div class="form-group">
                            <label for="nPassword">Change password:</label>
                            <input type="password" class="form-control" id="nPassword" name="nPassword">
                        </div>
                        <div class="form-group">
                            <label for="cPassword">Confirm new password:</label>
                            <input type="password" class="form-control" id="cPassword" name="cPassword">
                        </div>
                        <div class="form-group">
                            <label for="password">Current password:</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                    </fieldset>
                    <input type="submit">

                </form>
            </div>
            <div class="col-lg-4 user-panel col-lg-offset-2">
                <c:choose>
                    <c:when test="${not empty sessionScope.uSessionBean}">
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
                    </c:when> 
                </c:choose>
            </div> 
        </div>
    </body>
</html>
