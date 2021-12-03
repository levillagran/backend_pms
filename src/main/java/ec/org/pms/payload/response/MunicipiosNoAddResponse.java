package ec.org.pms.payload.response;

import lombok.Data;

@Data
public class MunicipiosNoAddResponse {
	
	private Integer cantonId;
	private String canton;
	private Integer provinciaId;
	private String provincia;

}
