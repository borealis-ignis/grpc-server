package org.grpc_server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sergey Kastalski
 */
@Getter
@AllArgsConstructor
public class EmployeeDto {

	private final long employeeId;

	private final String employeeName;

}
