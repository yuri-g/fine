

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Fine - new post</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <body id="content">
        <jsp:include page="../partial/logo.html" flush="true" />
        <div class="row">
             <div class="col-lg-6 col-lg-offset-2">
            <form action="/new_entry" method="post" id="new_entry">
                <div class="row">
                    <div id="title">
                    </div>
                </div>
                <div class="row">
                    <div id="body">
                    </div>
                </div>
                <input id="hTitle" type="hidden" name="title">
                <input id="hBody" type="hidden" name="body">
                <input type="submit">
                
            </form>
             </div>
                  <div class="col-lg-4 user-panel">
                <c:choose>
                    <c:when test="${not empty sessionScope.uSessionBean}">
                        ${sessionScope.uSessionBean.getUser().getName()}
                        <div>
                            <a href="/blog">
                                Blog
                            </a>
                        </div>
                        <div>
                            <a href="/settings">
                                Settings
                            </a>
                        </div>
                        <div>
                            <a href="/logout">
                                Logout
                            </a>
                        </div>
                    </c:when> 
                </c:choose>
            </div>    
        </div>
        
    </body>
    
    <script>
         new Medium({
                element: document.getElementById('body'),
                placeholder: "Type your text here"
            });
        new Medium({ 
                element: document.getElementById('title'),
                mode: 'inline',
                placeholder: "YOUR TITLE"
            });
    </script>
</html>
