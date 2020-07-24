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

	public int insertaTipoReclamo(TipoReclamo c) {
		int salida = -1;

		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "insert into tiporeclamo values(null,?,?,curtime())";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, c.getDescripcion());
			pstm.setString(2, c.getEstado());
			log.info("SQL-->" + pstm);
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
			}
		}
		return salida;
	}

	public List<TipoReclamo> listaTipoReclamo() {
		ArrayList<TipoReclamo> data = new ArrayList<TipoReclamo>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "select * from tiporeclamo";
			pstm = con.prepareStatement(sql);
			log.info("SQL-->" + pstm);
			rs = pstm.executeQuery();
			TipoReclamo c = null;
			while (rs.next()) {
				c = new TipoReclamo();
				c.setIdTipoReclamo(rs.getInt(1));
				c.setDescripcion(rs.getString(2));
				c.setEstado(rs.getString(3));
				c.setFechaRegistro(rs.getDate(4));
				data.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (con != null)
					con.close();
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
				if (pstm != null)
					pstm.close();
				if (con != null)
					con.close();
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
			String sql = "delete from tiporeclamo where idtiporeclamo=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idTipoReclamo);
			eliminados = pstm.executeUpdate();
			log.info("eliminados :  " + eliminados);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eliminados;
	}

}
