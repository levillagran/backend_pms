package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import ec.org.pms.models.UserZona;
import ec.org.pms.models.Zona;
import ec.org.pms.payload.request.UsuarioRequest;
import ec.org.pms.payload.response.UsuariosResponse;
import ec.org.pms.payload.response.ZonalesResponse;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.repositories.RoleRepository;
import ec.org.pms.repositories.UserRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.UserZonaRepository;
import ec.org.pms.repositories.ZonaRepository;
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
	@Autowired
	private UserZonaRepository uzRepo;
	@Autowired
	private ZonaRepository zRepo;

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
					Calendar cal = Calendar.getInstance();
					cal.setTime(user.getCreationDate());
					userRes.setFecha(cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.YEAR));
					userRes.setFechaUS(user.getCreationDate());
					if (userRol.getSttId().equals(901)) {
						userRes.setEstado("activado");
					} else {
						userRes.setEstado("desactivado");
					}
					for (Role rol : roles) {
						if (rol.getId().equals(userRol.getRolId())) {
							userRes.setRolId(rol.getId());
							userRes.setRol(rol.getName());
						}
					}
					for (Municipio canton : cantones) {
						if (userRol.getEntId() != null) {
							if (userRol.getEntId().equals(canton.getId())) {
								userRes.setCantonId(canton.getId());
								userRes.setCanton(canton.getCanton());
								for (Provincia provincia : provincias) {
									if (canton.getProvinciaId().equals(provincia.getCodigo())) {
										userRes.setProvinciaId(provincia.getCodigo());
										userRes.setProvincia(provincia.getProvincia());
									}
								}
							}
						}

					}
					if (userRol.getEntId() != null) {
						usuarios.add(userRes);
					}
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

			if (usuario.getId() != null) {
				user.setId(usuario.getId());
				bandera = true;
			} else {
				user.setId(userRepository.findFirstByOrderByIdDesc().getId() + 1);
				bandera = false;
			}
			user.setCreationDate(usuario.getFecha());
			Calendar cal = Calendar.getInstance();
			cal.setTime(usuario.getFecha());
			user.setCreationTime(
					cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
			user.setUsername(usuario.getUsuario());
			user.setName(usuario.getNombre());
			user.setSurname(usuario.getApellido());
			user.setMail(usuario.getCorreo());
			if (usuario.getClave().isBlank()) {
				user.setPasswod(userRepository.findById(usuario.getId()).getPasswod());
			} else {
				user.setPasswod(passwordEncoder.encode(usuario.getClave()));
			}
			user = userRepository.save(user);
			if (bandera) {
				usRol.setId(userRoleRepository.findFirstByPersonId(user.getId()).getId());
			} else {
				usRol.setId(userRoleRepository.findFirstByOrderByIdDesc().getId() + 1);
			}
			usRol.setPersonId(user.getId());
			usRol.setEntId(usuario.getCantonId());
			usRol.setRolId(usuario.getRolId());
			if (usuario.isEstado()) {
				usRol.setSttId(901);
			} else {
				usRol.setSttId(902);
			}
			usRol.setRegisterDate(user.getCreationDate());
			usRol.setRegisterTime(user.getCreationTime());
			usRol = userRoleRepository.save(usRol);
			listU = findUsersAdd();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listU;
	}

	@Override
	public List<ZonalesResponse> findZonales() {
		List<ZonalesResponse> usuarios = new ArrayList<>();

		List<UserRole> usuariosRol = (List<UserRole>) userRoleRepository.findByRolId(102);
		for (UserRole userRol : usuariosRol) {
			User user = userRepository.findById(userRol.getPersonId());
			UserZona uz = uzRepo.findByPersonId(user.getId());
			Zona z = zRepo.findById(uz.getZonaId()).get();
			ZonalesResponse userRes = new ZonalesResponse();
			userRes.setZonalId(uz.getZonaId());
			userRes.setZonal(z.getZona());
			userRes.setId(user.getId());
			userRes.setUsuario(user.getUsername());
			userRes.setNombre(user.getName());
			userRes.setApellido(user.getSurname());
			userRes.setCorreo(user.getMail());
			Calendar cal = Calendar.getInstance();
			cal.setTime(user.getCreationDate());
			userRes.setFecha(cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.YEAR));
			userRes.setFechaUS(user.getCreationDate());
			if (userRol.getSttId().equals(901)) {
				userRes.setEstado("activado");
			} else {
				userRes.setEstado("desactivado");
			}
			userRes.setRolId(102);
			userRes.setRol("Zonal");
			usuarios.add(userRes);
		}

		return usuarios;
	}

	@Override
	public List<ZonalesResponse> saveUsuarioZonal(UsuarioRequest usuario) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		List<ZonalesResponse> listU = new ArrayList<>();
		User user = new User();
		UserRole usRol = new UserRole();
		UserZona uz = new UserZona();
		boolean bandera;
		try {

			if (usuario.getId() != null) {
				user.setId(usuario.getId());
				bandera = true;
			} else {
				user.setId(userRepository.findFirstByOrderByIdDesc().getId() + 1);
				bandera = false;
			}
			user.setCreationDate(usuario.getFecha());
			Calendar cal = Calendar.getInstance();
			cal.setTime(usuario.getFecha());
			user.setCreationTime(
					cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
			user.setUsername(usuario.getUsuario());
			user.setName(usuario.getNombre());
			user.setSurname(usuario.getApellido());
			user.setMail(usuario.getCorreo());
			if (usuario.getClave().isBlank()) {
				user.setPasswod(userRepository.findById(usuario.getId()).getPasswod());
			} else {

				user.setPasswod(passwordEncoder.encode(usuario.getClave()));
			}
			user = userRepository.save(user);
			if (bandera) {
				usRol.setId(userRoleRepository.findFirstByPersonId(user.getId()).getId());
				uz.setId(uzRepo.findFirstByPersonId(user.getId()).getId());
			} else {
				usRol.setId(userRoleRepository.findFirstByOrderByIdDesc().getId() + 1);
				UserZona uzAux = uzRepo.findFirstByOrderByIdDesc();
				uz.setId(uzAux != null ? uzAux.getId() + 1 : 1);
			}
			usRol.setPersonId(user.getId());
			uz.setPersonId(user.getId());
			// zana
			// usRol.setEntId(usuario.getCantonId());
			usRol.setRolId(usuario.getRolId());
			if (usuario.isEstado()) {
				usRol.setSttId(901);
			} else {
				usRol.setSttId(902);
			}
			usRol.setRegisterDate(user.getCreationDate());
			usRol.setRegisterTime(user.getCreationTime());
			usRol = userRoleRepository.save(usRol);

			uz.setZonaId(usuario.getCantonId());
			uz = uzRepo.save(uz);

			listU = findZonales();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listU;
	}

}
