package ec.org.pms.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Indicador;

public interface IndicadorRepository extends PagingAndSortingRepository<Indicador, Integer>{
	Indicador findBycode(Integer code);
}
