package org.grpc_server.service.adapter;

import org.grpc_server.model.dto.EmployeeDto;
import org.grpc_server.repository.EmployeeRepository;
import org.grpc_server.service.AbstractEmployeeService;
import org.grpc_server.service.converter.DtoEmployeeConverter;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Kastalski
 */
@Service
public class DtoEmployeeServiceAdapter extends AbstractEmployeeService<EmployeeDto> {

	public DtoEmployeeServiceAdapter(final EmployeeRepository employeeRepository, final DtoEmployeeConverter converter) {
		super(employeeRepository, converter);
	}

}
