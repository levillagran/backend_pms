package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Zona;

public interface ZonaRepository extends PagingAndSortingRepository<Zona, Integer>{
	List<Zona> findAllByEstado(Integer estadoId);
}
