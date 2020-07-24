package com.example.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.rest.entidades.Cliente;
import com.example.rest.entidades.Pedido;
import com.example.rest.entidades.PedidoDetalle;
import com.example.rest.entidades.Ubigeo;
import com.example.rest.entidades.Usuario;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class PedidoModel {

	public int inserta(Pedido pedido) {
		log.info("---> En PedidoModel -> inserta");

		int contador = -1;
		Connection conn = null;
		PreparedStatement pstm1 = null, pstm2 = null, pstm3 = null;
		try {
			conn = MySqlDBConexion.getConexion();

			conn.setAutoCommit(false);
			log.info(pedido.getUbigeo().getIdUbigeo() + "");

			String sql1 = "insert into pedido values(null,curtime(),?,?,'PENDIENTE',?,?,?)";
			pstm1 = conn.prepareStatement(sql1);
			pstm1.setDate(1, pedido.getFechaEntrega());
			pstm1.setString(2, pedido.getLugarEntrega());
			pstm1.setInt(3, pedido.getCliente().getIdCliente());
			pstm1.setInt(4, pedido.getUbigeo().getIdUbigeo());
			pstm1.setInt(5, pedido.getUsuario().getIdUsuario());
			pstm1.executeUpdate();
			log.info(pstm1);

			String sql2 = "select max(idPedido) from pedido";
			pstm2 = conn.prepareStatement(sql2);
			log.info(pstm2);
			ResultSet rs = pstm2.executeQuery();
			rs.next();
			int idPedido = rs.getInt(1);

			String sql3 = "insert into pedido_has_producto values(?,?,?,?)";
			pstm3 = conn.prepareStatement(sql3);
			for (PedidoDetalle aux : pedido.getDetalles()) {
				pstm3.setInt(1, idPedido);
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

	public ArrayList<Pedido> listaPedidoPorId(int idPedido) {
		log.info("---> En PedidoModel -> listaPedidoPorId");

		ArrayList<Pedido> lista = new ArrayList<Pedido>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		ResultSet rs = null, rs1 = null;
		ArrayList<PedidoDetalle> listaDetalle = null;
		try {
			conn = MySqlDBConexion.getConexion();

			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idpedido,p.fechaRegistro,p.fechaEntrega,p.lugarEntrega,p.estado,u.idubigeo,p.idusuario from pedido p join cliente c on p.idcliente=c.idcliente join ubigeo u on p.idubigeo=u.idubigeo where p.idpedido = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idPedido);
			log.info(pstm);
			rs = pstm.executeQuery();

			String sql1 = "SELECT * FROM pedido_has_producto where idpedido=?";
			pstm1 = conn.prepareStatement(sql1);

			Pedido obj = null;
			PedidoDetalle objDetalle = null;
			Ubigeo objUbi = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				objUbi = new Ubigeo();
				objUbi.setIdUbigeo(rs.getInt(9));

				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(10));

				obj = new Pedido();
				obj.setCliente(objCli);
				obj.setIdPedido(rs.getInt(4));
				obj.setFechaRegistro(rs.getDate(5));
				obj.setFechaEntrega(rs.getDate(6));
				obj.setLugarEntrega(rs.getString(7));
				obj.setEstado(rs.getString(8));
				obj.setUbigeo(objUbi);
				obj.setUsuario(objUsu);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<PedidoDetalle>();
				while (rs1.next()) {
					objDetalle = new PedidoDetalle();
					objDetalle.setIdPedido(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					obj.setDetalles(listaDetalle);
				}

				lista.add(obj);
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

	public ArrayList<Pedido> listaPedido() {
		log.info("---> En PedidoModel -> listaPedido");
		ArrayList<Pedido> lista = new ArrayList<Pedido>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		ResultSet rs = null, rs1 = null;
		ArrayList<PedidoDetalle> listaDetalle = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idpedido,p.fechaRegistro,p.fechaEntrega,p.lugarEntrega,p.estado,u.idubigeo,p.idusuario from pedido p join cliente c on p.idcliente=c.idcliente join ubigeo u on p.idubigeo=u.idubigeo";
			pstm = conn.prepareStatement(sql);
			log.info(pstm);

			String sql1 = "SELECT * FROM pedido_has_producto where idpedido=?";
			pstm1 = conn.prepareStatement(sql1);

			rs = pstm.executeQuery();

			Pedido obj = null;
			PedidoDetalle objDetalle = null;
			Ubigeo objUbi = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				objUbi = new Ubigeo();
				objUbi.setIdUbigeo(rs.getInt(9));

				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(10));

				obj = new Pedido();
				obj.setCliente(objCli);
				obj.setIdPedido(rs.getInt(4));
				obj.setFechaRegistro(rs.getDate(5));
				obj.setFechaEntrega(rs.getDate(6));
				obj.setLugarEntrega(rs.getString(7));
				obj.setEstado(rs.getString(8));
				obj.setUbigeo(objUbi);
				obj.setUsuario(objUsu);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<PedidoDetalle>();
				while (rs1.next()) {
					objDetalle = new PedidoDetalle();
					objDetalle.setIdPedido(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					obj.setDetalles(listaDetalle);
				}

				lista.add(obj);
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

	public ArrayList<Pedido> listaPedidoporCliente(int idCliente) {
		log.info("---> En PedidoModel -> listaPedidoporCliente");

		ArrayList<Pedido> lista = new ArrayList<Pedido>();

		Connection conn = null;
		PreparedStatement pstm = null, pstm1 = null;
		ResultSet rs = null, rs1 = null;
		ArrayList<PedidoDetalle> listaDetalle = null;
		try {
			conn = MySqlDBConexion.getConexion();

			String sql = "select c.idcliente,c.nombres,c.apellidos,p.idpedido,p.fechaRegistro,p.fechaEntrega,p.lugarEntrega,p.estado,u.idubigeo,p.idusuario from pedido p join cliente c on p.idcliente=c.idcliente join ubigeo u on p.idubigeo=u.idubigeo where p.idcliente = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
			log.info(pstm);

			String sql1 = "SELECT * FROM pedido_has_producto where idpedido=?";
			pstm1 = conn.prepareStatement(sql1);

			rs = pstm.executeQuery();

			Pedido obj = null;
			PedidoDetalle objDetalle = null;
			Ubigeo objUbi = null;
			Cliente objCli = null;
			Usuario objUsu = null;
			while (rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				objCli.setApellidos(rs.getString(3));

				objUbi = new Ubigeo();
				objUbi.setIdUbigeo(rs.getInt(9));

				objUsu = new Usuario();
				objUsu.setIdUsuario(rs.getInt(10));

				obj = new Pedido();
				obj.setCliente(objCli);
				obj.setIdPedido(rs.getInt(4));
				obj.setFechaRegistro(rs.getDate(5));
				obj.setFechaEntrega(rs.getDate(6));
				obj.setLugarEntrega(rs.getString(7));
				obj.setEstado(rs.getString(8));
				obj.setUbigeo(objUbi);
				obj.setUsuario(objUsu);

				pstm1.setInt(1, rs.getInt(4));
				log.info(pstm1);
				rs1 = pstm1.executeQuery();

				listaDetalle = new ArrayList<PedidoDetalle>();
				while (rs1.next()) {
					objDetalle = new PedidoDetalle();
					objDetalle.setIdPedido(rs1.getInt(1));
					objDetalle.setIdProducto(rs1.getInt(2));
					objDetalle.setPrecio(rs1.getDouble(3));
					objDetalle.setCantidad(rs1.getInt(4));
					listaDetalle.add(objDetalle);
					obj.setDetalles(listaDetalle);
				}

				lista.add(obj);
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

}
