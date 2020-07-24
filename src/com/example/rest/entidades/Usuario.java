package com.example.rest.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Usuario {

	private int idUsuario;
	private String nombre;
	private String apellido;
	private String dni;
	private String login;
	private String password;
	
	

}
