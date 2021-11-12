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
@Table(name = "web_tbl_mnu_sub")
public class SubMenu {
	
	@Id
	@Column(name = "i_mnu_sub_id")
	private Integer id;
	@Column(name = "i_mnu_id")
	private Integer menuId;
	@Column(name = "s_mnu_sub_nme")
	private String subName;
	@Column(name = "s_mnu_sub_icn")
	private String icon;
	@Column(name = "s_mnu_sub_lnk")
	private String link;
	@Column(name = "b_mnu_sub_stt")
	private boolean active;
}
