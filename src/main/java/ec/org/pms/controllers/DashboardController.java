package ec.org.pms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.payload.response.SemaforoMunicipiosResponse;
import ec.org.pms.payload.response.componenteResponse.Componente;
import ec.org.pms.payload.response.componenteResponse.HijoComponente;
import ec.org.pms.payload.response.dashboardResponse.NumMuniAdd;
import ec.org.pms.payload.response.dashboardResponse.PorcentajesMuni;
import ec.org.pms.payload.response.ejeResponse.DatosBarra;
import ec.org.pms.services.DashboardService;
import ec.org.pms.services.IndicadorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	@Autowired
	private IndicadorService indicadorService;
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping(value = "/ejes/{id}")
	public DatosBarra ejes(@PathVariable String id) {
		return indicadorService.ejes(Integer.parseInt(id));
	}
	
	@GetMapping(value = "/componentes")
	public List<Componente> componentesList() {
		return indicadorService.findComponentes();
	}
	
	@GetMapping(value = "/componente/{id}")
	public List<HijoComponente> componenteDetail(@PathVariable String id) {
		return indicadorService.findComponenteDetalle(Integer.parseInt(id));
	}
	
	@GetMapping(value = "/numMuniAdheridos")
	public NumMuniAdd numMuniAdheridos() {
		return dashboardService.findNumMuniAdd();
	}
	
	@GetMapping(value = "/porcentajes")
	public List<PorcentajesMuni> Porcentajes() {
		return dashboardService.findPorcentajes();
	}
	
	@GetMapping(value = "/municipios")
	public List<SemaforoMunicipiosResponse> listAdd() {
		return dashboardService.findSemaforoMunicipios();
	}

}
