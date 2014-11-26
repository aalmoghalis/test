/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean;

import com.entities.Books;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aalmoghalis
 */
@Stateless
public class BooksFacade extends AbstractFacade<Books> {
    @PersistenceContext(unitName = "TestJSP_SecurityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BooksFacade() {
        super(Books.class);
    }
    //addeb by abdullah
    public List<Books> getBooks() {
    return em.createQuery("From Books where id=3").getResultList();
    }
    public List<Books> getBooks2(String name) {
    System.out.println("From Books where name like '%"+name+"%'");
        return em.createQuery("From Books where name like '%"+name+"%'").getResultList();
}
}
