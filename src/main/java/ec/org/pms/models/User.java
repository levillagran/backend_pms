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
@Table(name = "dta_tbl_prs")
public class User {
	
	@Id
	@Column(name = "i_prs_id")
	private Integer id;
	@Column(name = "d_prs_dte_rgs")
	private Date creationDate;
	@Column(name = "s_prs_tme_rgs")
	private String creationTime;
	@Column(name = "s_prs_lst_nme")
	private String surname;
	@Column(name = "s_prs_nme")
	private String name;
	@Column(name = "s_prs_usr")
	private String username;
	@Column(name = "s_prs_eml")
	private String mail;
	@Column(name = "s_prs_psw")
	private String passwod;
}
