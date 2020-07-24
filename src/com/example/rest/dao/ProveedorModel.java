package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.entidades.Proveedor;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class ProveedorModel {

	public int insertaProveedor(Proveedor p) {
		int salida = -1;

		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();

			String sql = "insert into proveedor values(null,?,?,?,?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getRazonSocial());
			pstm.setString(2, p.getRuc());
			pstm.setString(3, p.getDireccion());
			pstm.setString(4, p.getTelefono());
			pstm.setString(5, p.getCelular());
			pstm.setString(6, p.getContacto());
			pstm.setString(7, p.getEstado());
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

	public List<Proveedor> listaProveedor() {
		ArrayList<Proveedor> data = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "select * from proveedor";
			pstm = con.prepareStatement(sql);
			log.info("SQL-->" + pstm);

			rs = pstm.executeQuery();
			Proveedor c = null;
			while (rs.next()) {
				c = new Proveedor();
				c.setIdproveedor(rs.getInt(1));
				c.setRazonSocial(rs.getString(2));
				c.setRuc(rs.getString(3));
				c.setDireccion(rs.getString(4));
				c.setTelefono(rs.getString(5));
				c.setCelular(rs.getString(6));
				c.setContacto(rs.getString(7));
				c.setEstado(rs.getString(8));
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

	public int actualizaProveedor(Proveedor p) {
		int actualizados = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "update proveedor set razonsocial=?, ruc=?,direccion=?,telefono=?,celular=?,"
					+ "contacto=?,estado=? where idproveedor=?";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getRazonSocial());
			pstm.setString(2, p.getRuc());
			pstm.setString(3, p.getDireccion());
			pstm.setString(4, p.getTelefono());
			pstm.setString(5, p.getCelular());
			pstm.setString(6, p.getContacto());
			pstm.setString(7, p.getEstado());
			pstm.setInt(8, p.getIdproveedor());
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

	public int eliminaProveedor(int idproveedor) {
		int eliminados = -1;
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = MySqlDBConexion.getConexion();
			String sql = "delete from proveedor where idproveedor=?";
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, idproveedor);

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
