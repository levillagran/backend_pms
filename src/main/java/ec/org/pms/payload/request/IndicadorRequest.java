package ec.org.pms.payload.request;

import lombok.Data;

@Data
public class IndicadorRequest {

	private Integer indicadorId;
	private String code;
	private Integer userId;
	private String value;

}
