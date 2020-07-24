package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.rest.entidades.Marca;
import com.example.rest.entidades.Pais;
import com.example.rest.entidades.Producto;
import com.example.rest.entidades.Proveedor;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class ProductoModel {
	
	
	public List<Producto> listarProductoPorNombre(String filtro) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<Producto> lista = new ArrayList<Producto>();
		try {
			String sql = "select * from producto where nombre like ?";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, filtro+"%");
			log.info(pstm);
			rs = pstm.executeQuery();
			Producto bean = null;
			Marca beanMarca = null;
			Pais beanPais = null;
			Proveedor beanProveedor = null;
			
			while (rs.next()) {
				bean = new Producto();
				bean.setIdProducto(rs.getInt(1));
				bean.setNombre(rs.getString(2));
				bean.setSerie(rs.getString(3));
				bean.setPrecio(rs.getDouble(4));
				bean.setStock(rs.getInt(5));
				
				beanMarca = new Marca();
				beanMarca.setIdMarca(rs.getInt(6));

				beanPais = new Pais();
				beanPais.setIdPais(rs.getInt(7));

				beanProveedor = new Proveedor();
				beanProveedor.setIdproveedor(rs.getInt(8));
				
				bean.setMarca(beanMarca);
				bean.setPais(beanPais);
				bean.setProveedor(beanProveedor);
				
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
	public List<Producto> listarProductoTodos() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		List<Producto> lista = new ArrayList<Producto>();
		try {
			String sql = "select * from producto";
			conn = MySqlDBConexion.getConexion();
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			rs = pstm.executeQuery();
			Producto bean = null;
			Marca beanMarca = null;
			Pais beanPais = null;
			Proveedor beanProveedor = null;
			
			while (rs.next()) {
				bean = new Producto();
				bean.setIdProducto(rs.getInt(1));
				bean.setNombre(rs.getString(2));
				bean.setSerie(rs.getString(3));
				bean.setPrecio(rs.getDouble(4));
				bean.setStock(rs.getInt(5));
				
				beanMarca = new Marca();
				beanMarca.setIdMarca(rs.getInt(6));

				beanPais = new Pais();
				beanPais.setIdPais(rs.getInt(7));

				beanProveedor = new Proveedor();
				beanProveedor.setIdproveedor(rs.getInt(8));
				
				bean.setMarca(beanMarca);
				bean.setPais(beanPais);
				bean.setProveedor(beanProveedor);
				
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
	
	
	public int insertaProducto(Producto p) {
		int salida = -1;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = MySqlDBConexion.getConexion();
			String sql = "insert into producto values(null,?,?,?,?,?,?,?)";
			pstm = con.prepareCall(sql);
			pstm.setString(1, p.getNombre());
			pstm.setString(2, p.getSerie());
			pstm.setDouble(3, p.getPrecio());
			pstm.setInt(4, p.getStock());
			pstm.setInt(5, p.getMarca().getIdMarca());
			pstm.setInt(6, p.getPais().getIdPais());
			pstm.setInt(7, p.getProveedor().getIdproveedor());

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
}
