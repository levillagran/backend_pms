package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.ValorIndicador;

public interface ValorIndicadorRepository extends PagingAndSortingRepository<ValorIndicador, Integer>{
	List<ValorIndicador> findByCantonId(Integer cantonId);
	ValorIndicador findByCantonIdAndEjeId(Integer cantonId, Integer ejeId);
	int countByCantonId(Integer cantonId);
	ValorIndicador findFirstByOrderByIdDesc();
	boolean existsByCantonIdAndAnio(Integer id, Integer anio);
}
