package com.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Person;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class PersonDAO2 {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Person person) {
		em.persist(person);
		//em.flush();
	}

	public Person merge(Person person) {
		return em.merge(person);
	}

	public void remove(Person person) {
		em.remove(em.merge(person));
	}

	public Person find(Object id) {
		return em.find(Person.class, id);
	}
	
	public List<Person> getFullList() {
		List<Person> list = null;

		Query query = em.createQuery("select p from Person p");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Person> getList(Map<String, Object> searchParams) {
		List<Person> list = null;

		String select = "select p ";
		String from = "from Person p ";
		String where = "";
		String orderby = "order by p.surname asc, p.name";

		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.surname like :surname ";
		}
		
		Query query = em.createQuery(select + from + where + orderby);

		if (surname != null) {
			query.setParameter("surname", surname+"%");
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Object> checkPass(String login, String password){
		List<Object> list = null;
		
		Query query = em.createQuery("SELECT  p.login, p.idRole, p.password, p.idperson FROM Person p WHERE p.login = :name and p.password = :password");
		
		if (login != null) {
			query.setParameter("name", login);
		}
		
	    if (password != null) {
	        query.setParameter("password", password);
	    }
		
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
		
	public List<Person> checkPass(String nick) {
			
			List<Person> list = null;

			String name = nick;
			
			Query query = em.createQuery("SELECT p.surname FROM Person p WHERE p.name = :name");


			if (nick != null) {
				query.setParameter("name", nick);
			}


			try {
				list = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}		

			return list;
		}

}
