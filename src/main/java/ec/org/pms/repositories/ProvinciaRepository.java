package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Provincia;

public interface ProvinciaRepository extends PagingAndSortingRepository<Provincia, Integer>{
	Provincia findByCodigoAndBandera(Integer provinciaId, Integer bandera);
	List<Provincia> findAllByBandera(Integer bandera);
}
