

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create new blog entry</title>
        <jsp:include page="../partial/resources.html" flush="true" />
    </head>
    <body id="content">
        <div class="row">
            <form action="/new_entry" method="post" id="new_entry">
                <div class="row">
                    <div class="col-lg-4">
                        <div id="title">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div id="body">
                        </div>
                    </div>
                </div>
                <input id="hTitle" type="hidden" name="title">
                <input id="hBody" type="hidden" name="body">
                <input type="submit">
                
            </form>


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
