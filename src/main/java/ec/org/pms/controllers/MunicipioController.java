package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.services.MunicipioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MunicipioController {
	@Autowired
	private MunicipioService municipioService;
	
	@GetMapping(value = "/municipios/add")
	public List<MunicipiosAddResponse> listAdd() {
		return municipioService.findMunicipiosAdd();
	}
	
	@PostMapping(value = "/municipios/save")
	public List<MunicipiosAddResponse> saveMunicipio(@RequestBody MunicipioAdherirRequest municipio) {
		System.out.println(municipio);
		return municipioService.findMunicipiosAdd();
	}

}
