/**
 * 
 */
package ec.org.pms.models;

/**
 * @author episig := Lenin Villagran
 *
 */
import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "scr_tbl_prs_rol")
public class UserRole {
	
	@Id
	@Column(name = "i_prs_rol_id")
	private Integer id;
	@Column(name = "d_prs_rol_dte_rgs")
	private Date registerDate;
	@Column(name = "s_prs_rol_tme_rgs")
	private String registerTime;
	@Column(name = "i_rol_id")
	private Integer rolId;
	@Column(name = "i_prs_id")
	private Integer personId;
	@Column(name = "i_stt_id")
	private Integer sttId;
	@Column(name = "i_ent_pms")
	private Integer entId;
	
}
