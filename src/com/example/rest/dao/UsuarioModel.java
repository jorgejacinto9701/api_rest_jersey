package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.rest.entidades.Usuario;
import com.example.rest.util.MySqlDBConexion;

public class UsuarioModel {

	private static final Log log = LogFactory.getLog(UsuarioModel.class);

	public Usuario login(Usuario bean) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Usuario obj = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select * from usuario where login = ? and password =? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, bean.getLogin());
			pstm.setString(2, bean.getPassword());
			log.info(pstm);
			rs = pstm.executeQuery();
			while (rs.next()) {
				obj = new Usuario();
				obj.setIdUsuario(rs.getInt(1));
				obj.setNombre(rs.getString(2));
				obj.setApellido(rs.getString(3));
				obj.setDni(rs.getString(4));
				obj.setLogin(rs.getString(5));
				obj.setPassword(rs.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)	  rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {
			}
		}
		return obj;
	}

	public List<Usuario> listarTodos() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			String sql = "select * from usuario";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			rs = pstm.executeQuery();
			Usuario obj = null;
			while (rs.next()) {
				obj = new Usuario();
				obj.setIdUsuario(rs.getInt(1));
				obj.setNombre(rs.getString(2));
				obj.setApellido(rs.getString(3));
				obj.setDni(rs.getString(4));
				obj.setLogin(rs.getString(5));
				obj.setPassword(rs.getString(6));
				lista.add(obj);
			}
		} catch (Exception e) {
			log.info(e);
		} finally {
			try {
				if (rs != null)rs.close();
				if (pstm != null)pstm.close();
				if (conn != null)conn.close();
			} catch (SQLException e) {}
		}
		return lista;
	}

}
