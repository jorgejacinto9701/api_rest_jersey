package com.example.rest.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Producto {

	private int idProducto;
	private String nombre;
	private String serie;
	private double precio;
	private int stock;
	private Marca marca;
	private Pais pais;
	private Proveedor proveedor;
	
	
	
	
}
