package ec.org.pms.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.Menu;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Integer>{
	List<Menu> findAllByRoleIdAndActive(Integer rol, boolean active);
}
