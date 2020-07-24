package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.rest.entidades.Cliente;
import com.example.rest.entidades.Comprobante;
import com.example.rest.entidades.ComprobanteDetalle;
import com.example.rest.entidades.Pedido;
import com.example.rest.entidades.Usuario;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class ComprobanteModel {

	public ArrayList<Comprobante> listaComprobante() {
		log.info("---> En ComprobanteModel -> listaComprobante");

		ArrayList<Comprobante> lista = new ArrayList<Comprobante>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		;
		ResultSet rs = null, rs1 = null;
		ArrayList<ComprobanteDetalle> listaDetalle = null;

		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idcomprobante,p.fechaRegistro,p.fechaPago,p.estado,p.idpedido,p.idusuario from cliente c join comprobante p on c.idcliente=p.idcliente ";
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			rs = pstm.executeQuery();

			String sql1 = "SELECT * FROM comprobante_has_producto where idcomprobante=?";
			pstm1 = conn.prepareStatement(sql1);

			Pedido obj = null;
			Comprobante objComp = null;
			ComprobanteDetalle objDetalle = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				obj = new Pedido();
				obj.setIdPedido(rs.getInt(8));

				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(9));
				
				objComp = new Comprobante();
				objComp.setCliente(objCli);
				objComp.setIdComprobante(rs.getInt(4));
				objComp.setFechaRegistro(rs.getDate(5));
				objComp.setFechaPago(rs.getDate(6));
				objComp.setEstado(rs.getString(7));
				objComp.setPedido(obj);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<ComprobanteDetalle>();
				while (rs1.next()) {
					objDetalle = new ComprobanteDetalle();
					objDetalle.setIdComprobante(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					objComp.setDetalles(listaDetalle);
				}

				lista.add(objComp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (rs1 != null)
					rs1.close();
				if (pstm1 != null)
					pstm1.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
			}
		}

		return lista;
	}

	public ArrayList<Comprobante> listaComprobantePorCliente(int idCliente) {
		log.info("---> En ComprobanteModel -> listaComprobantexCliente");

		ArrayList<Comprobante> lista = new ArrayList<Comprobante>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		;
		ResultSet rs = null, rs1 = null;
		ArrayList<ComprobanteDetalle> listaDetalle = null;

		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idcomprobante,p.fechaRegistro,p.fechaPago,p.estado,p.idpedido,p.idusuario from cliente c join comprobante p on c.idcliente=p.idcliente where c.idcliente = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
			log.info(pstm);
			rs = pstm.executeQuery();

			String sql1 = "SELECT * FROM comprobante_has_producto where idcomprobante=?";
			pstm1 = conn.prepareStatement(sql1);

			Pedido obj = null;
			Comprobante objComp = null;
			ComprobanteDetalle objDetalle = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				obj = new Pedido();
				obj.setIdPedido(rs.getInt(8));

				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(9));

				
				objComp = new Comprobante();
				objComp.setCliente(objCli);
				objComp.setIdComprobante(rs.getInt(4));
				objComp.setFechaRegistro(rs.getDate(5));
				objComp.setFechaPago(rs.getDate(6));
				objComp.setEstado(rs.getString(7));
				objComp.setPedido(obj);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<ComprobanteDetalle>();
				while (rs1.next()) {
					objDetalle = new ComprobanteDetalle();
					objDetalle.setIdComprobante(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					objComp.setDetalles(listaDetalle);
				}

				lista.add(objComp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (rs1 != null)
					rs1.close();
				if (pstm1 != null)
					pstm1.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
			}
		}

		return lista;
	}

	public ArrayList<Comprobante> listaComprobantePorId(int idcomprobante) {
		log.info("---> En ComprobanteModel -> listaComprobantePorId");

		ArrayList<Comprobante> lista = new ArrayList<Comprobante>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		;
		ResultSet rs = null, rs1 = null;
		ArrayList<ComprobanteDetalle> listaDetalle = null;

		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idcomprobante,p.fechaRegistro,p.fechaPago,p.estado,p.idpedido,p.idusuario from cliente c join comprobante p on c.idcliente=p.idcliente where p.idcomprobante = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idcomprobante);
			log.info(pstm);
			rs = pstm.executeQuery();

			String sql1 = "SELECT * FROM comprobante_has_producto where idcomprobante=?";
			pstm1 = conn.prepareStatement(sql1);

			Pedido obj = null;
			Comprobante objComp = null;
			ComprobanteDetalle objDetalle = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				obj = new Pedido();
				obj.setIdPedido(rs.getInt(8));

				
				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(9));
				
				
				objComp = new Comprobante();
				objComp.setCliente(objCli);
				objComp.setIdComprobante(rs.getInt(4));
				objComp.setFechaRegistro(rs.getDate(5));
				objComp.setFechaPago(rs.getDate(6));
				objComp.setEstado(rs.getString(7));
				objComp.setPedido(obj);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<ComprobanteDetalle>();
				while (rs1.next()) {
					objDetalle = new ComprobanteDetalle();
					objDetalle.setIdComprobante(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					objComp.setDetalles(listaDetalle);
				}

				lista.add(objComp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (rs1 != null)
					rs1.close();
				if (pstm1 != null)
					pstm1.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
			}
		}

		return lista;
	}

	public int inserta(Comprobante comp) {
		log.info("---> En MySqlComprobante-> inserta");

		int contador = -1;
		Connection conn = null;
		PreparedStatement pstm1 = null, pstm2 = null, pstm3 = null;

		try {
			conn = MySqlDBConexion.getConexion();
			conn.setAutoCommit(false);

			String sql1 = "insert into comprobante values(null,curtime(),?,'PAGADOS',?,?,?)";
			pstm1 = conn.prepareStatement(sql1);
			pstm1.setDate(1, comp.getFechaPago());
			pstm1.setInt(2, comp.getPedido().getIdPedido());
			pstm1.setInt(3, comp.getCliente().getIdCliente());
			pstm1.setInt(4, comp.getUsuario().getIdUsuario());
			pstm1.executeUpdate();
			log.info(pstm1);

			String sql2 = "select max(idComprobante) from comprobante";
			pstm2 = conn.prepareStatement(sql2);
			log.info(pstm2);
			ResultSet rs = pstm2.executeQuery();
			rs.next();
			int idComprobante = rs.getInt(1);
			String sql3 = "insert into comprobante_has_producto values(?,?,?,?)";
			pstm3 = conn.prepareStatement(sql3);
			for (ComprobanteDetalle aux : comp.getDetalles()) {
				pstm3.setInt(1, idComprobante);
				pstm3.setInt(2, aux.getIdProducto());
				pstm3.setDouble(3, aux.getPrecio());
				pstm3.setInt(4, aux.getCantidad());
				pstm3.executeUpdate();
				log.info(pstm3);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstm1.close();
				pstm2.close();
				pstm3.close();

			} catch (SQLException e) {
			}
		}
		return contador;
	}

}
