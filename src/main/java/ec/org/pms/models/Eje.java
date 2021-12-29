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
@Table(name = "ind_tbl_lvl")
public class Eje {
	
	@Id
	@Column(name = "i_lvl_cde")
	private Integer id;
	@Column(name = "s_lvl_nme")
	private String name;
	@Column(name = "i_lvl_fth")
	private Integer parent;
	@Column(name = "s_lvl_pth")
	private String path;
}