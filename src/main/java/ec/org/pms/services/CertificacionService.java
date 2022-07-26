package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.request.IndicadorRequest;
import ec.org.pms.payload.response.SemaforizacionResponse;
import ec.org.pms.payload.response.componenteResponse.Componente;
import ec.org.pms.payload.response.componenteResponse.HijoComponente;
import ec.org.pms.payload.response.ejeResponse.DatosBarra;
import ec.org.pms.payload.response.indicadorResponse.Root;

public interface CertificacionService {
	public List<Root> findIndicadores(Integer cantonId);
	public List<Root> saveIndicador(IndicadorRequest indicadorSave);
	public List<SemaforizacionResponse> semaforizacion(Integer cantonId);
	public DatosBarra ejes(Integer cantonId);
	public List<Componente> findComponentes();
	public List<HijoComponente> findComponenteDetalle(Integer componenteId);
	public String comprobanteIndicador(Integer valorIndicadorId);
	public String createCertificado(Integer cantonId);
}
