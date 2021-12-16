package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.models.Role;
import ec.org.pms.models.User;
import ec.org.pms.models.UserRole;
import ec.org.pms.payload.request.UsuarioRequest;
import ec.org.pms.payload.response.UsuariosResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.repositories.RoleRepository;
import ec.org.pms.repositories.UserRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.services.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private MunicipioRepository cantonRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;

	@SuppressWarnings("deprecation")
	@Override
	public List<UsuariosResponse> findUsersAdd() {
		List<UsuariosResponse> usuarios = new ArrayList<>();
		List<Role> roles = (List<Role>) roleRepository.findAll();
		List<UserRole> usuariosRol = (List<UserRole>) userRoleRepository.findAll();
		List<User> users = userRepository.findAll();
		List<Municipio> cantones = (List<Municipio>) cantonRepository.findAll();
		List<Provincia> provincias = (List<Provincia>) provinciaRepository.findAllByBandera(100);
		for (UserRole userRol : usuariosRol) {
			for (User user : users) {
				if (user.getId().equals(userRol.getPersonId())) {
					UsuariosResponse userRes = new UsuariosResponse();
					userRes.setId(user.getId());
					userRes.setUsuario(user.getUsername());
					userRes.setNombre(user.getName());
					userRes.setApellido(user.getSurname());
					userRes.setCorreo(user.getMail());
					userRes.setFecha(user.getCreationDate().getDate() + "-" + (user.getCreationDate().getMonth() +1) + "-" + (user.getCreationDate().getYear() + 1900));
					userRes.setFechaUS(user.getCreationDate());
					if (userRol.getSttId().equals(901)) {
						userRes.setEstado("activado");
					} else {
						userRes.setEstado("desactivado");
					}
					for (Role rol : roles) {
						if(rol.getId().equals(userRol.getRolId())){
							userRes.setRolId(rol.getId());
							userRes.setRol(rol.getName());
						}
					}
					for (Municipio canton : cantones) {
						if(userRol.getEntId().equals(canton.getId())){
							userRes.setCantonId(canton.getId());
							userRes.setCanton(canton.getCanton());
							for (Provincia provincia : provincias) {
								if(canton.getProvinciaId().equals(provincia.getCodigo())){
									userRes.setProvinciaId(provincia.getCodigo());
									userRes.setProvincia(provincia.getProvincia());
								}
							}
						}
					}
					usuarios.add(userRes);
				}
			}
		}
		return usuarios;
	}

	@Override
	public List<UsuariosResponse> save(UsuarioRequest usuario) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<UsuariosResponse> listU = new ArrayList<>();
		User user = new User();
		UserRole usRol = new UserRole();
		boolean bandera;
		try {
			
			if(usuario.getId() != null) {
				user.setId(usuario.getId());
				bandera = true;
			}else {
				user.setId(userRepository.findFirstByOrderByIdDesc().getId()+1);
				bandera = false;
			}
			user.setCreationDate(usuario.getFecha());
			user.setCreationTime(usuario.getFecha().getHours() + ":" + usuario.getFecha().getMinutes() + ":" + usuario.getFecha().getSeconds());
			user.setUsername(usuario.getUsuario());
			user.setName(usuario.getNombre());
			user.setSurname(usuario.getApellido());
			user.setMail(usuario.getCorreo());
			user.setPasswod(passwordEncoder.encode(usuario.getClave()));
			user = userRepository.save(user);
			if(bandera) {
				usRol.setId(userRoleRepository.findFirstByPersonId(user.getId()).getId());
			}else {
				usRol.setId(userRoleRepository.findFirstByOrderByIdDesc().getId()+1);
			}
			usRol.setPersonId(user.getId());
			usRol.setEntId(usuario.getCantonId());
			usRol.setRolId(usuario.getRolId());
			if(usuario.isEstado()){
				usRol.setSttId(901);
			}
			else{
				usRol.setSttId(902);
			}
			usRol.setRegisterDate(user.getCreationDate());
			usRol.setRegisterTime(user.getCreationTime());
			usRol = userRoleRepository.save(usRol);
			listU = findUsersAdd();
			System.out.println(listU);
		} catch (Exception e) {
			System.out.println(e);
		}
		return listU;
	}

}
