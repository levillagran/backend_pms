package ec.org.pms.payload.response;

import lombok.Data;

@Data
public class SemaforoMunicipiosResponse {
	
	private Integer cantonId;
	private String semaforo;
	private boolean adherido;

}
