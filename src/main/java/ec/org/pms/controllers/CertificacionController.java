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

import ec.org.pms.payload.request.IndicadorRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.payload.response.SemaforizacionResponse;
import ec.org.pms.payload.response.indicadorResponse.Root;
import ec.org.pms.services.CertificacionService;
import ec.org.pms.services.MunicipioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/certificacion")
public class CertificacionController {
	@Autowired
	private CertificacionService cerService;
	@Autowired
	private MunicipioService municipioService;
	
	@GetMapping(value = "/evaluar")
	public List<MunicipiosAddResponse> listAdd() {
		return municipioService.findMunicipiosEva();
	}

	@GetMapping(value = "/canton/{id}")
	public List<Root> identificadoresCanton(@PathVariable String id) {
		return cerService.findIndicadores(Integer.parseInt(id));
	}

	@PostMapping(value = "/save")
	public List<Root> saveIndicadores(@RequestBody IndicadorRequest indicadorSave) {
		return cerService.saveIndicador(indicadorSave);
	}
	
	@GetMapping(value = "/indicadores/{id}")
	public List<SemaforizacionResponse> semaforizacion(@PathVariable String id) {
		return cerService.semaforizacion(Integer.parseInt(id));
	}	
	
	@GetMapping(value = "/comprobante/{id}")
	public String comprobanteIndicador(@PathVariable Integer id) {
		return cerService.comprobanteIndicador(id);
	}
	
	@GetMapping(value = "/certificado/{id}")
	public String createCertificado(@PathVariable Integer id) {
		return cerService.createCertificado(id);
	}

}
