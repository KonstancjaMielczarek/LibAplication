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

import com.jsf.dao.PersonDAO2;
import com.jsf.entities.Person;

@Named
@RequestScoped
public class SignBB{
	//private static final long serialVersionUID = 1L;
	
	private static final String PAGE_LOG = "/pages/log.xhtml";//?faces-redirect=true
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private Person person = new Person();
	
	public Person getPerson() {
		return person;
	}
	
	@EJB
	PersonDAO2 personDAO;
	
	public String saveData() {

		try {
			if (person.getIdperson() == 0) {
				person.setIdRole("1"); //
				personDAO.create(person);
			} else {

				personDAO.merge(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wyst¹pi³ b³¹d podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_LOG;
	}
	
}
