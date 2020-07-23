package com.example.rest.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDetalle {

	private int idPedido;
	private int idProducto;
	private double precio;
	private int cantidad;
	
	
	
	
}
