package cl.uv.model.base.core.beans;

import java.util.ArrayList;
import java.util.List;

public class AtributosFuncionario {

	private String sn;
	private String cn;
	private String correouv;
	private String givenname;
	private String rut;
	private String mail;
	private List <String> listaRoles = new ArrayList<String>();

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCorreouv() {
		return correouv;
	}

	public void setCorreouv(String correouv) {
		this.correouv = correouv;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public List<String> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<String> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
}
