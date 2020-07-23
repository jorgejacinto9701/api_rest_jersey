package com.example.rest.entidades;

import java.sql.Date;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {

	private int idPedido;
	private Date fechaRegistro;
	private Date fechaEntrega;
	private String lugarEntrega;
	private String estado;
	private Cliente cliente;
	private Ubigeo ubigeo;
	private Usuario usuario;
	private ArrayList<PedidoDetalle> detalles;

}
