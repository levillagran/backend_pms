package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.payload.response.MunicipiosNoAddResponse;
import ec.org.pms.payload.response.UsuariosResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.services.CantonService;
import ec.org.pms.services.UsuarioService;

@Service("cantonService")
public class CantonServiceImpl implements CantonService {

	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private UsuarioService usuarioService;

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

	@Override
	public List<MunicipiosNoAddResponse> findCantonesSinUsuario() {
		List<UsuariosResponse> listU = new ArrayList<>();
		listU = usuarioService.findUsersAdd();
		List<Municipio> municipios = (List<Municipio>) municipioRepository.findAll();
		List<MunicipiosNoAddResponse> munProv = new ArrayList<>();
		List<MunicipiosNoAddResponse> munProvMove = new ArrayList<>();
		
		for (Municipio municipio : municipios) {
			if (municipio.getProvinciaId() > 0) {
				Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
				MunicipiosNoAddResponse munAdd = new MunicipiosNoAddResponse();
				munAdd.setCantonId(municipio.getId());
				munAdd.setCanton(municipio.getCanton());
				munAdd.setProvinciaId(municipio.getProvinciaId());
				munAdd.setProvincia(prov.getProvincia());
				munProv.add(munAdd);
			}
		}
		
		for (UsuariosResponse user : listU) {
			for (MunicipiosNoAddResponse munPro : munProv) {
				if (user.getCantonId().equals(munPro.getCantonId())) {
					munProvMove.add(munPro);
				}
			}
		}
		
		for (MunicipiosNoAddResponse mP : munProvMove) {
			munProv.remove(mP);
		}
		return munProv;
	}

}
