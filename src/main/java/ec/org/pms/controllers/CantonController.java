package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.payload.response.MunicipiosNoAddResponse;
import ec.org.pms.payload.response.ZonalesNoAddResponse;
import ec.org.pms.services.CantonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cantones")
public class CantonController {
	@Autowired
	private CantonService cantonService;
	
	@GetMapping(value = "all")
	public List<MunicipiosNoAddResponse> listAdd() {
		return cantonService.findCantones();
	}
	
	@GetMapping(value = "allWithoutUser")
	public List<MunicipiosNoAddResponse> listWithoutUser() {
		return cantonService.findCantonesSinUsuario();
	}
	
	@GetMapping(value = "zonales")
	public List<ZonalesNoAddResponse> findZonales() {
		return cantonService.findZonales();
	}

}
