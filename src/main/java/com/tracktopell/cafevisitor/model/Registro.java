package com.tracktopell.cafevisitor.model;

import java.sql.Timestamp;

/**
 *
 * @author alfredo estrada
 */
public class Registro {
	private Integer id;
	private String nombre;
	private String genero;
	private String edadAproximada;	
	private String destino;
	private String departamento;
	private Timestamp entrada;
	private Timestamp salida;
	private String foto;
	private Integer restringido;
	private String usuario_id;

	public Registro() {
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the edadAproximada
	 */
	public String getEdadAproximada() {
		return edadAproximada;
	}

	/**
	 * @param edadAproximada the edadAproximada to set
	 */
	public void setEdadAproximada(String edadAproximada) {
		this.edadAproximada = edadAproximada;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the entrada
	 */
	public Timestamp getEntrada() {
		return entrada;
	}

	/**
	 * @param entrada the entrada to set
	 */
	public void setEntrada(Timestamp entrada) {
		this.entrada = entrada;
	}

	/**
	 * @return the salida
	 */
	public Timestamp getSalida() {
		return salida;
	}

	/**
	 * @param salida the salida to set
	 */
	public void setSalida(Timestamp salida) {
		this.salida = salida;
	}

	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * @return the restringido
	 */
	public Integer getRestringido() {
		return restringido;
	}

	/**
	 * @param restringido the restringido to set
	 */
	public void setRestringido(Integer restringido) {
		this.restringido = restringido;
	}

	/**
	 * @return the usuario_id
	 */
	public String getUsuario_id() {
		return usuario_id;
	}

	/**
	 * @param usuario_id the usuario_id to set
	 */
	public void setUsuario_id(String usuario_id) {
		this.usuario_id = usuario_id;
	}

	@Override
	public String toString() {
		return id+","+nombre+","+genero+","+edadAproximada+","+destino+","+departamento+","+entrada+","+salida+","+foto+","+restringido+","+usuario_id;
	}
	
	

}
