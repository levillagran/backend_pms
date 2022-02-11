/**
 * 
 */
package ec.org.pms.models;

/**
 * @author episig := Lenin Villagran
 *
 */
import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pms_tbl_mnc")
public class ValorIndicador {
	
	@Id
	@Column(name = "i_mnc_id")
	private Integer id;
	@Column(name = "i_cnt_cde")
	private Integer cantonId;
	@Column(name = "i_lv3_id")
	private Integer ejeId;
	@Column(name = "r_crt_nmb")
	private Double valor;
	@Column(name = "i_crt_yr")
	private Integer anio;
	@Column(name = "i_crt_mth")
	private Integer mes;
	@Column(name = "i_crt_day")
	private Integer dia;
	@Column(name = "s_crt_src")
	private String fuente;
	@Column(name = "s_crt_obs")
	private String observacion;
	@Column(name = "i_prs_rol_id")
	private Integer personaId;
	@Column(name = "d_mnc_dte_rgs")
	private Date fecha;
	@Column(name = "s_mnc_tme_rgs")
	private Date hora;
	@Column(name = "i_stt_id")
	private Integer estado;
	@Column(name = "s_mnc_fle")
	private String archivo;
	
}