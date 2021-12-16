package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Estado;
import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.repositories.EstadoRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.services.MunicipioService;

@Service("municipioService")
public class MunicipioServiceImpl implements MunicipioService {
	
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	public List<MunicipiosAddResponse> findMunicipiosAdd() {
		List<Municipio> municipios = new ArrayList<>();
		List<MunicipiosAddResponse> municipiosAdd = new ArrayList<>();
		municipios = municipioRepository.findAllByEstado(901);
		for (Municipio municipio : municipios) {
			Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
			Estado estado = estadoRepository.findByIdAndTipo(municipio.getEstado(), 900);
			MunicipiosAddResponse munAdd = new MunicipiosAddResponse();
			munAdd.setCantonId(municipio.getId());
			munAdd.setCanton(municipio.getCanton());
			munAdd.setProvinciaId(municipio.getProvinciaId());
			munAdd.setProvincia(prov.getProvincia());
			munAdd.setDocumento("");
			munAdd.setFecha(municipio.getDia() + "-" + municipio.getMes() + "-" + municipio.getAnio());
			munAdd.setFechaUS(municipio.getMes() + "-" + municipio.getDia() + "-" + municipio.getAnio());
			munAdd.setObservaciones(municipio.getObservaciones());
			munAdd.setEstado(estado.getEstado());
			municipiosAdd.add(munAdd);
		}
		return municipiosAdd;
	}

}
