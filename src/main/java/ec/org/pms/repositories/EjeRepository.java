package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Eje;

public interface EjeRepository extends PagingAndSortingRepository<Eje, Integer>{
	List<Eje> findByIdLessThan(Integer valor);
	List<Eje> findByIdGreaterThan(Integer valor);
}
