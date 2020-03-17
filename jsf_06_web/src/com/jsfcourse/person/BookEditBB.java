package com.jsfcourse.person;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import javax.enterprise.context.SessionScoped;
//import com.jsfcourse.login.ClientData;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.jsf.dao.BookDAO;
import com.jsf.entities.Book;

@Named
@ViewScoped
public class BookEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_BOOK_LIST = "/pages/bookList.xhtml";//?faces-redirect=true
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_BOOK_EDIT = "/pages/bookEdit.xhtml";

	//private Book book = new Book();
	private Book selectedBook;
	private Book book;
	//private Book loaded = null;

	//@EJB
	@Inject
	BookDAO bookDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Book getSelectedBook() {
		return selectedBook;
	}

	public void setSelectedBook(Book selectedBook) {
		this.selectedBook = selectedBook;
	}
	
	public String goToEditPage(Book m) {
		this.selectedBook = m;
		return PAGE_BOOK_EDIT;
	}
	
	public boolean validateBook(Book book) {
		List<Book> duplicates = bookDAO.searchForDuplicate(book.getTitle(), book.getAuthor(), book.getGenre());
		if(duplicates.isEmpty() || duplicates == null) return true;
		else return false;
	}
	
	public String editBook() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if(selectedBook == null) {
			return null;
		}
		
		
		Book oldBook = bookDAO.find(selectedBook.getIdBook());
		
		if(selectedBook.getTitle() != oldBook.getTitle()) {
			List<Book> duplicates = bookDAO.searchForDuplicateByTitle(selectedBook.getTitle());
			if(duplicates.size() > 1) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Podany tytu³ wystêpuje ju¿ w bazie danych", null));
				return null;
			}
			else {
				oldBook.setTitle(selectedBook.getTitle());
			}
		}
		
		if(selectedBook.getAuthor() != oldBook.getAuthor()) {
			List<Book> duplicates = bookDAO.searchForDuplicateByAuthor(selectedBook.getAuthor());
			if(duplicates.size() > 1) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Podany autor wystêpuje ju¿ w bazie danych", null));
				return null;
			}
			else {
				oldBook.setAuthor(selectedBook.getAuthor());
			}
		}
		
		if(selectedBook.getGenre() != oldBook.getGenre()) {
			List<Book> duplicates = bookDAO.searchForDuplicateByGenre(selectedBook.getGenre());
			if(duplicates.size() > 1) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Podany gatunek wystêpuje ju¿ w bazie danych", null));
				return null;
			}
			else {
				oldBook.setGenre(selectedBook.getGenre());
			}
		}
		
		if(selectedBook.getTitle() != oldBook.getTitle()) {
			oldBook.setTitle(selectedBook.getTitle());
		}
		
		if(selectedBook.getAuthor() != oldBook.getAuthor()) {
			oldBook.setAuthor(selectedBook.getAuthor());
		}
		
		if(selectedBook.getGenre() != oldBook.getGenre()) {
			oldBook.setGenre(selectedBook.getGenre());
		}
		
		oldBook.setSummary(selectedBook.getSummary());
		
		bookDAO.merge(oldBook);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Ksi¹¿ka zosta³a edytowana", null));

		return PAGE_BOOK_LIST;
	}
}
