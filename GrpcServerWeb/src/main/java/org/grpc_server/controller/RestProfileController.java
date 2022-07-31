package org.grpc_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.grpc_server.exception.NotFoundEmployeeException;
import org.grpc_server.model.dto.EmployeeDto;
import org.grpc_server.service.EmployeeService;
import org.grpc_server.service.RestEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@RestController
public class RestProfileController {

	final EmployeeService<EmployeeDto> restEmployeeService;

	public RestProfileController(final RestEmployeeService restEmployeeService) {
		this.restEmployeeService = restEmployeeService;
	}

	@GetMapping(path = "employee/{id}")
	public EmployeeDto getEmployee(@PathVariable final long id) {
		return restEmployeeService.getEmployee(id);
	}

	@PostMapping(path = "employee")
	public EmployeeDto createEmployee(@RequestBody final EmployeeDto employeeDto) {
		return restEmployeeService.createEmployee(employeeDto);
	}

	@DeleteMapping(path = "employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable final long id) {
		if (restEmployeeService.deleteEmployee(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@ExceptionHandler
	public ResponseEntity<?> handle(final Throwable e) {
		log.error("Operation with Employee is failed: " + e.getMessage());
		final HttpStatus status = ((e instanceof NotFoundEmployeeException))? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(status).build();
	}

}
