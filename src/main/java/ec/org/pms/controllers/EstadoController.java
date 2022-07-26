package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Estado;
import ec.org.pms.services.EstadoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/estados")
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping(value = "all")
	public List<Estado> listAdd() {
		return estadoService.findAllByTipo();
	}
	
	@GetMapping(value = "/certificacion/{id}")
	public String estadoCertificacion(@PathVariable String id) {
		return estadoService.esCertificable(Integer.parseInt(id));
	}
	
	@GetMapping(value = "/certificacion/evaluar/{id}")
	public boolean estadoEvaluar(@PathVariable String id) {
		return estadoService.evaluar(Integer.parseInt(id));
	}

}
