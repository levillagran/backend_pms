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

import ec.org.pms.models.Archivo;
import ec.org.pms.models.Municipio;
import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.repositories.ArchivoRepository;
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
	private ArchivoRepository archivoRepository;
	
	@GetMapping(value = "/municipios/add")
	public List<MunicipiosAddResponse> listAdd() {
		return municipioService.findMunicipiosAdd();
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/municipios/save")
	public List<MunicipiosAddResponse> saveMunicipio(@RequestBody MunicipioAdherirRequest municipio) {
	
		try {
			Municipio muni = municipioRepository.findById(municipio.getId()).get();
			muni.setEstado(municipio.getEstado());
			muni.setDocumento(municipio.getArchivo());
			muni.setObservaciones(municipio.getObservaciones());
			muni.setAnio(municipio.getFechaAdd().getYear()+1900);
			muni.setAnioFin(municipio.getFechaAdd().getYear()+1900);
			muni.setMes(municipio.getFechaAdd().getMonth()+1);
			muni.setMesFin(municipio.getFechaAdd().getMonth()+1);
			muni.setDia(municipio.getFechaAdd().getDate());
			muni.setDiaFin(municipio.getFechaAdd().getDate());
			municipioRepository.save(muni);
			Archivo arc = new Archivo();
			arc.setCantonId(muni.getId());
			arc.setArchivo(muni.getDocumento());
			arc.setTipo("pdf");
			arc.setTamanio(2000);
			archivoRepository.save(arc);
		} catch (Exception e) {
			System.out.println(e);
		}
		return municipioService.findMunicipiosAdd();
	}
	
	@GetMapping(value = "/municipios/comprobante/{id}")
	public MunicipiosAddResponse docMunicipio(@PathVariable Integer id) {
		Municipio mun = municipioRepository.findById(id).get();
		MunicipiosAddResponse  mar = new MunicipiosAddResponse();
		mar.setDocumento(mun.getDocumento());
		return mar;
	}

}
