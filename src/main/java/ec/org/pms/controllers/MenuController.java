package ec.org.pms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Menu;
import ec.org.pms.models.Role;
import ec.org.pms.models.SubMenu;
import ec.org.pms.repositories.MenuRepository;
import ec.org.pms.repositories.RoleRepository;
import ec.org.pms.repositories.SubMenuRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MenuController {
	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private SubMenuRepository subMenuRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	@PostMapping(value = "/menu")
	public List<Menu> listMenu(@RequestBody Role role) {
		//System.out.println(role);
		List<Menu> listMenu = new ArrayList<>();
		boolean active = true;
		listMenu = (List<Menu>) menuRepo.findAllByRoleIdAndActive(roleRepo.findByName(role.getName()).getId(), active);
		return listMenu;
	}
	
	@PostMapping(value = "/subMenu")
	public List<SubMenu> listSubMenu(@RequestBody List<SubMenu> menuIds) {
		//System.out.println(menuIds);
		List<SubMenu> listSubMenu = new ArrayList<>();
		boolean active = true;
		for (SubMenu menuId : menuIds) {
			List<SubMenu> aux = new ArrayList<>();
			aux = subMenuRepo.findAllByMenuIdAndActive(menuId.getMenuId(), active);
			if(aux != null){
				for(SubMenu au : aux) {
					listSubMenu.add(au);
				}
			}
		}
		return listSubMenu;
	}

}
