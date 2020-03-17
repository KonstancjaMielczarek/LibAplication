package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the loan database table.
 * 
 */
@Entity
@Table(name="loan")
@NamedQuery(name="Loan.findAll", query="SELECT l FROM Loan l")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idLoan; 

	@Lob
	private String dateRental;

	@Lob
	private String endDateLoan;

	@Lob
	private String idBook;

	@Lob
	private String idUser;

	public Loan() {
	}

	public int getIdLoan() {
		return this.idLoan;
	}

	public void setIdLoan(int idLoan) {
		this.idLoan = idLoan;
	}

	public String getDateRental() {
		return this.dateRental;
	}

	public void setDateRental(String dateRental) {
		this.dateRental = dateRental;
	}

	public String getEndDateLoan() {
		return this.endDateLoan;
	}

	public void setEndDateLoan(String endDateLoan) {
		this.endDateLoan = endDateLoan;
	}

	public String getIdBook() {
		return this.idBook;
	}

	public void setIdBook(String idBook) {
		this.idBook = idBook;
	}

	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	@Override
	public String toString() {
		return "Loan [idLoan=" + idLoan + ", idUser=" + idUser + ", idBook=" + idBook + ", dateRental=" + dateRental
				+ ", endDateLoan=" + endDateLoan + "]";
	}

}