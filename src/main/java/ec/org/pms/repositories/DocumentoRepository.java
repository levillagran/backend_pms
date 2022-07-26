package ec.org.pms.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Documento;

public interface DocumentoRepository extends PagingAndSortingRepository<Documento, Integer>{
	Documento findFirstByOrderByIdDesc();
}
