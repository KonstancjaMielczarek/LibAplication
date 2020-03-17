package com.jsfcourse.person;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext; 
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jsf.dao.BookDAO;
import com.jsf.entities.Book;

@Named
@RequestScoped
public class BookBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_PERSON_LIST = "bookList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private Book book = new Book();
	
	public Book getBook() {
		return  book;
	}
	
	@EJB
	BookDAO bookDAO;
	
	public String saveData() {

		try {
			if (book.getIdBook() == 0) {
				
				bookDAO.create(book);
			} else {

				bookDAO.merge(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
	
}
