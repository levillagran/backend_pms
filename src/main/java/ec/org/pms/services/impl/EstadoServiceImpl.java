package ec.org.pms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Estado;
import ec.org.pms.models.Municipio;
import ec.org.pms.models.UserRole;
import ec.org.pms.repositories.EstadoRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.services.EstadoService;

@Service("estadoService")
public class EstadoServiceImpl implements EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Override
	public List<Estado> findAllByTipo() {
		return  estadoRepository.findAllByTipo(900);
	}

	@Override
	public String esCertificable(Integer personaId) {
		UserRole user = userRoleRepository.findById(personaId).get();
		Municipio muni = municipioRepository.findById(user.getEntId()).get();
		Estado est = estadoRepository.findById(muni.getEstadoCtf()).get();
		return est.getEstado();
	}

	@Override
	public boolean evaluar(Integer personaId) {
		UserRole user = userRoleRepository.findById(personaId).get();
		Municipio muni = municipioRepository.findById(user.getEntId()).get();
		try {
			muni.setEstadoCtf(201);
			municipioRepository.save(muni);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
