package org.grpc_server.controller;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.grpc_server.exception.NotFoundEmployeeException;
import org.grpc_server.proto.EmployeeError;
import org.grpc_server.proto.EmployeeProfile;
import org.grpc_server.proto.EmployeeServiceGrpc;
import org.grpc_server.service.EmployeeService;
import org.grpc_server.service.GrpcEmployeeService;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.http.HttpStatus;

/**
 * @author Sergey Kastalski
 */
@Slf4j
@GRpcService
public class GrpcProfileController extends EmployeeServiceGrpc.EmployeeServiceImplBase {

	private static final Metadata.Key<String> HTTP_STATUS_KEY = Metadata.Key.of("httpStatus", Metadata.ASCII_STRING_MARSHALLER);

	private static final Metadata.Key<EmployeeError> EMPLOYEE_ERROR_KEY = ProtoUtils.keyForProto(EmployeeError.getDefaultInstance());

	private final EmployeeService<EmployeeProfile> grpcEmployeeService;

	public GrpcProfileController(final GrpcEmployeeService grpcEmployeeService) {
		this.grpcEmployeeService = grpcEmployeeService;
	}

	@Override
	public void getEmployee(final Int64Value request, final StreamObserver<EmployeeProfile> responseObserver) {
		try {
			final EmployeeProfile employee = grpcEmployeeService.getEmployee(request.getValue());
			responseObserver.onNext(employee);
			responseObserver.onCompleted();
		} catch (final NotFoundEmployeeException e) {
			returnError(e, Status.INVALID_ARGUMENT, HttpStatus.NOT_FOUND.value(), responseObserver);
		} catch (final Exception e) {
			returnError(e, Status.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseObserver);
		}
	}

	@Override
	public void addEmployee(final EmployeeProfile request, final StreamObserver<EmployeeProfile> responseObserver) {
		try {
			final EmployeeProfile employee = grpcEmployeeService.createEmployee(request);
			responseObserver.onNext(employee);
			responseObserver.onCompleted();
		} catch (final Exception e) {
			returnError(e, Status.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseObserver);
		}
	}

	@Override
	public void deleteEmployee(final Int64Value request, final StreamObserver<BoolValue> responseObserver) {
		try {
			responseObserver.onNext(BoolValue.of(grpcEmployeeService.deleteEmployee(request.getValue())));
			responseObserver.onCompleted();
		} catch (final Exception e) {
			returnError(e, Status.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseObserver);
		}
	}

	@Override
	public StreamObserver<EmployeeProfile> clientStream(final StreamObserver<Empty> responseObserver) {
		return new StreamObserver<>() {
			@Override
			public void onNext(final EmployeeProfile request) {
				log.info("[clientStream()] " + request.toString());
				final EmployeeProfile employee = grpcEmployeeService.createEmployee(request);
				log.info("After saving: " + employee);
			}

			@Override
			public void onError(final Throwable throwable) {
				log.error("[clientStream()] Smth went wrong", throwable);
				responseObserver.onError(throwable);
			}

			@Override
			public void onCompleted() {
				responseObserver.onNext(Empty.newBuilder().build());
				responseObserver.onCompleted();
			}
		};
	}

	@Override
	public void serverStream(final Empty request, final StreamObserver<EmployeeProfile> responseObserver) {
		try {
			grpcEmployeeService.getEmployees().forEach(responseObserver::onNext);
		} catch (final Exception e) {
			responseObserver.onError(e);
		}
		responseObserver.onCompleted();
	}

	@Override
	public StreamObserver<EmployeeProfile> biDirectionalStream(final StreamObserver<EmployeeProfile> responseObserver) {
		return new StreamObserver<>() {
			@Override
			public void onNext(final EmployeeProfile request) {
				final EmployeeProfile e1 = EmployeeProfile.newBuilder(request).setEmployeeName(request.getEmployeeName() + "-child1").build();
				responseObserver.onNext(grpcEmployeeService.createEmployee(e1));
				final EmployeeProfile e2 = EmployeeProfile.newBuilder(request).setEmployeeName(request.getEmployeeName() + "-child2").build();
				responseObserver.onNext(grpcEmployeeService.createEmployee(e2));
			}

			@Override
			public void onError(final Throwable throwable) {
				responseObserver.onError(throwable);
			}

			@Override
			public void onCompleted() {
				responseObserver.onCompleted();
			}
		};
	}


	private void returnError(final Throwable exception, final Status status, final int httpStatus, final StreamObserver<?> responseObserver) {
		final var metadata = new Metadata();
		metadata.put(HTTP_STATUS_KEY, String.valueOf(httpStatus));
		metadata.put(EMPLOYEE_ERROR_KEY, EmployeeError.newBuilder().setDescription(exception.getMessage() + "!!!").build());
		final StatusRuntimeException grpcException = status.withCause(exception).withDescription(exception.getMessage()).asRuntimeException(metadata);
		responseObserver.onError(grpcException);
	}

}
