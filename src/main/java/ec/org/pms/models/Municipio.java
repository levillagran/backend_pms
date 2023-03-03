/**
 * 
 */
package ec.org.pms.models;

/**
 * @author episig := Lenin Villagran
 *
 */
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dpa_tbl_cnt")
public class Municipio {
	
	@Id
	@Column(name = "i_cnt_cde")
	private Integer id;
	@Column(name = "i_prv_cde")
	private Integer provinciaId;
	@Column(name = "s_cnt_nme")
	private String canton;
	@Column(name = "s_cnt_dcm")
	private Integer documento;
	@Column(name = "i_cnt_dte_str_prc_yr")
	private Integer anio;
	@Column(name = "i_cnt_dte_str_prc_mth")
	private Integer mes;
	@Column(name = "i_cnt_dte_str_prc_day")
	private Integer dia;
	@Column(name = "i_cnt_dte_end_prc_yr")
	private Integer anioFin;
	@Column(name = "i_cnt_dte_end_prc_mth")
	private Integer mesFin;
	@Column(name = "i_cnt_dte_end_prc_day")
	private Integer diaFin;
	@Column(name = "s_cnt_obs")
	private String observaciones;
	@Column(name = "i_stt_cde")
	private Integer estado;
	@Column(name = "i_stt_ctf_cde")
	private Integer estadoCtf;
	@Column(name = "drive_id")
	private Integer drive_id;
	@Column(name = "evaluation_request_id")
	private Integer evaluationId;
}