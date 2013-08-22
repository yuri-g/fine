<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login Page</title>
        <link href="../style.css" rel="stylesheet"  />
        <link href="../bootstrap.css" rel="stylesheet"  />
    </head>


    <body id="content">
        <c:if test="${not empty requestScope.errors}">
            <div class="row">
                <div class="col-lg-2">
                    ${requestScope.errors}    
                </div>
            </div>
        </c:if>
         <div class="row">
            <div class="col-lg-2">
                <form action="/log_in" method="post">
                    <fieldset> 
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="text" class="form-control" id="email" name="email">
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" class="form-control" id="password" name="password">
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="remember" name="remember"> Remember me
                                </label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-default">Log in</button>
                    </fieldset>
                </form>
            </div>
    </body>
</html>