package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Indicador;
import ec.org.pms.models.Municipio;
import ec.org.pms.models.UserRole;
import ec.org.pms.models.ValorIndicador;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.payload.response.SemaforoMunicipiosResponse;
import ec.org.pms.payload.response.dashboardResponse.NumMuniAdd;
import ec.org.pms.payload.response.dashboardResponse.PorcentajesMuni;
import ec.org.pms.repositories.EjeRepository;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.ValorIndicadorRepository;
import ec.org.pms.services.DashboardService;
import ec.org.pms.services.MunicipioService;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private EjeRepository ejeRepository;
	@Autowired
	private IndicadorRepository indicadorRepository;
	@Autowired
	private ValorIndicadorRepository valorIndicadorRepository;
	@Autowired
	private MunicipioService municipioService;

	@Override
	public NumMuniAdd findNumMuniAdd() {
		List<Municipio> munis = (List<Municipio>) municipioRepository.findAllByEstado(901);
		NumMuniAdd numMuniAdd = new NumMuniAdd();

		List<UserRole> users = (List<UserRole>) userRoleRepository.findAll();
		int j = 0;
		for (UserRole user : users) {
			if (user.getEntId() != null && user.getEntId() > 0) {
				j = j + 1;
			}
		}
		int numComp = ejeRepository.countByIdLessThan(10);
		int numEjes = ejeRepository.countByIdGreaterThan(10);
		int numIndi = indicadorRepository.countByIdGreaterThan(0);

		numMuniAdd.setNumMuniAdd(munis.size());
		numMuniAdd.setNumMuniNoAdd((munis.size()-221) * (-1));
		numMuniAdd.setNumMuniSaludable(0);
		numMuniAdd.setNumMuniGarante(1);
		numMuniAdd.setNumMuniPromotor(2);
		numMuniAdd.setNumUsuarios(j);
		numMuniAdd.setNumComponentes(numComp);
		numMuniAdd.setNumEjes(numEjes);
		numMuniAdd.setNumIndicadores(numIndi);
		return numMuniAdd;
	}

	@Override
	public List<PorcentajesMuni> findPorcentajes() {
		List<MunicipiosAddResponse> muniAdds = municipioService.findMunicipiosAdd();
		List<PorcentajesMuni> porcenMuni = new ArrayList<>();
		PorcentajesMuni porcentajesMuni;
		for (MunicipiosAddResponse muniAdd : muniAdds) {
			porcentajesMuni = new PorcentajesMuni();
			porcentajesMuni.setProvince(muniAdd.getProvincia());
			porcentajesMuni.setName(muniAdd.getCanton());
			porcentajesMuni.setId(String.valueOf(muniAdd.getCantonId()));
			porcentajesMuni.setStatus("Promotor");
			porcentajesMuni.setPorcentaje(String.valueOf(
					Math.round(valorIndicadorRepository.countByCantonId(muniAdd.getCantonId()) * (100 / 66) * 100)
							/ 100d));
			porcenMuni.add(porcentajesMuni);
		}
		return porcenMuni;
	}

	@Override
	public List<SemaforoMunicipiosResponse> findSemaforoMunicipios() {
		List<SemaforoMunicipiosResponse> semMuniList = new ArrayList<>();
		SemaforoMunicipiosResponse semMuni;
		List<Municipio> municipios = (List<Municipio>) municipioRepository.findAll();
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		int numIndicadores = indicadorRepository.countByIdGreaterThan(0);
		for (Municipio municipio : municipios) {
			int i = 0;
			List<ValorIndicador> valores = valorIndicadorRepository.findByCantonId(municipio.getId());
			for (ValorIndicador valor : valores) {
				for (Indicador indicador : indicadores) {
					if (valor.getEjeId().equals(indicador.getId())) {
						if (indicador.getType().equals("Opciones") || indicador.getType().equals("Tipo")
								|| indicador.getType().equals("Diferencia de Tasas")
								|| indicador.getType().equals("Variación Anual")
								|| indicador.getType().equals("Cantidad")) {
							if (valor.getValor().equals(3.0)) {
								i = i + 1;
							}
						} else {
							if (Double.parseDouble(indicador.getLimite3()) < Double.parseDouble(indicador.getLimite4())) {
								if (valor.getValor() > Double.parseDouble(indicador.getLimite3())
										&& valor.getValor() < Double.parseDouble(indicador.getLimite4())) {
									i = i + 1;
								}
							}else {
								if (valor.getValor() < Double.parseDouble(indicador.getLimite3())
										&& valor.getValor() > Double.parseDouble(indicador.getLimite4())) {
									i = i + 1;
								}
							}
						}
					}
				}
			}
			if (i > 0) {
				i = (i / 100) * numIndicadores;
				if (i > 85 && i <= 100) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("SALUDABLE");
				} else if (i <= 85 && i > 60) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("GARANTE");
				} else if (i <= 60 && i > 35) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("PROMOTOR");
				} else {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("NOCERTIFICA");
				}
			} else {
				semMuni = new SemaforoMunicipiosResponse();
				semMuni.setCantonId(municipio.getId());
				semMuni.setSemaforo("NOCERTIFICA");
			}
			semMuniList.add(semMuni);
		}
		List<MunicipiosAddResponse> munAdds = municipioService.findMunicipiosAdd();
		for (SemaforoMunicipiosResponse semMunis : semMuniList) {
			boolean bandera = false;
			for (MunicipiosAddResponse munAdd : munAdds) {
				if(semMunis.getCantonId().equals(munAdd.getCantonId()))
					bandera = true;
			}
			semMunis.setAdherido(bandera);				
		}
		return semMuniList;
	}

}
