package com.example.rest.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.rest.dao.MarcaModel;
import com.example.rest.dao.UbigeoModel;
import com.example.rest.dao.UsuarioModel;
import com.example.rest.entidades.Marca;
import com.example.rest.entidades.Ubigeo;
import com.example.rest.entidades.Usuario;

import lombok.extern.apachecommons.CommonsLog;

@Path("/servicios")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@CommonsLog
public class ServicioRest {
	
	private UsuarioModel daoUser = new UsuarioModel();
	private MarcaModel daoMarca = new MarcaModel();
	private UbigeoModel daoUbigeo = new UbigeoModel();
	

	@GET
	@Path("/login")
	public Response login(Usuario obj) {
		log.info("login rest ");
		return Response.ok(daoUser.login(obj)).build();
	}

	@GET
	@Path("/usuario")
	public Response listarUsuarioTodos() {
		log.info("listar usuario rest ");
		return Response.ok(daoUser.listarTodos()).build();
	}

	//-----------------------------------------------
	// Marca
	//-----------------------------------------------
	
	@GET
	@Path("/marca")
	public Response listarMarcaTodos() {
		log.info("listars marca rest ");
		return Response.ok(daoMarca.listarMarcaTodos()).build();
	}

	@POST
	@Path("/marca")
	public Response registraMarca(Marca obj) {
		log.info("Registra marca " + obj.getIdMarca());
		if (daoMarca.insertaMarca(obj) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}

	@PUT
	@Path("/marca")
	public Response atualizaMarca(Marca obj) {
		log.info("Actualiza marca " + obj.getIdMarca());
		if (daoMarca.actualizaMarca(obj) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}

	@DELETE
	@Path("/marca/{idMarca}")
	public Response eliminaMarca(@PathParam("idMarca") int id) {
		log.info("Elimina usuario " + id);
		if (daoMarca.eliminaMarca(id) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}

	
	//-----------------------------------------------
	// Ubigeo
	//-----------------------------------------------
	
	@GET
	@Path("/departamentos")
	public Response listarDepartamentos() {
		log.info("listar departamentos");
		return Response.ok(daoUbigeo.listarDepartamentos()).build();
	}
	
	@GET
	@Path("/provincias/{idDepa}")
	public Response listarProvincias(@PathParam("idDepa") String idDepa) {
		log.info("listar provincias");
		Ubigeo obj = new Ubigeo();
		obj.setDepartamento(idDepa);
		return Response.ok(daoUbigeo.listarProvincia(obj)).build();
	}
	
	
	@GET
	@Path("/distritos/{idDepa}/{idPro}")
	public Response listarDistritos(@PathParam("idDepa") String idDepa,@PathParam("idPro") String idPro) {
		log.info("listar Distritos");
		Ubigeo obj = new Ubigeo();
		obj.setDepartamento(idDepa);
		obj.setProvincia(idPro);
		return Response.ok(daoUbigeo.listarDistrito(obj)).build();
	}
}