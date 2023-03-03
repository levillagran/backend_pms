package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Indicador;

public interface IndicadorRepository extends PagingAndSortingRepository<Indicador, Integer>{
	List<Indicador> findAllByOrderByCodeAsc();
	Indicador findBycode(Integer code);
	List<Indicador> findByEjeId(Integer code);
	int countByIdGreaterThan(Integer valor);
	@Query("SELECT i.type FROM Indicador AS i WHERE i.quantitative = ?1 GROUP BY i.type ORDER BY i.type ASC")
	List<String> findAllByGroupByTypeIsQuantitative(boolean cuantitativa);
	Indicador findFirstByEjeIdOrderByCodeDesc(Integer valor);
	Indicador findFirstByOrderByIdDesc();
}
