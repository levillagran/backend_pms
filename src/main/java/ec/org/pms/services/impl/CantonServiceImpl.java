package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.payload.response.MunicipiosNoAddResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.services.CantonService;

@Service("cantonService")
public class CantonServiceImpl implements CantonService {
	
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Override
	public List<MunicipiosNoAddResponse> findCantones() {
		List<Municipio> municipios = new ArrayList<>();
		List<MunicipiosNoAddResponse> municipiosAdd = new ArrayList<>();
		municipios = municipioRepository.findAllByEstado(902);
		for (Municipio municipio : municipios) {
			Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
			MunicipiosNoAddResponse munAdd = new MunicipiosNoAddResponse();
			munAdd.setCantonId(municipio.getId());
			munAdd.setCanton(municipio.getCanton());
			munAdd.setProvinciaId(municipio.getProvinciaId());
			munAdd.setProvincia(prov.getProvincia());
			municipiosAdd.add(munAdd);
		}
		return municipiosAdd;
	}

}
