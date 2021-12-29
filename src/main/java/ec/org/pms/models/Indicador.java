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
@Table(name = "ind_tbl_main")
public class Indicador {
	
	@Id
	@Column(name = "i_main_id")
	private Integer id;
	@Column(name = "i_main_cde")
	private Integer code;
	@Column(name = "s_main_nme")
	private String name;
	@Column(name = "b_main_qnt")
	private boolean quantitative;
	@Column(name = "s_main_unt")
	private String type;
	@Column(name = "r_spr")
	private String inicial;
	@Column(name = "s_rng")
	private String satisfactorio;
	@Column(name = "r_inf")
	private String optimo;
	@Column(name = "s_main_v0")
	private String limite1;
	@Column(name = "s_main_v1")
	private String limite2;
	@Column(name = "s_main_v2")
	private String limite3;
	@Column(name = "s_main_v3")
	private String limite4;
	@Column(name = "s_main_dsc")
	private String description;
	@Column(name = "b_main_obl")
	private boolean obliged;

	@Column(name = "i_lvl_cde")
	private Integer ejeId;
	
}