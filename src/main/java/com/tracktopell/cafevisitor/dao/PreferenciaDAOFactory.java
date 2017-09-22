/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.dao;

import com.tracktopell.cafevisitor.dao.jdbc.DataSourceAdaptor;
import com.tracktopell.cafevisitor.dao.jdbc.PreferenciaDAOJDBC;
import java.sql.SQLException;

/**
 *
 * @author alfredo
 */
public class PreferenciaDAOFactory {

	private static PreferenciaDAO preferenciaDAO;

	public static PreferenciaDAO getPreferenciaDAO() {
		if (preferenciaDAO == null) {
			try {
				preferenciaDAO = new PreferenciaDAOJDBC(DataSourceAdaptor.getConnection());
			} catch (SQLException ex) {
			}
		}
		return preferenciaDAO;
	}
}
