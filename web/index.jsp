<%@page import="javax.imageio.ImageIO" %>
<%@ page  import="java.awt.*" %>
<%@ page  import="java.io.*" %>
<%@ page  import="org.jfree.chart.*" %>
<%@ page  import="org.jfree.chart.entity.*" %>
<%@ page  import ="org.jfree.data.general.*"%>
<%@ page session="true" %>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
  /*DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Item1", 10);
    dataset.setValue("Item2", 15);
    dataset.setValue("Item3", 8);
    dataset.setValue("Item4", 12.5);
    dataset.setValue("Item5", 30);
    JFreeChart chart = ChartFactory.createPieChart("Sales", dataset, true, true, Locale.ENGLISH);
    BufferedImage bufferedImage = chart.createBufferedImage(300, 300);
 //   ImageIO.write(bufferedImage, "gif", out);
          */
  
session.setAttribute( "chart", chart ); 

// get ImageMap
ChartRenderingInfo info = new ChartRenderingInfo(); 
// populate the info
chart.createBufferedImage(640, 400, info); 
String imageMap = ChartUtilities.getImageMap( "map", info ); 


%>

<%= imageMap%>


<IMG src="chartviewer" usemap="#map">

<html>
  <head>
  <meta http-equiv="Content-Type" 
  content="text/html; charset=UTF-8">
  <title>JSP Page</title>
  </head>
  <body>
  <IMG SRC="piechart.png" WIDTH="600" HEIGHT="400" 
   BORDER="0" USEMAP="#chart">
  </body>
</html> 