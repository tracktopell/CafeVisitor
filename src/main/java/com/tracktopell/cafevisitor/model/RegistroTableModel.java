/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.cafevisitor.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Softtek
 */
public class RegistroTableModel implements TableModel{
	
	ArrayList<Registro> registroList;
	String columnNames[]={"FOLIO","NOMBRE","GENERO","EDAD APROX.","DESTINO","DEPARTAMENTO","ENTRADA","SALIDA","FOTO","RESTRINGIDO","USUARIO"};
	SimpleDateFormat sdfHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public RegistroTableModel(ArrayList<Registro> registroList) {
		this.registroList = registroList;
	}
	
	public int getRowCount() {
		return this.registroList.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Registro r= registroList.get(rowIndex);
		if(columnIndex == 0) return String.valueOf(r.getId());
		if(columnIndex == 1) return r.getNombre();
		if(columnIndex == 2) return r.getGenero();
		if(columnIndex == 3) return r.getEdadAproximada();
		if(columnIndex == 4) return r.getDestino();
		if(columnIndex == 5) return r.getDepartamento();
		if(columnIndex == 6) return sdfHora.format(r.getEntrada());
		if(columnIndex == 7) return r.getSalida()!=null?sdfHora.format(r.getSalida()):"";
		if(columnIndex == 8) return r.getFoto();
		if(columnIndex == 9) return r.getRestringido()==1?"S":"N";
		if(columnIndex == 10)return r.getUsuario_id();
		return null; 
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}

	public void addTableModelListener(TableModelListener l) {
		
	}

	public void removeTableModelListener(TableModelListener l) {
		
	}

	public Registro getAt(int cteSel) {
		return registroList.get(cteSel);
	}
	
}
