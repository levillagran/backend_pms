package ec.org.pms.payload.response.indicadorResponse;

import lombok.Data;

@Data
public class Datos {

	private Integer ejeId;
	private Integer indicadorId;
	private Integer code;
	private String name;
	private Integer valueIndicadorId;
	private String value;
	private String type;
	private String description;
	private boolean obligatory;
	private boolean desnutrition;
	private boolean maternity;
	private boolean violence;
	private Integer userId;
	private String option1;
	private String option2;
	private String option3;
	private String date;
	private String dateRegister;
	private String font;
	private String obs;
	private Boolean archivo;
}
