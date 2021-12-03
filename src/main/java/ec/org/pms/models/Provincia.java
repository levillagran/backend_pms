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
@Table(name = "dpa_tbl_opt")
public class Provincia {
	
	@Id
	@Column(name = "i_opt_id")
	private Integer id;
	@Column(name = "i_opt_cde")
	private Integer codigo;
	@Column(name = "s_opt_nme")
	private String provincia;
	@Column(name = "i_opt_tpe")
	private Integer bandera;
}