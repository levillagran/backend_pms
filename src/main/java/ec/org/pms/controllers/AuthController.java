package ec.org.pms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.Role;
import ec.org.pms.models.UserRole;
import ec.org.pms.models.UserZona;
import ec.org.pms.models.Zona;
import ec.org.pms.payload.request.LoginRequest;
import ec.org.pms.payload.response.JwtResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.RoleRepository;
import ec.org.pms.repositories.UserRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.UserZonaRepository;
import ec.org.pms.repositories.ZonaRepository;
import ec.org.pms.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository usersRepository;
	@Autowired
	private UserRoleRepository usersRolesRepository;
	@Autowired
	private RoleRepository rolesRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private UserZonaRepository uzrepo;
	@Autowired
	private ZonaRepository zrepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());
			
		ec.org.pms.models.User user = usersRepository.findByUsername(loginRequest.getUsername()).get();
		List<UserRole> userRol = usersRolesRepository.findByPersonId(user.getId());
		List<String> roles = new ArrayList<>();
		for (UserRole userol : userRol) {
			Role rol = rolesRepository.findById(userol.getRolId()).get();
			roles.add(rol.getName());
		}
		Municipio usuario = new Municipio();
		UserRole usRolId = usersRolesRepository.findFirstByPersonId(user.getId());
		if (usRolId.getEntId() != null) {
			usuario = municipioRepository.findById(usRolId.getEntId()).get();
		}else {
			UserZona uz = uzrepo.findByPersonId(usRolId.getPersonId());
			Zona z = zrepo.findById(uz.getZonaId()).get();
			usuario.setId(z.getId());
			usuario.setCanton(user.getUsername());
		}
		
		return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), usuario.getId(), usuario.getCanton(), user.getUsername(), roles));
	}

}
