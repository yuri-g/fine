<%-- 
    Document   : newMessage
    Created on : Aug 12, 2013, 6:39:52 PM
    Author     : yuri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>New message servlet at <%= request.getContextPath() %></h1>
        <form method="POST">
            <div>
                Title: 
                <input type="text" name="title">
            </div>
            <div>
                Message: <textarea name='body'></textarea>
            </div>
            <div>
                <input type="submit">
            </div>
        </form>
    </body>
</html>
