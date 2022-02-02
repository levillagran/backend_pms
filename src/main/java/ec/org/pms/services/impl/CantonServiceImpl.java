package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.models.UserRole;
import ec.org.pms.payload.response.MunicipiosNoAddResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.services.CantonService;

@Service("cantonService")
public class CantonServiceImpl implements CantonService {

	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

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
		List<MunicipiosNoAddResponse> minicipiosSinUsuario = new ArrayList<>();
		MunicipiosNoAddResponse minicipioSinUsuario;
		List<Municipio> municipios = (List<Municipio>) municipioRepository.findAllByEstado(901);
		List<UserRole> userRoles = (List<UserRole>) userRoleRepository.findAll();
			
		for (Municipio municipio : municipios) {
			boolean bandera = false;
			for (UserRole userRole : userRoles) {
				if (municipio.getId().equals(userRole.getEntId()) ) {
					bandera = true;
				}
			}
			if(!bandera) {
				minicipioSinUsuario = new MunicipiosNoAddResponse();
				minicipioSinUsuario.setCantonId(municipio.getId());
				minicipioSinUsuario.setCanton(municipio.getCanton());
				minicipioSinUsuario.setProvinciaId(municipio.getProvinciaId());
				Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
				minicipioSinUsuario.setProvincia(prov.getProvincia());
				minicipiosSinUsuario.add(minicipioSinUsuario);
			}
		}

		/*
		 * List<UsuariosResponse> listU = new ArrayList<>(); listU =
		 * usuarioService.findUsersAdd(); System.out.println(listU.size());
		 * List<Municipio> municipios = (List<Municipio>) municipioRepository.findAll();
		 * List<MunicipiosNoAddResponse> munProvMove = new ArrayList<>();
		 * 
		 * for (Municipio municipio : municipios) { if (municipio.getProvinciaId() > 0)
		 * { Provincia prov =
		 * provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
		 * MunicipiosNoAddResponse munAdd = new MunicipiosNoAddResponse();
		 * munAdd.setCantonId(municipio.getId());
		 * munAdd.setCanton(municipio.getCanton());
		 * munAdd.setProvinciaId(municipio.getProvinciaId());
		 * munAdd.setProvincia(prov.getProvincia()); munProv.add(munAdd); } }
		 * 
		 * for (UsuariosResponse user : listU) { for (MunicipiosNoAddResponse munPro :
		 * munProv) { if (user.getCantonId().equals(munPro.getCantonId())) {
		 * munProvMove.add(munPro); } } }
		 * 
		 * for (MunicipiosNoAddResponse mP : munProvMove) { munProv.remove(mP); }
		 */
		return minicipiosSinUsuario;
	}

}
