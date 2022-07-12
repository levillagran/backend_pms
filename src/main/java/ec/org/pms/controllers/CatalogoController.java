package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.payload.response.UnidadeResponse;
import ec.org.pms.services.CatalogoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {
	@Autowired
	private CatalogoService catalogoService;
	
	@GetMapping(value = "/unidades/{cuantitativa}")
	public List<UnidadeResponse> listUnidadesAll(@PathVariable String cuantitativa) {
		return catalogoService.findUnidadesAll(cuantitativa);
	}
}
