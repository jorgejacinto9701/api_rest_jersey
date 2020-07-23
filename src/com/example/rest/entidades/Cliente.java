package com.example.rest.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {

	private int idCliente;
	private String nombres;
	private String apellidos;
	private String dni;
	private String correo;
	private String login;
	private String password;
	private String direccion;
	private String estado;
	private Ubigeo ubigeo;

}
