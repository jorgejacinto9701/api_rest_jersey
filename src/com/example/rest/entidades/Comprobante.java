package com.example.rest.entidades;

import java.sql.Date;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Comprobante {

	private int idComprobante;
	private Date fechaRegistro;
	private Date fechaPago;
	private String estado;
	private Pedido pedido;
	private Cliente cliente;
	private Usuario usuario;
	private ArrayList<ComprobanteDetalle> detalles;

	
	
}
