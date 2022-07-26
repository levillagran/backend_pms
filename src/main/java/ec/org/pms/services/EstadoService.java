package ec.org.pms.services;

import java.util.List;

import ec.org.pms.models.Estado;

public interface EstadoService {
	public List<Estado> findAllByTipo();
	public String esCertificable(Integer estadoId);
	public boolean evaluar(Integer estadoId);
}
