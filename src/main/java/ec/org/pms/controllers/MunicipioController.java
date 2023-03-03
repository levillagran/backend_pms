package ec.org.pms.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import ec.org.pms.models.UserRole;
import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.repositories.DocumentoRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.UserRoleRepository;
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
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@GetMapping(value = "/municipios/add")
	public List<MunicipiosAddResponse> listAdd() {
		return municipioService.findMunicipiosAdd();
	}
	
	@GetMapping(value = "/municipios/municipio/{id}")
	public MunicipioAdherirRequest findMunicipio(@PathVariable Integer id) {
		return municipioService.findMunicipio(id);
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
	
	@PostMapping(value = "/municipios/link/save")
	public List<MunicipiosAddResponse> saveMunicipioLink(@RequestBody MunicipioAdherirRequest municipio) {
		return municipioService.saveMunicipioLink(municipio);
	}
	
	@PostMapping(value = "/municipios/solicitud/save")
	public List<MunicipiosAddResponse> saveMunicipioRequest(@RequestBody MunicipioAdherirRequest municipio) {
		return municipioService.saveMunicipioRequest(municipio);
	}

	@GetMapping(value = "/municipios/solicitud/{id}")
	public MunicipiosAddResponse soliMunicipio(@PathVariable Integer id) {
		UserRole user = userRoleRepository.findById(id).get();
		Integer cantonId = user.getEntId();
		Municipio mun = municipioRepository.findById(cantonId).get();
		Documento doc = docRepository.findById(mun.getEvaluationId()).get();
		MunicipiosAddResponse  mar = new MunicipiosAddResponse();
		mar.setDocumento(doc.getDocumento());
		return mar;
	}

	@GetMapping(value = "/municipios/solicitudPlantilla")
	public String solicitudPlantilla() {
		Path path = Paths.get("src/main/resources/reports/Anexo 1_Modelo de Solicitud de Evaluación Externa.docx");
	    String anexo = "";
		try {
			anexo = Files.readString(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return anexo;
	}
	
	@GetMapping(value = "/municipios/link/{id}")
	public MunicipioAdherirRequest findLink(@PathVariable Integer id) {
		return municipioService.findLink(id);
	}
}
