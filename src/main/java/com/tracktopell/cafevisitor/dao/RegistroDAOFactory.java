/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.dao;

import com.tracktopell.cafevisitor.dao.jdbc.DataSourceAdaptor;
import com.tracktopell.cafevisitor.dao.jdbc.RegistroDAOJDBC;
import java.sql.SQLException;

/**
 *
 * @author alfredo
 */
public class RegistroDAOFactory {

	private static RegistroDAO RegistroDAO;

	public static RegistroDAO getRegistroDAO() {
		if (RegistroDAO == null) {
			try {
				RegistroDAO = new RegistroDAOJDBC(DataSourceAdaptor.getConnection());
			} catch (SQLException ex) {
			}
		}
		return RegistroDAO;
	}
}
