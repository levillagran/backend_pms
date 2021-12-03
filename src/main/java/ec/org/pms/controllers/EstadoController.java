package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Estado;
import ec.org.pms.repositories.EstadoRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/estados")
public class EstadoController {
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping(value = "all")
	public List<Estado> listAdd() {
		return estadoRepository.findAllByTipo(900);
	}

}
