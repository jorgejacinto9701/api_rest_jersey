package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.entidades.TipoReclamo;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;
@CommonsLog
public class TipoReclamoModel {

	//El metodo que va inserta enb la tabla tiporeclamo
	public int insertaTipoReclamo(TipoReclamo c){
		int salida = -1;
		
		Connection con = null;
		PreparedStatement pstm  = null;
		try{
			//1 Conectar a la base de  datos
			con = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "insert into tiporeclamo values(null,?,?,curtime())";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, c.getDescripcion());
			pstm.setString(2, c.getEstado());
			log.info("SQL-->" + pstm);
			
			//3 envia el sql y se recibe la cantidad de registrados
			salida = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();	
		}finally{
			try {
				if(pstm!= null)pstm.close();
				if(con!= null)con.close();
			} catch (Exception e2) {}
		}
		return salida;
	}
	
	public List<TipoReclamo> listaTipoReclamo() {
		ArrayList<TipoReclamo> data = new ArrayList<TipoReclamo>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null; //Trae la data de la BD
		try {
			con = MySqlDBConexion.getConexion();
			String sql ="select * from tiporeclamo";
			pstm = con.prepareStatement(sql);
			log.info("SQL-->" + pstm);
			
			//En rs se trae los datos de la BD segun el SQL
			rs = pstm.executeQuery();
			
			//Se pasa la data del rs al ArrayList(data)
			TipoReclamo c = null;
			while(rs.next()){
				c = new TipoReclamo();
				c.setIdTipoReclamo(rs.getInt("idTipoReclamo"));
				c.setDescripcion(rs.getString("descripcion"));
				c.setEstado(rs.getString("estado"));
				c.setFechaRegistro(rs.getDate("fechaRegistro"));
				data.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public int actualizaTipoReclamo(TipoReclamo c) {
		int actualizados = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "update tiporeclamo set descripcion=?, estado=? where idtiporeclamo=?"; 
			pstm = con.prepareStatement(sql);
			pstm.setString(1, c.getDescripcion());
			pstm.setString(2, c.getEstado());
			pstm.setInt(3, c.getIdTipoReclamo());
			actualizados = pstm.executeUpdate();
			log.info("actualizados :  " + actualizados);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return actualizados;
	}
	
	public int eliminaTipoReclamo(int idTipoReclamo) {
		int eliminados = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql ="delete from tiporeclamo where idtiporeclamo=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idTipoReclamo);
			eliminados = pstm.executeUpdate();
			log.info("eliminados :  " + eliminados);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)pstm.close();
				if (con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eliminados;
	}
	
	
	
}
