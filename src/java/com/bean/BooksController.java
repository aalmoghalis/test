package com.bean;

import com.entities.Books;
import com.bean.util.JsfUtil;
import com.bean.util.PaginationHelper;
import com.keypoint.PngEncoder;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

@ManagedBean(name = "booksController")
@SessionScoped
public class BooksController implements Serializable {

    private Books current;
    private DataModel items = null;
    @EJB
    private com.bean.BooksFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String showForm ="false";
    private String bkName="";
    private HtmlInputText input1;


      public void drawChart(OutputStream out, Object data) throws IOException {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Item1", 10);
    dataset.setValue("Item2", 15);
    dataset.setValue("Item3", 8);
    dataset.setValue("Item4", 12.5);
    dataset.setValue("Item5", 30);
    JFreeChart chart = ChartFactory.createPieChart("Sales", dataset, true, true, Locale.ENGLISH);
    BufferedImage bufferedImage = chart.createBufferedImage(300, 300);
    ImageIO.write(bufferedImage, "gif", out);
  }
    public HtmlInputText getInput1() {
        return input1;
    }

    public void setInput1(HtmlInputText input1) {
        this.input1 = input1;
    }

    public void setCurrent(Books current) {
        this.current = current;
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public String getBkName() {
        return bkName;
    }

    public void setShowForm(String show_form) {
        this.showForm = show_form;
    }

    public String getShowForm() {
        return showForm;
    }

    
    public BooksController() {
    }

    public Books getSelected() {
        if (current == null) {
            current = new Books();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BooksFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
         
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                   // return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                     return new ListDataModel(getFacade().getBooks());
                }
            };
        }
        return pagination;
    }
    
    public PaginationHelper getPagination2(   ) {
        
        System.out.println("iiiiiiinp:"+this.getBkName());
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
         
                @Override
                public int getItemsCount() {
                    return getFacade().getBooks2(getBkName()).size();
                }

                @Override
                public DataModel createPageDataModel( ) {
                   // return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                     return new ListDataModel(getFacade().getBooks2(getBkName()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List2";
    }

    public String prepareView() {
        current = (Books) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Books();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BooksCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Books) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        this.setShowForm("true");
        return "List2";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BooksUpdated"));
            this.setShowForm("false");
            return "List2";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Books) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List2";
    }

  public String input()
  {
    recreateModel();
    System.out.println("input:#####"+(String) input1.getValue());
    this.setBkName((String) input1.getValue());
    items = getPagination2().createPageDataModel();
    return "List2";
  }
   
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BooksDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {

        
        if (items == null) {
           //added by abdullah
            //HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            //String inp1 =request.getParameter("myform:input1");
           // FacesContext ctx = FacesContext.getCurrentInstance();  
           // Map<String,String> request = ctx.getExternalContext().getRequestParameterMap();  
           // String data = request.get("myform"+NamingContainer.SEPARATOR_CHAR+"input1");
           System.out.println("items:"+(String) input1.getValue());
       // items = getPagination2((String) input1.getValue()).createPageDataModel();
        
          //  items = getPagination().createPageDataModel();
           input();
        }
         // System.out.println("getitems:"+(String) input1.getValue());
        //items = getPagination2((String) input1.getValue()).createPageDataModel();
       
        return items;
    }

    
     
    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination2().nextPage();
        recreateModel();
        return "List2";
    }

    public String previous() {
        getPagination2().previousPage();
        recreateModel();
        return "List2";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public byte [] getChart(int x) {
        //String chart = request.getParameter("chart");
        // also you can process other parameters like width or height here
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("JAN", 10);
        dataset.setValue("FEB", 15);
        dataset.setValue("MAR", 8);
        dataset.setValue("APR", 12.5);
        dataset.setValue("MAY", 30);
      //  if (chart.equals("myDesiredChart1")) {
        ChartRenderingInfo info = new ChartRenderingInfo(); 
            JFreeChart chart = ChartFactory.createPieChart("Sales", dataset, true, true, Locale.ENGLISH);
        BufferedImage buf2 = chart.createBufferedImage(300, 300,info);
      PngEncoder encoder2 = new PngEncoder( buf2, false, 0, 9 );
       
            return encoder2.pngEncode();
        }

    @FacesConverter(forClass = Books.class)
    public static class BooksControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BooksController controller = (BooksController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "booksController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Books) {
                Books o = (Books) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Books.class.getName());
            }
        }

    }

}
