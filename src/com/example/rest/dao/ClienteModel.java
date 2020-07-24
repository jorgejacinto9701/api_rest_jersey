package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.entidades.Cliente;
import com.example.rest.entidades.Ubigeo;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class ClienteModel {

	public List<Cliente> listarClientePorNombre(String filtro) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			String sql = "select * from cliente where nombres like ? or apellidos like ?";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, filtro + "%");
			pstm.setString(2, filtro + "%");
			log.info(pstm);
			rs = pstm.executeQuery();
			Cliente bean = null;
			Ubigeo beanUbigeo = null;
			while (rs.next()) {
				bean = new Cliente();
				bean.setIdCliente(rs.getInt(1));
				bean.setNombres(rs.getString(2));
				bean.setApellidos(rs.getString(3));
				bean.setDni(rs.getString(4));
				bean.setCorreo(rs.getString(5));
				bean.setFechaRegistro(rs.getDate(6));
				bean.setLogin(rs.getString(7));
				bean.setPassword(rs.getString(8));
				bean.setDireccion(rs.getString(9));
				bean.setEstado(rs.getString(10));

				beanUbigeo = new Ubigeo();
				beanUbigeo.setIdUbigeo(rs.getInt(11));

				lista.add(bean);
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

	public List<Cliente> listarClienteTodos() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			String sql = "select * from cliente";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			rs = pstm.executeQuery();
			Cliente bean = null;
			Ubigeo beanUbigeo = null;
			while (rs.next()) {
				bean = new Cliente();
				bean.setIdCliente(rs.getInt(1));
				bean.setNombres(rs.getString(2));
				bean.setApellidos(rs.getString(3));
				bean.setDni(rs.getString(4));
				bean.setCorreo(rs.getString(5));
				bean.setFechaRegistro(rs.getDate(6));
				bean.setLogin(rs.getString(7));
				bean.setPassword(rs.getString(8));
				bean.setDireccion(rs.getString(9));
				bean.setEstado(rs.getString(10));

				beanUbigeo = new Ubigeo();
				beanUbigeo.setIdUbigeo(rs.getInt(11));

				lista.add(bean);
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

	public int insertaCliente(Cliente c) {
		int salida = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "insert into cliente values(null,?,?,?,?,curdate(),?,?,?,?,?)";
			pstm = con.prepareCall(sql);
			pstm.setString(1, c.getNombres());
			pstm.setString(2, c.getApellidos());
			pstm.setString(3, c.getDni());
			pstm.setString(4, c.getCorreo());
			pstm.setString(5, c.getLogin());
			pstm.setString(6, c.getPassword());
			pstm.setString(7, c.getDireccion());
			pstm.setString(8, c.getEstado());
			pstm.setInt(9, c.getUbigeo().getIdUbigeo());
			System.out.println("SQL-->" + pstm);
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

}
