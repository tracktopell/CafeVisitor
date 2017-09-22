/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.dao;

import com.tracktopell.cafevisitor.model.Registro;
import java.util.List;

/**
 *
 * @author alfredo
 */
public interface RegistroDAO {
	List<Registro> getAll();
	List<Registro> getAllToday();

	Registro  getRegistro(Integer RegistroId);
	Registro  edit(Registro Registro) throws EntidadInexistenteException;
	Registro  persist(Registro producto) throws EntidadExistenteException;
	Registro  delete(Registro Registro) throws EntidadInexistenteException;
}
