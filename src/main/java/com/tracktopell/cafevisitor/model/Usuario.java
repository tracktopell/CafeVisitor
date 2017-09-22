/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.model;

/**
 *
 * @author Softtek
 */
public class Usuario {
	private String id;
	private String password;
	private String nombre;
	private Integer esAdmin;	

	public Usuario(String id, String password, String nombre, Integer esAdmin) {
		this.id = id;
		this.password = password;
		this.nombre = nombre;
		this.esAdmin = esAdmin;
	}
	
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the esAdmin
	 */
	public Integer getEsAdmin() {
		return esAdmin;
	}

	/**
	 * @param esAdmin the esAdmin to set
	 */
	public void setEsAdmin(Integer esAdmin) {
		this.esAdmin = esAdmin;
	}
}
