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

import ec.org.pms.models.Documento;
import ec.org.pms.models.Municipio;
import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.repositories.DocumentoRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.services.MunicipioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MunicipioController {
	@Autowired
	private MunicipioService municipioService;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private DocumentoRepository docRepository;
	
	@GetMapping(value = "/municipios/add")
	public List<MunicipiosAddResponse> listAdd() {
		return municipioService.findMunicipiosAdd();
	}
	
	@PostMapping(value = "/municipios/save")
	public List<MunicipiosAddResponse> saveMunicipio(@RequestBody MunicipioAdherirRequest municipio) {
		return municipioService.saveMunicipio(municipio);
	}
	
	@GetMapping(value = "/municipios/comprobante/{id}")
	public MunicipiosAddResponse docMunicipio(@PathVariable Integer id) {
		Municipio mun = municipioRepository.findById(id).get();
		Documento doc = docRepository.findById(mun.getDocumento()).get();
		MunicipiosAddResponse  mar = new MunicipiosAddResponse();
		mar.setDocumento(doc.getDocumento());
		return mar;
	}

}
