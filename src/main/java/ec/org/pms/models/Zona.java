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
@Table(name = "dpa_tbl_znl")
public class Zona {
	
	@Id
	@Column(name = "i_znl_cde")
	private Integer id;
	@Column(name = "s_znl_nme")
	private String zona;
	@Column(name = "s_znl_obs")
	private String observaciones;
	@Column(name = "i_stt_cde")
	private Integer estado;
	
}