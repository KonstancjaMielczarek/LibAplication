package com.jsfcourse.person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.ejb.EJB;
import com.jsf.dao.PersonDAO2;
import com.jsf.entities.Person;

import utils.SessionUtils;


@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_MAIN = "/index.xhtml";
	private static final String PAGE_LOGIN = "/pages/log.xhtml";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String label = "ZALOGUJ";
	
	private String login;
	private String pass;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	@Inject
	PersonDAO2 personDAO;
	
	public String doLogin() {
		String login = this.login;
		String pass = this.pass;
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		List<Object> db = personDAO.checkPass(login, pass);
		
		if(db.size() < 1) {
			return PAGE_LOGIN;
		}
		
		Object table[] = (Object[]) db.get(0);
		
		if(table.length!=0) {
			setLabel("WYLOGUJ");
		}
		
		SessionUtils utils = SessionUtils.parseQueryResult(table);
		
		context.getExternalContext().getSessionMap().put("login", utils.getLogin());
		context.getExternalContext().getSessionMap().put("role", utils.getIdRole());
		context.getExternalContext().getSessionMap().put("idPerson", utils.getIdPerson());
		
		return PAGE_MAIN;		
	} 
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		session.invalidate();
		
		label="ZALOGUJ";
		return PAGE_MAIN ;
	}
	
	public String takeRole() {

        String role;

        try {
        FacesContext context = FacesContext.getCurrentInstance();
        role = (String)(context.getExternalContext().getSessionMap().get("role"));
        } catch (Exception e) {
        role = "0";
        }

        return role;
    }

}
