package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rentbook database table.
 * 
 */
@Entity
@NamedQuery(name="Rentbook.findAll", query="SELECT r FROM Rentbook r")
public class Rentbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idRentBook;

	@Lob
	private String endDateLoan;

	@Lob
	private String title;

	public Rentbook() {
	}

//	public Rentbook(Object[] t) { 
//		// TODO Auto-generated constructor stub
//	}
	public Rentbook(Object[] result){
		endDateLoan = (String) result[0];
		title = (String) result[1];
	}

	public int getIdRentBook() {
		return this.idRentBook;
	}

	public void setIdRentBook(int idRentBook) {
		this.idRentBook = idRentBook;
	}

	public String getEndDateLoan() {
		return this.endDateLoan;
	}

	public void setEndDateLoan(String endDateLoan) {
		this.endDateLoan = endDateLoan;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Rentbook [idRentBook=" + idRentBook + ", endDateLoan=" + endDateLoan + ", title=" + title + "]";
	}

}