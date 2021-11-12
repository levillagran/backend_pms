package ec.org.pms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Menu;
import ec.org.pms.models.Role;
import ec.org.pms.models.SubMenu;
import ec.org.pms.repositories.MenuRepository;
import ec.org.pms.repositories.RoleRepository;
import ec.org.pms.repositories.SubMenuRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class MenuController {
	@Autowired
	private MenuRepository menuRepo;
	@Autowired
	private SubMenuRepository subMenuRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	@RequestMapping(value = "/menu")
	public List<Menu> listMenu(@RequestBody Role rol) {
		System.out.println(rol);
		List<Menu> listMenu = new ArrayList<>();
		boolean active = true;
		listMenu = (List<Menu>) menuRepo.findAllByRoleIdAndActive(roleRepo.findByName(rol.getName()).getId(), active);
		return listMenu;
	}
	
	@RequestMapping(value = "/subMenu")
	public List<SubMenu> listSubMenu(@RequestBody List<SubMenu> menuIds) {
		List<SubMenu> listSubMenu = new ArrayList<>();
		boolean active = true;
		for (SubMenu menuId : menuIds) {
			listSubMenu.add(subMenuRepo.findByMenuIdAndActive(menuId.getMenuId(), active));
		}
		return listSubMenu;
	}

}
