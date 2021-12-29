package ec.org.pms.payload.response;

import lombok.Data;

@Data
public class SemaforizacionResponse {
	
	private String eje;
	private String determinante;
	private String indicador;
	private String semaforizacion;

}
