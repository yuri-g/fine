<%-- 
    Document   : edit
    Created on : Sep 9, 2013, 1:11:45 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fine - Edit ${requestScope.entry.getTitle()}</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <body id="content">
        <div class="row">
            <div class="col-lg-offset-2">
                <form action="/edit" method="post" id="edit_entry">
                    <div class="row">
                        <div class="col-lg-4">
                            <div id="title">
                                ${requestScope.title}
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-4">
                            <div id="body">
                                ${requestScope.body}
                            </div>
                        </div>
                    </div>
                    <input id="hTitle" type="hidden" name="title">
                    <input id="hBody" type="hidden" name="body">
                    <input id="hId" type ="hidden" name="entryId" value="${requestScope.id}">
                    <input type="submit">

                </form>
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
