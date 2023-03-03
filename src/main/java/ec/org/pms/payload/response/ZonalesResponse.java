package ec.org.pms.payload.response;

import java.util.Date;

import lombok.Data;

@Data
public class ZonalesResponse {
	
	private Integer id;
	private String estado;
	private Integer rolId;
	private String rol;
	private Integer zonalId;
	private String zonal;
	private String usuario;
	private String nombre;
	private String apellido;
	private String clave;
	private String correo;
	private String fecha;
	private Date fechaUS;

}
