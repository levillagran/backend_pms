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
@Table(name = "means_verification")
public class MediosVerificacion {
	
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "identifier_code")
	private Integer code;
	@Column(name = "name")
	private String name;
}