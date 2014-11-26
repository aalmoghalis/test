/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aalmoghalis
 */
@ManagedBean
@RequestScoped
public class NavigationController {

    /**
     * Creates a new instance of NavigationController
     */
    public NavigationController() {
        
        
    }
    @ManagedProperty(value="#{param.pageId}")
   private String pageId;
     public String moveToPage2(){
        
         
         return "page2";
         /*
         if(pageId.equals("1"))
         return "page2";
         else
             return "home";
           */  
   }
    
}
