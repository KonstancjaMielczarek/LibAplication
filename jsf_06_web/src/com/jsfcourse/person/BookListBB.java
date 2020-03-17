package com.jsfcourse.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jsf.dao.BookDAO;
import com.jsf.entities.Book;
import com.jsf.entities.Person;

@Named
@RequestScoped
public class BookListBB {
	private static final String PAGE_NEW_BOOK = "/pages/addBook";//?faces-redirect=true
	private static final String PAGE_BOOK_EDIT = "/pages/bookEdit.xhtml";//?faces-redirect=true
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String title;
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@EJB
	BookDAO bookDAO;
		
	public List<Book> getFullList(){
		return bookDAO.getFullList();
	}

	public List<Book> getList(){
		List<Book> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (title != null && title.length() > 0){
			searchParams.put("title", title);
		}
		
		list = bookDAO.getList(searchParams);
		
		return list;
	}

	public String newBook(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Book book = new Book();
		session.setAttribute("book", book);
		return PAGE_NEW_BOOK;
	}
	
	public String editBook(Book book){
		//System.out.println("test" + book.getIdBook());
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("book", book);
		return PAGE_BOOK_EDIT;
	}

	public String deleteBook(Book book){
		bookDAO.remove(book);
		return PAGE_STAY_AT_THE_SAME;
	}
	
}
