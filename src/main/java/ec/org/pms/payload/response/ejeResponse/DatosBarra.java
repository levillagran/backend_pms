package ec.org.pms.payload.response.ejeResponse;

import java.util.List;

import lombok.Data;

@Data
public class DatosBarra {

	private String[] labels;
	private List<DatosEje> datasets;
}
