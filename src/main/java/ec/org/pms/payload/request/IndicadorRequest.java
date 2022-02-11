package ec.org.pms.payload.request;

import java.util.Date;

import lombok.Data;

@Data
public class IndicadorRequest {

	private Integer indicadorId;
	private String code;
	private Integer userId;
	private Integer ValueIndicadorId;
	private String value;
	private Date date;
	private Date dateRegister;
	private String font;
	private String obs;
	private String  archivo;

}
