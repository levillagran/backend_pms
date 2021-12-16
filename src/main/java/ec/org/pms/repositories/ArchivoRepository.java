package ec.org.pms.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Archivo;

public interface ArchivoRepository extends PagingAndSortingRepository<Archivo, Integer>{
}
