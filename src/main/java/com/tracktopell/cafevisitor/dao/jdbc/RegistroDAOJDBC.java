/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.dao.jdbc;

import com.tracktopell.cafevisitor.dao.EntidadExistenteException;
import com.tracktopell.cafevisitor.dao.EntidadInexistenteException;
import com.tracktopell.cafevisitor.dao.RegistroDAO;
import com.tracktopell.cafevisitor.model.Registro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author alfredo
 */
public final class RegistroDAOJDBC implements RegistroDAO{
	private Connection conn;
	private Logger logger;

	public RegistroDAOJDBC(Connection connection) {
		logger = Logger.getLogger(RegistroDAOJDBC.class);
		logger.info("-->> init with connection:"+connection);
		conn = connection;
	}
	
	
	@Override
	public List<Registro> getAllToday() {
		List<Registro> r = new ArrayList<Registro>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		Date d1 = new Date();
		Date d2 = new Date();
		try{
			d2 = sdf.parse(sdf.format(d1));
		} catch(ParseException pe){
			pe.printStackTrace(System.err);
		}
		try {
			ps = conn.prepareStatement("SELECT ID,NOMBRE,GENERO,EDAD_APROXIMADA,DESTINO,DEPARTAMENTO,ENTRADA,SALIDA,FOTO,RESTRINGIDO,USUARIO_ID FROM REGISTRO"
					+ " WHERE ENTRADA >= ? ");
			ps.setDate(1, new java.sql.Date(d2.getTime()));
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Registro x = new Registro();
				x.setId				(rs.getInt("ID"));
				x.setNombre			(rs.getString("NOMBRE"));
				x.setGenero			(rs.getString("GENERO"));
				x.setEdadAproximada (rs.getString("EDAD_APROXIMADA"));
				x.setDestino		(rs.getString("DESTINO"));
				x.setDepartamento	(rs.getString("DEPARTAMENTO"));				
				x.setEntrada		(rs.getTimestamp("ENTRADA"));
				x.setSalida			(rs.getTimestamp("SALIDA"));
				x.setFoto			(rs.getString("FOTO"));
				x.setRestringido	(rs.getInt("RESTRINGIDO"));
				x.setUsuario_id		(rs.getString("USUARIO_ID"));
				r.add(x);
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.err);
			//logger.error("in getAll:", ex);
			r = null;
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return r;
	}

	@Override
	public List<Registro> getAll() {
		List<Registro> r = new ArrayList<Registro>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT ID,NOMBRE,GENERO,EDAD_APROXIMADA,DESTINO,DEPARTAMENTO,ENTRADA,SALIDA,FOTO,RESTRINGIDO,USUARIO_ID FROM REGISTRO");
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Registro x = new Registro();
				
				x.setId				(rs.getInt("ID"));
				x.setNombre			(rs.getString("NOMBRE"));
				x.setGenero			(rs.getString("GENERO"));
				x.setEdadAproximada (rs.getString("EDAD_APROXIMADA"));
				x.setDestino		(rs.getString("DESTINO"));
				x.setDepartamento	(rs.getString("DEPARTAMENTO"));				
				x.setEntrada		(rs.getTimestamp("ENTRADA"));
				x.setSalida			(rs.getTimestamp("SALIDA"));
				x.setFoto			(rs.getString("FOTO"));
				x.setRestringido	(rs.getInt("RESTRINGIDO"));
				x.setUsuario_id		(rs.getString("USUARIO_ID"));
				
				r.add(x);
			}
		}catch(SQLException ex) {
			logger.error("in getAll:", ex);
			r = null;
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return r;
	}

	@Override
	public Registro getRegistro(Integer id) {
		Registro x = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT ID,NOMBRE,GENERO,EDAD_APROXIMADA,DESTINO,DEPARTAMENTO,ENTRADA,SALIDA,FOTO,RESTRINGIDO,USUARIO_ID FROM REGISTRO WHERE ID=?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				x = new Registro();
				
				x.setId				(rs.getInt("ID"));
				x.setNombre			(rs.getString("NOMBRE"));
				x.setGenero			(rs.getString("GENERO"));
				x.setEdadAproximada (rs.getString("EDAD_APROXIMADA"));
				x.setDestino		(rs.getString("DESTINO"));
				x.setDepartamento	(rs.getString("DEPARTAMENTO"));				
				x.setEntrada		(rs.getTimestamp("ENTRADA"));
				x.setSalida			(rs.getTimestamp("SALIDA"));
				x.setFoto			(rs.getString("FOTO"));
				x.setRestringido	(rs.getInt("RESTRINGIDO"));
				x.setUsuario_id		(rs.getString("USUARIO_ID"));
				
			}

		}catch(SQLException ex) {
			logger.error("in getRegistro", ex);			
		} finally {
			if(rs != null) {
				try{
					rs.close();
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return x;
	}

	@Override
	public Registro edit(Registro reg) throws EntidadInexistenteException {
		Registro x = null;
		PreparedStatement ps = null;
		int ra= -1;
		try {
			ps = conn.prepareStatement("UPDATE REGISTRO SET SALIDA=? "+
					" WHERE ID=?");
			
			ps.setTimestamp	(1, new Timestamp(reg.getSalida().getTime()));
			ps.setInt		(2, reg.getId());
			
			ra = ps.executeUpdate();
			
			ps.close();
			
			logger.info("ra="+ra);
		}catch(SQLException ex) {
			logger.error("in insert:", ex);
			x = null;
		} finally {
			if(ps != null) {
				try{
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return x;
	}

	@Override
	public Registro persist(Registro registro) throws EntidadExistenteException {
		Registro x = null;
		PreparedStatement ps = null;
		int ra= -1;
		try {
			ps = conn.prepareStatement("INSERT INTO REGISTRO(ID,NOMBRE,GENERO,EDAD_APROXIMADA,DESTINO,DEPARTAMENTO,ENTRADA,FOTO,RESTRINGIDO,USUARIO_ID) "+
					" VALUES(?,?,?,?,?,?,?,?,?,?)");
			
			ps.setInt		(1, registro.getId());
			ps.setString	(2, registro.getNombre());
			ps.setString	(3, registro.getGenero());
			ps.setString	(4, registro.getEdadAproximada());
			ps.setString	(5, registro.getDestino());
			ps.setString	(6, registro.getDepartamento());
			ps.setTimestamp	(7, new Timestamp(registro.getEntrada().getTime()));
			ps.setString	(8, registro.getFoto());
			ps.setInt		(9, registro.getRestringido());
			ps.setString	(10, registro.getUsuario_id());
			
			ra = ps.executeUpdate();
			
			ps.close();
			
			logger.info("ra="+ra);
		}catch(SQLException ex) {
			logger.error("in insert:", ex);
			x = null;
		} finally {
			if(ps != null) {
				try{
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return x;
	}

	@Override
	public Registro delete(Registro Registro) throws EntidadInexistenteException {
		Registro x = null;
		PreparedStatement ps = null;
		int ra= -1;
		try {
			ps = conn.prepareStatement("DELETE FROM REGISTRO WHERE ID=?");
			ps.setInt(1, Registro.getId());
			
			ra += ps.executeUpdate();
			if(ra != 1) {
				x = null;
				throw new EntidadInexistenteException();
			} else{
				x = Registro;
			}
			logger.info("ra="+ra);
		}catch(SQLException ex) {
			logger.error("in delete:", ex);
			x = null;
		} finally {
			if(ps != null) {
				try{
					ps.close();
				}catch(SQLException ex) {
				}
			}
		}
		return x;
	}	

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
