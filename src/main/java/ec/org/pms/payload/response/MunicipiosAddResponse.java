package ec.org.pms.payload.response;

import lombok.Data;

@Data
public class MunicipiosAddResponse {
	
	private String estado;
	private Integer cantonId;
	private String canton;
	private Integer provinciaId;
	private String provincia;
	private String documento;
	private String fecha;
	private String fechaUS;
	private String observaciones;

}
