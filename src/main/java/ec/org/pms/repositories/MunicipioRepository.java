package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Municipio;

public interface MunicipioRepository extends PagingAndSortingRepository<Municipio, Integer>{
	List<Municipio> findAllByEstado(Integer estadoId);
}
