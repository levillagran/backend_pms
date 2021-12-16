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
@Table(name = "dpa_tbl_cnt_img")
public class Archivo {
	
	@Id
	@Column(name = "i_img_id")
	private Integer id;
	@Column(name = "i_cnt_cde")
	private Integer cantonId;
	@Column(name = "b_img_img")
	private String archivo;
	@Column(name = "s_img_type")
	private String tipo;
	@Column(name = "d_img_sze")
	private Integer tamaño;
}