package com.jsfcourse.person;

import java.time.LocalDate;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.jsf.dao.LoanDAO;
import com.jsf.entities.Loan;
import com.jsf.entities.Book;
import com.jsf.entities.Rentbook;

@Named
@RequestScoped
public class LoanListBB {
	private static final String PAGE_PERSON_EDIT = "loanEdit?faces-redirect=true";
	private static final String PAGE_BASKET = "/pages/basket.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String dateRental;
	private String endDateLoan;
		
	public String getDataRental() {
		return dateRental;
	}

	public void setDataRental(String dateRental) {
		this.dateRental = dateRental;
	}
	
	public String getEndDateLoan() {
		return endDateLoan;
	}

	public void setEndDateLoan(String endDateLoan) {
		this.endDateLoan = endDateLoan;
	}

	@EJB
	LoanDAO loanDAO;
	
	public List<Rentbook> getFullList(){
		return loanDAO.getFullList();
	}

	public List<Rentbook> getList(){
		return loanDAO.getFullList();
	}

	public String newBook(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Loan loan = new Loan();
		session.setAttribute("loan", loan);
		return PAGE_PERSON_EDIT;
	}
	
	public String rentBook(Book book) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		Loan loan = new Loan();
		
		//loan.setIdLoan(0); 
		loan.setIdBook(Integer.toString(book.getIdBook()));
		loan.setIdUser((String) context.getExternalContext().getSessionMap().get("idPerson"));
		loan.setDateRental(LocalDate.now().toString());
		loan.setEndDateLoan(LocalDate.now().plusMonths(1).toString());
		
		loanDAO.create(loan);
		
		//return PAGE_STAY_AT_THE_SAME;
		return PAGE_BASKET;
		
	}

}
