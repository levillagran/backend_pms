package ec.org.pms.payload.response.componenteResponse;

import java.util.List;

import lombok.Data;

@Data
public class HijoComponente {
	
	private String label;
	private String type;
	private String className;
	private boolean expanded;
	private Componente data;
	private List<HijoComponente> children;

}
