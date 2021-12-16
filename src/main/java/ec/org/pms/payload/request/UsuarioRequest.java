package ec.org.pms.payload.request;

import java.util.Date;

import lombok.Data;

@Data
public class UsuarioRequest {

	private Integer id;
	private boolean estado;
	private Integer rolId;
	private Integer cantonId;
	private String usuario;
	private String nombre;
	private String apellido;
	private String clave;
	private String correo;
	private Date fecha;

}
