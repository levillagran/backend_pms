package ec.org.pms.payload.request;

import java.util.Date;

import lombok.Data;

@Data
public class MunicipioAdherirRequest {

	private Integer id;
	private String canton;
	private Date fechaAdd;
	private Integer estado;
	private String observaciones;
	private String  archivo;

}
