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
@Table(name = "pms_tbl_opt")
public class Estado {
	
	@Id
	@Column(name = "i_opt_id")
	private Integer id;
	@Column(name = "i_opt_vrb")
	private Integer tipo;
	@Column(name = "s_opt_nme")
	private String estado;
	@Column(name = "s_opt_dsc")
	private String descripcion;
}