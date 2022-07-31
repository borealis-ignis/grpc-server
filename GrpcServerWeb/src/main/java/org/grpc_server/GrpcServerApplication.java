package org.grpc_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sergey Kastalski
 */
@SpringBootApplication
public class GrpcServerApplication {

	public static void main(final String[] args) {
		SpringApplication.run(GrpcServerApplication.class, args);
	}

}
