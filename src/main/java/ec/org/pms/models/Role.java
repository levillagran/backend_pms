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
@Table(name = "dta_tbl_rls")
public class Role {
	
	@Id
	@Column(name = "i_rol_id")
	private Integer id;
	@Column(name = "s_rol_nme")
	private String name;
	@Column(name = "s_rol_cde")
	private String code;
}
