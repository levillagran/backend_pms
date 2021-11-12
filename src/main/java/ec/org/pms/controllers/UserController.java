package ec.org.pms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.User;
import ec.org.pms.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping("/all")
	public List<User> list() {
		List<User> listU = new ArrayList<>();
		listU = (List<User>) userRepo.findAll();
		return listU;
	}

}
