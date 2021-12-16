package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ec.org.pms.models.UserRole;

@RepositoryRestResource(path="users_roles")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Integer>{
	List<UserRole> findByPersonId(Integer personId);
	UserRole findFirstByPersonId(Integer personId);
	UserRole findFirstByOrderByIdDesc();
}
