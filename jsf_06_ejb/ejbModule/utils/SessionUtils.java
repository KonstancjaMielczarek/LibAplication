package utils;

public class SessionUtils {
	
	String login;
	String idRole;
	String idPerson;
	
	
	
	private SessionUtils(String login, String idRole, String idPerson) {
		this.login = login;
		this.idRole = idRole;
		this.idPerson = idPerson;
	}
	
	public static SessionUtils parseQueryResult(Object[] result) {
		String login = String.valueOf(result[0]);
		String idRole = String.valueOf(result[1]);
		String idPerson = String.valueOf(result[3]);
		
		return new SessionUtils(login, idRole, idPerson);
	}

	public String getLogin() {
		return login;
	}

	public String getIdRole() {
		return idRole;
	}

	public String getIdPerson() {
		return idPerson;
	}
	

}
