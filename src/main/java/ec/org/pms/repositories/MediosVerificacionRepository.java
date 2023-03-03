package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.MediosVerificacion;

public interface MediosVerificacionRepository extends PagingAndSortingRepository<MediosVerificacion, Integer>{
	List<MediosVerificacion> findByCodeOrderByIdAsc(Integer code);
}
