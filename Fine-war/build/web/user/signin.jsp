<%-- 
    Document   : signin
    Created on : Aug 13, 2013, 5:55:27 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In</title>
        <link href="../bootstrap.css" rel="stylesheet"  />
    </head>
    <body>
            11
         ${requestScope.password}
        <div class="row">
            <div class="col-lg-2">
                <form action="/sign_in" method="post">
                    <fieldset>
                       
                        <div class="form-group">
                            <label for="username">Name:</label>
                            <input type="text" class="form-control" id="username" name="username">
                        </div>
                        <div class="form-group">
                            <label for="mail">Mail:</label>
                            <input type="text" class="form-control" id="mail" name="mail">
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>
