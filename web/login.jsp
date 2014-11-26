<%-- 
    Document   : login
    Created on : Nov 18, 2014, 11:26:07 PM
    Author     : aalmoghalis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="j_security_check" method="post">
            username : <input type="text" name="j_username"/><br>
            password : <input type="password" name = "j_password"/><br>
            <input type ="submit" name = "submit" value = "submit">
        </form>

    </body>
</html>
