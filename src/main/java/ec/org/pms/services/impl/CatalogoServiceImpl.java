package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.payload.response.UnidadeResponse;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.services.CatalogoService;

@Service("catalogoService")
public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private IndicadorRepository indicadorRepository;

	@Override
	public List<UnidadeResponse> findUnidadesAll(String cuantitativa) {
		List<UnidadeResponse> unidades = new ArrayList<>();
		UnidadeResponse unidad;
		List<String> tipos = indicadorRepository.findAllByGroupByTypeIsQuantitative(cuantitativa.equals("Si") ? true : false);
		for (String tipo : tipos) {
			unidad = new UnidadeResponse();
			unidad.setCode(tipo);
			unidad.setName(tipo);
			unidades.add(unidad);
		}
		return unidades;
	}

	

}
