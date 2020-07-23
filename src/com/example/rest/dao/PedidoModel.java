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
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;
@CommonsLog
public class PedidoModel {

	public int inserta(Pedido pedido) {
		log.info("---> En MySqlPedido-> inserta");

		int contador = -1;
		Connection conn = null;
		PreparedStatement pstm1 = null, pstm2 = null, pstm3 = null;
		try {
			conn = MySqlDBConexion.getConexion();
			// Se anula el auto envío
			conn.setAutoCommit(false);
			log.info(pedido.getUbigeo().getIdUbigeo()+"");
			// se crea el sql de la cabecera
			String sql1 = "insert into pedido values(null,curtime(),?,?,'PENDIENTE',?,?,?)";
			pstm1 = conn.prepareStatement(sql1);
			pstm1.setDate(1,pedido.getFechaEntrega());
			pstm1.setString(2, pedido.getLugarEntrega());
			pstm1.setInt(3, pedido.getCliente().getIdCliente());
			pstm1.setInt(4,pedido.getUbigeo().getIdUbigeo());
			pstm1.setInt(5, pedido.getUsuario().getIdUsuario());
			pstm1.executeUpdate();
			log.info(pstm1);
			
			// se obtiene el idProducto insertado
			String sql2 = "select max(idPedido) from pedido";
			pstm2 = conn.prepareStatement(sql2);
			log.info(pstm2);
			ResultSet rs = pstm2.executeQuery();
			rs.next();
			int idPedido = rs.getInt(1);

			// se inserta el detalle de pedido
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

			// se ejecuta todos los SQL en la base de datos
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				// se vuelva a un inicio
				// No permite un SQL por separado
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
	
	public ArrayList<Pedido> listaPedido(){
		ArrayList<Pedido> lista = new  ArrayList<Pedido>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select c.idcliente,c.nombres,p.idpedido,p.fechaRegistro,"
					+ "p.fechaEntrega,p.lugarEntrega,p.estado,u.idubigeo from pedido p join cliente c \r\n" + 
					"on p.idcliente=c.idcliente join ubigeo u on p.idubigeo=u.idubigeo ";
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Pedido obj = null;
			Ubigeo objUbi = null;
			Cliente objCli = null;
			while(rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				
				objUbi = new Ubigeo();
				objUbi.setIdUbigeo(rs.getInt(8));
				
				obj = new Pedido();
				obj.setCliente(objCli);
				obj.setIdPedido(rs.getInt(3));
				obj.setFechaRegistro(rs.getDate(4));
				obj.setFechaEntrega(rs.getDate(5));
				obj.setLugarEntrega(rs.getString(6));
				obj.setEstado(rs.getString(7));
				obj.setUbigeo(objUbi);
				
				lista.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close(); 
			} catch (Exception e2) {}
		}
		
		return lista;
	}
	
	public ArrayList<Pedido> listaPedidoporCliente(int idCliente){
		ArrayList<Pedido> lista = new  ArrayList<Pedido>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select c.idcliente,c.nombres,p.idpedido,p.fechaRegistro,p.fechaEntrega,p.lugarEntrega,p.estado,u.idubigeo \r\n" + 
					"from sistema_delivery_simple.pedido p join sistema_delivery_simple.cliente c \r\n" + 
					"on p.idcliente=c.idcliente join ubigeo u on p.idubigeo=u.idubigeo where c.idcliente=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
	
			log.info(pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Pedido obj = null;
			Ubigeo objUbi = null;
			Cliente objCli = null;
			while(rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				
				objUbi = new Ubigeo();
				objUbi.setIdUbigeo(rs.getInt(8));
				
				obj = new Pedido();
				obj.setCliente(objCli);
				obj.setIdPedido(rs.getInt(3));
				obj.setFechaRegistro(rs.getDate(4));
				obj.setFechaEntrega(rs.getDate(5));
				obj.setLugarEntrega(rs.getString(6));
				obj.setEstado(rs.getString(7));
				obj.setUbigeo(objUbi);
				
				lista.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstm != null) pstm.close();
				if (conn != null) conn.close(); 
			} catch (Exception e2) {}
		}
		
		return lista;
	}

}





