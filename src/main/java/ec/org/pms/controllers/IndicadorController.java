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

import ec.org.pms.models.MediosVerificacion;
import ec.org.pms.payload.request.IndicadorRequest;
import ec.org.pms.payload.response.SemaforizacionResponse;
import ec.org.pms.payload.response.indicadorResponse.Root;
import ec.org.pms.services.IndicadorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class IndicadorController {
	@Autowired
	private IndicadorService indicadorService;

	@GetMapping(value = "/indicadores/canton/{id}")
	public List<Root> identificadoresCanton(@PathVariable String id) {
		return indicadorService.findIndicadores(Integer.parseInt(id));
	}

	@PostMapping(value = "/indicadores/save")
	public List<Root> saveIndicadores(@RequestBody IndicadorRequest indicadorSave) {
		return indicadorService.saveIndicador(indicadorSave);
	}
	
	@GetMapping(value = "/indicadores/semaforizacion/{id}")
	public List<SemaforizacionResponse> semaforizacion(@PathVariable String id) {
		return indicadorService.semaforizacion(Integer.parseInt(id));
	}	
	
	@GetMapping(value = "/indicadores/comprobante/{id}")
	public String comprobanteIndicador(@PathVariable Integer id) {
		return indicadorService.comprobanteIndicador(id);
	}
	
	@GetMapping(value = "/indicadores/certificable/{id}")
	public boolean esCertificable(@PathVariable String id) {
		return indicadorService.esCertificable(Integer.parseInt(id));
	}
	
	@GetMapping(value = "/fuentes/{id}")
	public List<String> fuentes(@PathVariable Integer id) {
		return indicadorService.findFuentes(id);
	}
	
	@GetMapping(value = "/medios/{id}")
	public List<MediosVerificacion> medios(@PathVariable Integer id) {
		return indicadorService.findMedios(id);
	}
	
	@GetMapping(value = "/valorIndicadorDelete/{id}")
	public List<Root> deleteIndicador(@PathVariable Integer id) {
		return indicadorService.deleteIndicador(id);
	}

}
