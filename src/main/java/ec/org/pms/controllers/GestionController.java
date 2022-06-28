package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Eje;
import ec.org.pms.models.Indicador;
import ec.org.pms.payload.response.componenteResponse.Componente;
import ec.org.pms.services.GestionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class GestionController {
	@Autowired
	private GestionService gestionService;
	
	@GetMapping(value = "/gestion/allComponentes")
	public List<Componente> allComponentes() {
		return gestionService.findAllComponentes();
	}
	
	@PostMapping(value = "/gestion/saveComponente")
	public List<Componente> saveComponente(@RequestBody Componente componente) {
		return gestionService.saveComponente(componente);
	}
	
	@GetMapping(value = "/gestion/getComponente/{id}")
	public Componente getComponente(@PathVariable Integer id) {
		return gestionService.findComponente(id);
	}
	
	@GetMapping(value = "/gestion/allEjes/{id}")
	public List<Eje> allEjes(@PathVariable Integer id) {
		return gestionService.findAllEjes(id);
	}
	
	@PostMapping(value = "/gestion/saveEje")
	public List<Eje> saveEje(@RequestBody Eje eje) {
		return gestionService.saveEje(eje);
	}
	
	@GetMapping(value = "/gestion/getEje/{id}")
	public Eje getEje(@PathVariable Integer id) {
		return gestionService.findEje(id);
	}
	
	@GetMapping(value = "/gestion/allIndicadores/{id}")
	public List<Indicador> allIndicadores(@PathVariable Integer id) {
		return gestionService.findAllIndicadores(id);
	}
	
	@PostMapping(value = "/gestion/saveIndicador")
	public List<Indicador> saveIndicador(@RequestBody Eje eje) {
		return gestionService.saveIndicador(eje);
	}
	
	@GetMapping(value = "/gestion/getIndicador/{id}")
	public Indicador getIndicador(@PathVariable Integer id) {
		return gestionService.findIndicador(id);
	}

}
