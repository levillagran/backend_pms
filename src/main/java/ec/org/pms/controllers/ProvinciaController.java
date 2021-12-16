package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Provincia;
import ec.org.pms.repositories.ProvinciaRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {
	@Autowired
	private ProvinciaRepository provinciaRepository;
	
	@GetMapping(value = "all")
	public List<Provincia> listAdd() {
		return  (List<Provincia>) provinciaRepository.findAllByBandera(100);
	}

}
