<%-- 
    Document   : signin
    Created on : Aug 13, 2013, 5:55:27 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <body id="content">
        <c:if test="${!empty requestScope.errors}">
            <div class='row'><div class='col-lg-2 col-lg-offset-5 alert alert-danger'>
                    ${requestScope.errors}    
                </div>
            </div>
            
        </c:if>

        <div class="row">
            <div class="col-lg-2 col-lg-offset-5 box">
                <form action="/registration" method="post">
                    <fieldset> 
                        <div class="form-group">
                            <label for="username">Name:</label>
                            <input type="text" class="form-control" id="username" name="username">
                        </div>
                        <div class="form-group">
                            <label for="email">Mail:</label>
                            <input type="text" class="form-control" id="email" name="email">
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="form-group">
                            <label for="passwordC">Password confirmation:</label>
                            <input type="password" class="form-control" id="passwordC" name="passwordC">
                        </div>
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>
