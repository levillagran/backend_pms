package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.UserRole;
import ec.org.pms.payload.response.MunicipiosAddResponse;
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
		List<Municipio> munis = (List<Municipio>) municipioRepository.findAll();
		NumMuniAdd numMuniAdd = new NumMuniAdd();
		int i = 0;
		for (Municipio municipio : munis) {
			if (municipio.getDocumento().length() > 1) {
				i = i + 1;
			}
		}
		List<UserRole> users = (List<UserRole>) userRoleRepository.findAll();
		int j = 0;
		for (UserRole user : users) {
			if (user.getEntId() > 0) {
				j = j + 1;
			}
		}
		int numComp = ejeRepository.countByIdLessThan(10);
		int numEjes = ejeRepository.countByIdGreaterThan(10);
		int numIndi = indicadorRepository.countByIdGreaterThan(0);
		
		numMuniAdd.setNumMuniAdd(i);
		numMuniAdd.setNumUsuarios(j);
		numMuniAdd.setNumComponentes(numComp);
		numMuniAdd.setNumEjes(numEjes);
		numMuniAdd.setNumIndicadores(numIndi);
		return numMuniAdd;
	}
	
	@Override
	public List<PorcentajesMuni> findPorcentajes() {
		List<MunicipiosAddResponse> muniAdds =  municipioService.findMunicipiosAdd();
		List<PorcentajesMuni> porcenMuni = new ArrayList<>();
		PorcentajesMuni porcentajesMuni;
		for (MunicipiosAddResponse muniAdd : muniAdds) {
			porcentajesMuni = new PorcentajesMuni();
			porcentajesMuni.setName(muniAdd.getCanton());
			porcentajesMuni.setId(String.valueOf(muniAdd.getCantonId()));
			porcentajesMuni.setStatus("Promotor");
			porcentajesMuni.setPorcentaje(String.valueOf(Math.round(valorIndicadorRepository.countByCantonId(muniAdd.getCantonId())*(100/66) * 100) / 100d));
			porcenMuni.add(porcentajesMuni);
		}
		return porcenMuni;
	}

}
