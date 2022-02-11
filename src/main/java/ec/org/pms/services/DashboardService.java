package ec.org.pms.services;

import java.util.List;

import ec.org.pms.payload.response.dashboardResponse.NumMuniAdd;
import ec.org.pms.payload.response.dashboardResponse.PorcentajesMuni;

public interface DashboardService {
	public NumMuniAdd findNumMuniAdd();
	public List<PorcentajesMuni> findPorcentajes();
}
