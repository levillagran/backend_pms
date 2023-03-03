package ec.org.pms.payload.response;

import lombok.Data;

@Data
public class SemaforizacionResponse {
	
	private String codigo;
	private String eje;
	private String determinante;
	private String indicador;
	private Double valor;
	private String tipo;
	private String semaforizacion;
	private Integer valorIndicadorId;
	private boolean isVoucher;
	private Double valorEvaluacion;

}
