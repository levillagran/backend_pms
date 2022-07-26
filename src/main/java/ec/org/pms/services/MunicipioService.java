package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;

public interface MunicipioService {
	public List<MunicipiosAddResponse> findMunicipiosAdd();
	public List<MunicipiosAddResponse> saveMunicipio(MunicipioAdherirRequest municipio);
	public List<MunicipiosAddResponse> findMunicipiosEva();
}
