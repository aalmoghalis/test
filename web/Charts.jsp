<%-- 
    Document   : Charts
    Created on : Nov 24, 2014, 8:35:27 PM
    Author     : aalmoghalis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CHARTS</title>
        
    </head>
    <body>
         <h3>Create Charts Dynamically using JFreechart</h3>
     
         <%
                response.setIntHeader("Refresh", 10);
                
        %>
        
        <hr>
        <hr>
        <img src="/TestJSP_Security/TestServlet?type=dialplot" />
        <hr>
        <img src="/TestJSP_Security/TestServlet?type=timeseries" />
        <img src="/TestJSP_Security/TestServlet?type=bubble" />
        
        <hr>
        <img src="/TestJSP_Security/TestServlet?type=bar" />
        <img src="/TestJSP_Security/TestServlet?type=3dbar" />
        
        <hr>
        
        <img src="/TestJSP_Security/TestServlet?type=line" />
        <img src="/TestJSP_Security/TestServlet?type=xychart" />
        <hr>
        
        <img src="/TestJSP_Security/TestServlet?type=pie" />
        <img src="/TestJSP_Security/TestServlet?type=3dpie" />
        
        
    </body>
</html>
