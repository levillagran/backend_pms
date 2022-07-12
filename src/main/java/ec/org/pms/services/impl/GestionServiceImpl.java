package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Eje;
import ec.org.pms.models.Indicador;
import ec.org.pms.payload.response.componenteResponse.Componente;
import ec.org.pms.repositories.EjeRepository;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.services.GestionService;

@Service("gestionService")
public class GestionServiceImpl implements GestionService {

	@Autowired
	private EjeRepository ejeRepository;

	@Autowired
	private IndicadorRepository indicadorRepository;

	@Override
	public List<Componente> findAllComponentes() {
		List<Componente> comps = new ArrayList<>();
		List<Eje> ejes = (List<Eje>) ejeRepository.findAllByParent(-99);
		Componente comp;
		for (Eje eje : ejes) {
			comp = new Componente();
			comp.setId(String.valueOf(eje.getId()));
			comp.setName(eje.getName());
			comps.add(comp);
		}
		return comps;
	}

	@Override
	public List<Componente> saveComponente(Componente componente) {
		Eje eje = new Eje();
		if (componente.getId() != null) {
			eje = ejeRepository.findById(Integer.parseInt(componente.getId())).get();
			eje.setName(componente.getName());
			ejeRepository.save(eje);
		} else {
			Eje ejeAux = ejeRepository.findFirstByParentOrderByIdDesc(-99);
			eje.setId(ejeAux.getId() + 1);
			eje.setName(componente.getName());
			eje.setParent(-99);
			eje.setPath(ejeAux.getPath());
			ejeRepository.save(eje);
		}
		return findAllComponentes();
	}

	@Override
	public Componente findComponente(Integer id) {
		Eje eje = ejeRepository.findById(id).get();
		Componente comp = new Componente();
		comp.setId(String.valueOf(eje.getId()));
		comp.setName(eje.getName());
		return comp;
	}

	@Override
	public List<Eje> findAllEjes(Integer id) {
		return (List<Eje>) ejeRepository.findAllByParent(id);
	}

	@Override
	public List<Eje> saveEje(Eje eje) {
		if (eje.getId() != null) {
			ejeRepository.save(eje);
		} else {
			Eje ej = ejeRepository.findFirstByParentOrderByIdDesc(eje.getParent());
			eje.setId(ej.getId() + 1);
			ejeRepository.save(eje);
		}
		return findAllEjes(eje.getParent());
	}

	@Override
	public Eje findEje(Integer id) {
		return ejeRepository.findById(id).get();
	}

	@Override
	public List<Indicador> findAllIndicadores(Integer id) {
		return indicadorRepository.findByEjeId(id);
	}

	@Override
	public List<Indicador> saveIndicador(Indicador indicador) {
		if (indicador.getCode() != null) {
			indicadorRepository.save(indicador);
		} else {
			Indicador ind = indicadorRepository.findFirstByEjeIdOrderByCodeDesc(indicador.getEjeId());
			indicador.setCode(ind.getCode() + 1);
			Indicador indi = indicadorRepository.findFirstByOrderByIdDesc();
			indicador.setId(indi.getId() + 1);
			indicadorRepository.save(indicador);
		}
		return findAllIndicadores(indicador.getEjeId());
	}

	@Override
	public Indicador findIndicador(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
