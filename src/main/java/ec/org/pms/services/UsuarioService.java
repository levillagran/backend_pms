package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.request.UsuarioRequest;
import ec.org.pms.payload.response.UsuariosResponse;

public interface UsuarioService {
	public List<UsuariosResponse> findUsersAdd();
	public List<UsuariosResponse> save(UsuarioRequest usuario);
}
