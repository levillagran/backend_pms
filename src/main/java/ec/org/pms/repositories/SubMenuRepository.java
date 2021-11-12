package ec.org.pms.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;

import ec.org.pms.models.SubMenu;

public interface SubMenuRepository extends PagingAndSortingRepository<SubMenu, Integer>{
	SubMenu findByMenuIdAndActive(Integer menuId, boolean active);
}
