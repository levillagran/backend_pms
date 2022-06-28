package ec.org.pms.services;

import java.util.List;

import ec.org.pms.models.Eje;
import ec.org.pms.models.Indicador;
import ec.org.pms.payload.response.componenteResponse.Componente;

public interface GestionService {
	public List<Componente> findAllComponentes();
	public List<Componente> saveComponente(Componente componente);
	public Componente findComponente(Integer id);
	public List<Eje> findAllEjes(Integer id);
	public List<Eje> saveEje(Eje eje);
	public Eje findEje(Integer id);
	public List<Indicador> findAllIndicadores(Integer id);
	public List<Indicador> saveIndicador(Eje eje);
	public Indicador findIndicador(Integer id);
}
