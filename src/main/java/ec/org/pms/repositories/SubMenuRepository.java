package ec.org.pms.repositories;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.SubMenu;

public interface SubMenuRepository extends PagingAndSortingRepository<SubMenu, Integer>{
	List<SubMenu> findAllByMenuIdAndActive(Integer menuId, boolean active);
}
