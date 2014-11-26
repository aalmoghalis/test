/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servlet;

import com.keypoint.PngEncoder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialPointer.Pin;
import org.jfree.chart.plot.dial.DialPointer.Pointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.date.SerialDate;

/**
 *
 * @author aalmoghalis
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        BufferedImage buf ;
        
      
      
      
    String type_=request.getParameter("type");  
      //os.write(encoder.pngEncode());
      //os.write(encoder2.pngEncode());
    if(type_.equals("bar"))
    {
    //bar chart
      buf = (BufferedImage)getBarChart(request);
      PngEncoder encoder_bar = new PngEncoder( buf, false, 0, 9 );
       
        os.write(encoder_bar.pngEncode());
    }
    else if(type_.equals("pie"))
    {
        //pie chart
         buf = (BufferedImage)getpieChart(request);
        PngEncoder encoder_pie = new PngEncoder( buf, false, 0, 9 );
        os.write(encoder_pie.pngEncode());
    }
    
    else if(type_.equals("3dbar"))
    {
        // 3D BAR CHART
       buf = (BufferedImage)getBarChart3D(request);
      PngEncoder encoder_3dbar = new PngEncoder( buf, false, 0, 9 );
        os.write(encoder_3dbar.pngEncode());
    }
    else if(type_.equals("line"))
    {
    // line chart
        buf = (BufferedImage)getLineChart(request);
        PngEncoder encoder_line = new PngEncoder( buf, false, 0, 9 );
    
        os.write(encoder_line.pngEncode());
    }
    else if(type_.equals("xychart"))
    {
        //xy chart
      buf = (BufferedImage)getXYChart();
      PngEncoder encoder_xychart = new PngEncoder( buf, false, 0, 9 );
        os.write(encoder_xychart.pngEncode());
    }
    else if(type_.equals("3dpie"))
    {
      // 3d pie 
      buf = (BufferedImage)getpie3dChart(request);
      PngEncoder encoder_3dpie = new PngEncoder( buf, false, 0, 9 );
      
        os.write(encoder_3dpie.pngEncode());
    }
    else if(type_.equals("bubble"))
    {
        //bubble chart
            buf = (BufferedImage) getbubbleChart();
      PngEncoder encoder_bubble = new PngEncoder( buf, false, 0, 9 );
        os.write(encoder_bubble.pngEncode());
    }
    else if(type_.equals("timeseries"))
    {
    //timeseries chart
      buf = (BufferedImage) getTimeSeriesChart();
      PngEncoder encoder_timeseries = new PngEncoder( buf, false, 0, 9 );
      os.write(encoder_timeseries.pngEncode());
    }
    else if(type_.equals("dialplot"))
    {
      // dialplot Chart
      buf = (BufferedImage) getDialPlotChart(0,100,10);
      PngEncoder encoder_dialplot = new PngEncoder( buf, false, 0, 9 );
      
        os.write(encoder_dialplot.pngEncode());
    }
    //os.write(img1);
    //os.write(img2);
    //os.write(combine);
    //ImageIO.write(buf2, "png", os);
    //os.flush();
    //ImageIO.write(buf, "png", os);
    
    os.close();
      
      
       
       
       // response.setContentType("text/html;charset=UTF-8");
       // PrintWriter out = response.getWriter();
        
    }
    
    private RenderedImage getpieChart(HttpServletRequest request) {
        //String chart = request.getParameter("chart");
        // also you can process other parameters like width or height here
      //  DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue("IPhone 5s", new Double( 20 ) );
      dataset.setValue("SamSung Grand", new Double( 20 ) );
      dataset.setValue("MotoG", new Double( 40 ) );
      dataset.setValue("Nokia Lumia", new Double( 10 ) );
      //  if (chart.equals("myDesiredChart1")) {
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = 
                    ChartFactory.createPieChart("Mobile Sales(Pie)", dataset,
                            true, true, Locale.ENGLISH);
        
        
            return chart.createBufferedImage(400, 400,info);
        }
    
    
    private RenderedImage getpie3dChart(HttpServletRequest request) {
        //String chart = request.getParameter("chart");
        // also you can process other parameters like width or height here
      //  DefaultPieDataset dataset = new DefaultPieDataset();
        DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue("IPhone 5s", new Double( 20 ) );
      dataset.setValue("SamSung Grand", new Double( 20 ) );
      dataset.setValue("MotoG", new Double( 40 ) );
      dataset.setValue("Nokia Lumia", new Double( 10 ) );
      //  if (chart.equals("myDesiredChart1")) {
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart =  ChartFactory.createPieChart3D( 
         "Mobile Sales(3D Pie)" ,  // chart title                   
         dataset ,         // data 
         true ,            // include legend                   
         true, 
         false);
          final PiePlot3D plot = ( PiePlot3D ) chart.getPlot( );             
          plot.setStartAngle( 270 );             
          plot.setForegroundAlpha( 0.60f );             
          plot.setInteriorGap( 0.02 );           
        
        
            return chart.createBufferedImage(534, 400,info);
        }
    
    
    
     private RenderedImage getBarChart(HttpServletRequest request) {
        //String chart = request.getParameter("chart");
        // also you can process other parameters like width or height here
//        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
//        dataset.setValue(6, "Profit", "Jane");
//        dataset.setValue(7, "Profit", "Tom");
//        dataset.setValue(8, "Profit", "Jill");
//        dataset.setValue(5, "Profit", "John");
//        dataset.setValue(12, "Profit", "Fred");
         
       final String fiat = "FIAT";
      final String audi = "AUDI";
      final String ford = "FORD";
      final String speed = "Speed";
      final String millage = "Millage";
      final String userrating = "User Rating";
      final String safety = "safety";

      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

      dataset.addValue( 1.0 , fiat , speed );
      dataset.addValue( 3.0 , fiat , userrating );
      dataset.addValue( 5.0 , fiat , millage );
      dataset.addValue( 5.0 , fiat , safety );

      dataset.addValue( 5.0 , audi , speed );
      dataset.addValue( 6.0 , audi , userrating );
      dataset.addValue( 10.0 , audi , millage );
      dataset.addValue( 4.0 , audi , safety );

      dataset.addValue( 4.0 , ford , speed );
      dataset.addValue( 2.0 , ford , userrating );
      dataset.addValue( 3.0 , ford , millage );
      dataset.addValue( 6.0 , ford , safety );
      
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = ChartFactory.createBarChart(
         "CAR USAGE STATIStICS(Bar)", 
         "Category", "Score", 
         dataset,PlotOrientation.VERTICAL, 
         true, true, false);
        
    
            return chart.createBufferedImage(400, 400,info);
        }
     
     
     private RenderedImage getBarChart3D(HttpServletRequest request) throws IOException {
        //String chart = request.getParameter("chart");
        // also you can process other parameters like width or height here
        final String fiat = "FIAT";
      final String audi = "AUDI";
      final String ford = "FORD";
      final String speed = "Speed";
      final String millage = "Millage";
      final String userrating = "User Rating";
      final String safety = "safety";

      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

      dataset.addValue( 1.0 , fiat , speed );
      dataset.addValue( 3.0 , fiat , userrating );
      dataset.addValue( 5.0 , fiat , millage );
      dataset.addValue( 5.0 , fiat , safety );

      dataset.addValue( 5.0 , audi , speed );
      dataset.addValue( 6.0 , audi , userrating );
      dataset.addValue( 10.0 , audi , millage );
      dataset.addValue( 4.0 , audi , safety );

      dataset.addValue( 4.0 , ford , speed );
      dataset.addValue( 2.0 , ford , userrating );
      dataset.addValue( 3.0 , ford , millage );
      dataset.addValue( 6.0 , ford , safety );
      
      //  if (chart.equals("myDesiredChart1")) {
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = ChartFactory.createBarChart3D(
         "Car Usage Statistics(3DBar)",             
         "Category",             
         "Score",             
         dataset,            
         PlotOrientation.VERTICAL,             
         true, true, false);  
            //save in file
             ChartRenderingInfo info2 = new ChartRenderingInfo(new StandardEntityCollection());
  //           File file1 = new File(getServletContext().getRealPath(".") + "/lineChart.png");
//System.out.println("PATH:"+getServletContext().getRealPath(".") );
              //  ChartUtilities.saveChartAsPNG(file1, chart, 300, 300, info2);
        
            return chart.createBufferedImage(400, 400,info);
        }
     
     private RenderedImage getLineChart(HttpServletRequest request) {
         
       DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 15 , "schools" , "1970" );
      dataset.addValue( 30 , "schools" , "1980" );
      dataset.addValue( 60 , "schools" ,  "1990" );
      dataset.addValue( 120 , "schools" , "2000" );
      dataset.addValue( 240 , "schools" , "2010" );
      dataset.addValue( 300 , "schools" , "2014" );
      
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = ChartFactory.createLineChart(
         "Schools Vs Years(Line)","Year",
         "Schools Count",
         dataset,PlotOrientation.VERTICAL,
         true,true,false);
        
        
            return chart.createBufferedImage(400, 400,info);
        }
     
     private RenderedImage getXYChart( ) {
         
        XYSeries firefox = new XYSeries( "Firefox" );          
      firefox.add( 1.0 , 1.0 );          
      firefox.add( 2.0 , 4.0 );          
      firefox.add( 3.0 , 3.0 );          
       XYSeries chrome = new XYSeries( "Chrome" );          
      chrome.add( 1.0 , 4.0 );          
      chrome.add( 2.0 , 5.0 );          
      chrome.add( 3.0 , 6.0 );          
       XYSeries iexplorer = new XYSeries( "InternetExplorer" );          
      iexplorer.add( 3.0 , 4.0 );          
      iexplorer.add( 4.0 , 5.0 );          
      iexplorer.add( 5.0 , 4.0 );          
       XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( firefox );          
      dataset.addSeries( chrome );          
      dataset.addSeries( iexplorer );
      
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = ChartFactory.createXYLineChart(
         "Browser usage statastics(XY)", 
         "Category",
         "Score", 
         dataset,
         PlotOrientation.VERTICAL, 
         true, true, false);
        
        
            return chart.createBufferedImage(400, 400,info);
        }
     

     private RenderedImage getbubbleChart() {
        
         DefaultXYZDataset xyzdataset = new DefaultXYZDataset(); 
                     
      double ad[ ] = { 30 , 40 , 50 , 60 , 70 , 80 };                 
      double ad1[ ] = { 10 , 20 , 30 , 40 , 50 , 60 };                 
      double ad2[ ] = { 4 , 5 , 10 , 8 , 9 , 6 };                 
      double ad3[][] = { ad , ad1 , ad2 };                 
      xyzdataset.addSeries( "Series 1" , ad3 );
      
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart =   ChartFactory.createBubbleChart(
         "AGE vs WEIGHT vs WORK(Bubble#XYZ)",                    
         "Weight",                    
         "AGE",                    
         xyzdataset,                    
         PlotOrientation.HORIZONTAL,                    
         true, true, false);
         
      XYPlot xyplot = ( XYPlot )chart.getPlot( );                 
      xyplot.setForegroundAlpha( 0.65F );                 
      XYItemRenderer xyitemrenderer = xyplot.getRenderer( );
      xyitemrenderer.setSeriesPaint( 0 , Color.blue );                 
      NumberAxis numberaxis = ( NumberAxis )xyplot.getDomainAxis( );                 
      numberaxis.setLowerMargin( 0.2 );                 
      numberaxis.setUpperMargin( 0.5 );                 
      NumberAxis numberaxis1 = ( NumberAxis )xyplot.getRangeAxis( );                 
      numberaxis1.setLowerMargin( 0.8 );                 
      numberaxis1.setUpperMargin( 0.9 ) ;  
      return chart.createBufferedImage(400, 400,info);
        }

          private RenderedImage getTimeSeriesChart() {
        
          TimeSeries series = new TimeSeries( "Random Data" );
        Second current = new Second();
      double value = 100.0;
      for ( int i = 0 ; i < 4000 ; i++ )
      {
         try
         {
            value = value + Math.random( ) - 0.5;
            series.add( current , new Double( value ) );
            current = ( Second ) current.next( );
         }
         catch ( SeriesException e ) 
         {
            System.err.println( "Error adding to series" );
         }
      }
       XYDataset dataset=( XYDataset )new TimeSeriesCollection(series);
      JFreeChart chart = ChartFactory.createTimeSeriesChart(
         "Computing Test(TimeSeries)", 
         "Seconds", 
         "Value", 
         dataset,
         false, 
         false, 
         false);
        ChartRenderingInfo info = new ChartRenderingInfo(); 
        
      return chart.createBufferedImage(400, 400,info);
        }

     private RenderedImage getDialPlotChart( int min,int max,int gap ) {
         
      Date d = new Date();
      
         
         DefaultValueDataset dataset = new DefaultValueDataset(d.getMinutes());
         DialPlot plot = new DialPlot(dataset);
    plot.setDialFrame(new StandardDialFrame());
    plot.addLayer(new DialValueIndicator(0));
    //second layer
    
    int redLine = 3 * min / 5;
    plot.addLayer(new StandardDialRange(max, redLine, Color.blue));
    plot.addLayer(new StandardDialRange(redLine, 0, Color.red));
    Pointer p1 = new Pointer();
    Pin p2 = new Pin();
    p2.setRadius(0.55000000000000004D);
    plot.addPointer(p1);
    plot.addPointer(p2);
    //plot.addLayer(new DialPointer.Pointer());
//min=0,max=100,tickgap=10
    //StandardDialScale scale = new StandardDialScale(0, 100,
        //-120, -300, 10, 10 - 1);
    StandardDialScale scale = new StandardDialScale(min, max,
        -120, -300, gap, gap - 1);
    scale.setTickRadius(0.88);
    scale.setTickLabelOffset(0.20);
    plot.addScale(0, scale);
    
    // add the second scale
    
//    StandardDialScale scale2 = new StandardDialScale(0, 24,
//        -120, -300, gap, gap - 1);
//    scale.setTickRadius(0.88);
//    scale.setTickLabelOffset(0.20);
//    plot.addScale(1, scale2);
//    
    
         ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = new JFreeChart(plot);
           chart.setTitle("Activation Status(Dailplot)");
        
            return chart.createBufferedImage(300, 300,info);
        }
     

          
         

     
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
             {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
