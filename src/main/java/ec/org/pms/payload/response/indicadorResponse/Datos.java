package ec.org.pms.payload.response.indicadorResponse;

import lombok.Data;

@Data
public class Datos {

	private Integer ejeId;
	private Integer indicadorId;
	private Integer code;
	private String name;
	private Double value;
	private String type;
	private String description;
	private boolean obligatory;
	private Integer userId;
	private String option1;
	private String option2;
	private String option3;
}
