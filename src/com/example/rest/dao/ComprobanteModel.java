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
import com.example.rest.entidades.PedidoDetalle;
import com.example.rest.entidades.Producto;
import com.example.rest.util.MySqlDBConexion;

import lombok.extern.apachecommons.CommonsLog;
@CommonsLog
public class ComprobanteModel {

	
	public ArrayList<Comprobante> listaComprobante(){
		ArrayList<Comprobante> lista = new  ArrayList<Comprobante>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select c.idcliente,c.nombres,o.idcomprobante,o.fechaRegistro,o.fechaPago,o.estado,o.idpedido \r\n" + 
						"from cliente c join pedido p on c.idcliente=p.idcliente join comprobante o \r\n" +
						"on c.idcliente=o.idcliente";
					
			pstm = conn.prepareStatement(sql);
			log.info(pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Pedido obj = null;
			Comprobante objComp = null;
			Cliente objCli = null;
			while(rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				
				obj = new Pedido();
				obj.setIdPedido(rs.getInt(7));
				
				objComp = new Comprobante();
				objComp.setCliente(objCli);
				objComp.setIdComprobante(rs.getInt(3));
				objComp.setFechaRegistro(rs.getDate(4));
				objComp.setFechaPago(rs.getDate(5));
				objComp.setEstado(rs.getString(6));
				objComp.setPedido(obj);
				
				lista.add(objComp);
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
	
	public ArrayList<Comprobante> listaComprobantexCliente(int idCliente){
		ArrayList<Comprobante> lista = new  ArrayList<Comprobante>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "select distinct c.idcliente,c.nombres,o.idcomprobante,o.fechaRegistro,o.fechaPago,o.estado,o.idpedido \r\n" + 
					"from cliente c join pedido p on c.idcliente=p.idcliente join comprobante o \r\n" +
					"on c.idcliente=o.idcliente where c.idcliente=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
	
			log.info(pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
			Pedido obj = null;
			Comprobante objComp = null;
			Cliente objCli = null;
			while(rs.next()) {
				objCli = new Cliente();
				objCli.setIdCliente(rs.getInt(1));
				objCli.setNombres(rs.getString(2));
				
				obj = new Pedido();
				obj.setIdPedido(rs.getInt(7));
				
				objComp = new Comprobante();
				objComp.setCliente(objCli);
				objComp.setIdComprobante(rs.getInt(3));
				objComp.setFechaRegistro(rs.getDate(4));
				objComp.setFechaPago(rs.getDate(5));
				objComp.setEstado(rs.getString(6));
				objComp.setPedido(obj);
				
				lista.add(objComp);
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
	public int inserta(Comprobante comp) {
		log.info("---> En MySqlComprobante-> inserta");

		
		int contador = -1;
		Connection conn = null;
		PreparedStatement pstm1 = null, pstm2 = null, pstm3 = null;

		try {
			conn = MySqlDBConexion.getConexion();
			
			conn.setAutoCommit(false);

			// se crea el sql de la cabecera
			String sql1 = "insert into comprobante values(null,curtime(),?,'PAGADOS',?,?,?)";
			pstm1 = conn.prepareStatement(sql1);
			pstm1.setDate(1, comp.getFechaPago());
			pstm1.setInt(2, comp.getPedido().getIdPedido());
			pstm1.setInt(3, comp.getCliente().getIdCliente());
			pstm1.setInt(4, comp.getUsuario().getIdUsuario());
			pstm1.executeUpdate();
			log.info(pstm1);
			
			// se obtiene el idBoleta insertado
			String sql2 = "select max(idComprobante) from comprobante";
			pstm2 = conn.prepareStatement(sql2);
			log.info(pstm2);
			ResultSet rs = pstm2.executeQuery();
			rs.next();
			int idComprobante = rs.getInt(1);

			// se inserta el detalle de comprobante
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
	public ArrayList<PedidoDetalle> llenartablaProductos(int idPed){
		ArrayList<PedidoDetalle> lista = new  ArrayList<PedidoDetalle>();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			//1 Se realiza la conexión a la bd
			conn = MySqlDBConexion.getConexion();
			
			//2 Se prepara el SQL
			String sql = "SELECT P.idproducto,PR.nombre,P.precio,P.cantidad from pedido_has_producto P Join producto PR on P.idproducto=PR.idproducto where P.idpedido=?";
					
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idPed);
			log.info(pstm);
			
			//3 se ejecuta el sql en la BD
			rs = pstm.executeQuery();
			
			//4 se pasa los datos del RS al ArrayList
		
			PedidoDetalle objPedido = null;
			Producto objProd = null;
			while(rs.next()) {
				objProd = new Producto();
												
				objPedido = new PedidoDetalle();
				objPedido.setIdProducto(rs.getInt(1));
				objProd.setNombre(rs.getString(2));
				objPedido.setPrecio(rs.getDouble(3));
				objPedido.setCantidad(rs.getInt(4));		
				
				lista.add(objPedido);
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
