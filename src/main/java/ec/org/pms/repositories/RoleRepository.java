package ec.org.pms.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ec.org.pms.models.Role;

@RepositoryRestResource(path="roles")
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>{
	Role findByName(String username);
}
