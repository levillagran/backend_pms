package ec.org.pms.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MunicipioAdherirRequest {

	private Integer id;
	private String canton;
	private String fechaAdd;
	private Integer estado;
	private String observaciones;
	private MultipartFile  archivo;

}
