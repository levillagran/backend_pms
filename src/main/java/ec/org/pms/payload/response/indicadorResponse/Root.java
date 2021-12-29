package ec.org.pms.payload.response.indicadorResponse;

import java.util.List;

import lombok.Data;

@Data
public class Root {
	
	private String key;
	private Datos data;
	private List<Padre> children;

}
