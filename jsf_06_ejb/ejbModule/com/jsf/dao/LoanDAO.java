package com.jsf.dao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Loan;
import com.jsf.entities.Rentbook;


@Stateless
public class LoanDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	private Boolean flag = false;

	public void create(Loan loan) {
		em.persist(loan);
	}

	public Loan merge(Loan loan) {
		return em.merge(loan);
	}

	public void remove(Loan loan) {
		em.remove(em.merge(loan));
	}

	public Loan find(Object id) {
		return em.find(Loan.class, id);
	}

	public List<Rentbook> getFullList() {
		List<Object> list = null;
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String)(context.getExternalContext().getSessionMap().get("login"));
		
		if(flag == false) {
			Query query = em.createQuery("select l.endDateLoan, b.title from Loan l, Book b, Person p where l.idBook = b.idBook AND l.idUser = p.idperson AND l.endDateLoan >= :date AND p.login = :user" );
			query.setParameter("date", date);
			query.setParameter("user", login);

			try {
				list = query.getResultList();
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			flag = true;

			return list.stream().map(m -> (Object[]) m).map(t -> new Rentbook(t)).collect(Collectors.toList());
		}else {

			Query query = em.createQuery("select l.endDateLoan, b.title from Loan l, Book b, Person p where l.idBook = b.idBook AND l.idUser = p.idperson AND l.endDateLoan >= :date AND p.login = :user" );
			query.setParameter("date", date);
			query.setParameter("user", login);

			try {
				list = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			flag = false;

			return list.stream().map(m -> (Object[]) m).map(t -> new Rentbook(t)).collect(Collectors.toList());
		}


	}
}
