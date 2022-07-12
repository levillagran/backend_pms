package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.response.UnidadeResponse;

public interface CatalogoService {
	public List<UnidadeResponse> findUnidadesAll(String cuantitativa);
}
