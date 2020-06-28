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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.rest.dao.MarcaModel;
import com.example.rest.dao.OpcionModel;
import com.example.rest.dao.RolModel;
import com.example.rest.dao.UsuarioModel;
import com.example.rest.entidades.Marca;
import com.example.rest.entidades.Opcion;
import com.example.rest.entidades.Rol;
import com.example.rest.entidades.Usuario;

@Path("/servicios")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ServicioRest {
	private static final Log log = LogFactory.getLog(ServicioRest.class);
	private UsuarioModel daoUser = new UsuarioModel();
	private MarcaModel daoMarca = new MarcaModel();
	private OpcionModel daoOpcion = new OpcionModel();
	private RolModel daoRol = new RolModel();
	
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

	// Crud de Marca
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

	// Crud de Opcion
	@GET
	@Path("/opcion")
	public Response listarOpcionTodos() {
		log.info("listars opcion rest ");
		return Response.ok(daoOpcion.listarOpcionTodos()).build();
	}

	@POST
	@Path("/opcion")
	public Response registraOpcion(Opcion obj) {
		log.info("Registra opcion " + obj.getIdOpcion());
		if (daoOpcion.insertaOpcion(obj) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}

	@PUT
	@Path("/opcion")
	public Response atualizaOpcion(Opcion obj) {
		log.info("Actualiza opcion " + obj.getIdOpcion());
		if (daoOpcion.actualizaOpcion(obj) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}

	@DELETE
	@Path("/opcion/{idOpcion}")
	public Response eliminaOpcion(@PathParam("idOpcion") int id) {
		log.info("Elimina opcion " + id);
		if (daoOpcion.eliminaOpcion(id) > 0)
			return Response.ok().build();
		else
			return Response.notModified().build();
	}
	
	
	// Crud de Rol
		@GET
		@Path("/rol")
		public Response listarRolTodos() {
			log.info("listars rol rest ");
			return Response.ok(daoRol.listarRolTodos()).build();
		}

		@POST
		@Path("/rol")
		public Response registraRol(Rol obj) {
			log.info("Registra rol " + obj.getIdRol());
			if (daoRol.insertaRol(obj) > 0)
				return Response.ok().build();
			else
				return Response.notModified().build();
		}

		@PUT
		@Path("/rol")
		public Response atualizaRol(Rol obj) {
			log.info("Actualiza rol " + obj.getIdRol());
			if (daoRol.actualizaRol(obj) > 0)
				return Response.ok().build();
			else
				return Response.notModified().build();
		}

		@DELETE
		@Path("/rol/{idRol}")
		public Response eliminaRol(@PathParam("idRol") int id) {
			log.info("Elimina rol " + id);
			if (daoRol.eliminaRol(id) > 0)
				return Response.ok().build();
			else
				return Response.notModified().build();
		}
}