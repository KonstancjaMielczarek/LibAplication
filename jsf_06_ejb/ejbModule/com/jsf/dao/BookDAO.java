package com.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Book;

//DAO - Data Access Object for Book entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class BookDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Book book) {
		em.persist(book);
		//em.flush();
	}

	public Book merge(Book book) {
		return em.merge(book);
	}

	public void remove(Book book) {
		em.remove(em.merge(book));
	}

	public Book find(Object id) {
		return em.find(Book.class, id);
	}

	public List<Book> getFullList() {
		List<Book> list = null;

		Query query = em.createQuery("select m from Book m");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Book> getList(Map<String, Object> searchParams) {
		List<Book> list = null;

		// 1. Build query string with parameters
		String select = "select m ";
		String from = "from Book m ";
		String where = "";
		String orderby = "order by m.title asc";

		// search for surname
		String title = (String) searchParams.get("title");
		if (title != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "m.title like :title ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (title != null) {
			query.setParameter("title", title+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Book> searchForDuplicate(String title, String author, String genre) {
		Query query =  em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title OR b.author LIKE :author OR b.genre LIKE :genre");
		query.setParameter("title", title);
		query.setParameter("author", author);
		query.setParameter("genre", genre);
		return query.getResultList();
	}
	public List<Book> searchForDuplicateByTitle(String title) {
		Query query =  em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title");
		query.setParameter("title", title);
		return query.getResultList();
	}
	
	public List<Book> searchForDuplicateByAuthor(String author) {
		Query query =  em.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author");
		query.setParameter("author", author);
		return query.getResultList();
	}

	public List<Book> searchForDuplicateByGenre(String genre) {
		Query query =  em.createQuery("SELECT b FROM Book b WHERE b.genre LIKE :genre");
		query.setParameter("genre", genre);
		return query.getResultList();
	}

}
