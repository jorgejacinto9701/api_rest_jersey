package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.entidades.Ubigeo;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class UbigeoModel {

	public List<String> listarDepartamentos() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<String> lista = new ArrayList<String>();
		try {
			String sql = "select distinct (departamento) from ubigeo";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			rs = pstm.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (Exception e) {
			log.info(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	public List<String> listarProvincia(Ubigeo obj) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<String> lista = new ArrayList<String>();
		try {
			String sql = "select distinct (provincia) from ubigeo where departamento=?";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getDepartamento());
			log.info(pstm);
			rs = pstm.executeQuery();
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (Exception e) {
			log.info(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return lista;
	}

	public List<Ubigeo> listarDistrito(Ubigeo obj) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<Ubigeo> lista = new ArrayList<Ubigeo>();
		try {
			String sql = "select idubigeo, distrito from ubigeo where departamento=? and provincia=?";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getDepartamento());
			pstm.setString(2, obj.getProvincia());
			log.info(pstm);
			rs = pstm.executeQuery();
			Ubigeo aux = null;
			while (rs.next()) {
				aux = new Ubigeo();
				aux.setIdUbigeo(rs.getInt(1));
				aux.setDistrito(rs.getString(2));
				lista.add(aux);
			}
		} catch (Exception e) {
			log.info(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return lista;
	}

}
