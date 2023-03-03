package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.payload.request.UsuarioRequest;
import ec.org.pms.payload.response.UsuariosResponse;
import ec.org.pms.payload.response.ZonalesResponse;
import ec.org.pms.services.UsuarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
public class UserController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/all")
	public List<UsuariosResponse> listUsers() {
		return usuarioService.findUsersAdd();
	}
	
	@PostMapping(value = "/save")
	public List<UsuariosResponse> saveUsuario(@RequestBody UsuarioRequest usuario) {
		return (List<UsuariosResponse>) usuarioService.save(usuario);
	}
	
	@GetMapping("/allZonales")
	public List<ZonalesResponse> findZonales() {
		return usuarioService.findZonales();
	}
	
	@PostMapping(value = "/saveZonales")
	public List<ZonalesResponse> saveUsuarioZonal(@RequestBody UsuarioRequest usuario) {
		return (List<ZonalesResponse>) usuarioService.saveUsuarioZonal(usuario);
	}

}
