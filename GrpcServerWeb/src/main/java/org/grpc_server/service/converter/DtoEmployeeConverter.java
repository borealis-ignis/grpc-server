package org.grpc_server.service.converter;

import org.grpc_server.model.dbo.EmployeeDbo;
import org.grpc_server.model.dto.EmployeeDto;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kastalski
 */
@Component
public class DtoEmployeeConverter implements EmployeeConverter<EmployeeDto, EmployeeDbo> {

	@Override
	public EmployeeDbo convertDto(final EmployeeDto dto) {
		return new EmployeeDbo(dto.getEmployeeId(), dto.getEmployeeName());
	}

	@Override
	public EmployeeDto convertDbo(final EmployeeDbo dbo) {
		return new EmployeeDto(dbo.getId(), dbo.getName());
	}

}
