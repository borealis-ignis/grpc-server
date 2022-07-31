package org.grpc_server.model.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Sergey Kastalski
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeDbo {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String name;

}
