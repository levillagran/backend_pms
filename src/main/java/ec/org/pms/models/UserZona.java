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
@Table(name = "dpa_tbl_znl_prs")
public class UserZona {
	
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "znl_id")
	private Integer zonaId;
	@Column(name = "prs_id")
	private Integer personId;
	
}
