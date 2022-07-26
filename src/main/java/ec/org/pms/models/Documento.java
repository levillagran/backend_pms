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
@Table(name = "dta_tbl_doc")
public class Documento {
	
	@Id
	@Column(name = "i_doc_id")
	private Integer id;
	@Column(name = "i_doc_typ")
	private Integer typeId;
	@Column(name = "s_doc_doc")
	private String documento;
}