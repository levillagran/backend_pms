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
@Table(name = "web_tbl_mnu")
public class Menu {
	
	@Id
	@Column(name = "i_mnu_id")
	private Integer id;
	@Column(name = "i_rol_id")
	private Integer roleId;
	@Column(name = "s_mnu_nme")
	private String name;
	@Column(name = "i_mnu_pst")
	private String position;
	@Column(name = "b_mnu_stt")
	private boolean active;
}
