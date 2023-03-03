package ec.org.pms.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ec.org.pms.models.UserZona;

@RepositoryRestResource(path="users_roles")
public interface UserZonaRepository extends PagingAndSortingRepository<UserZona, Integer>{
	UserZona findByPersonId(Integer personId);
	UserZona findFirstByPersonId(Integer personId);
	UserZona findFirstByOrderByIdDesc();
	UserZona findByZonaId(Integer zonaId);
}
