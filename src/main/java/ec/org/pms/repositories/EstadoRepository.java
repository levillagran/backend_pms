package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Estado;

public interface EstadoRepository extends PagingAndSortingRepository<Estado, Integer>{
	Estado findByIdAndTipo(Integer estadoId, Integer tipo);
	List<Estado> findAllByTipo(Integer tipo);
}
