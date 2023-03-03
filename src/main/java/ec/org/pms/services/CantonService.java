package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.response.MunicipiosNoAddResponse;
import ec.org.pms.payload.response.ZonalesNoAddResponse;

public interface CantonService {
	public List<MunicipiosNoAddResponse> findCantones();
	public List<MunicipiosNoAddResponse> findCantonesSinUsuario();
	public List<ZonalesNoAddResponse> findZonales();
}
