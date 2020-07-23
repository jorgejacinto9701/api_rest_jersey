package com.example.rest.entidades;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class TipoReclamo {

	private int idTipoReclamo;
	private String descripcion;
	private String estado;
	private Date fechaRegistro;
	

}
