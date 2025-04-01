package .Users.pcoundia.projects.spring-microservices-starter.tmp.application.queryHandler;

	import .Users.pcoundia.projects.spring-microservices-starter.tmp.application.query.*;
	import .Users.pcoundia.projects.spring-microservices-starter.tmp.application.dto.ProductResponse;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ProductQueryHandler {

@QueryHandler
public ProductResponse handle(GetProductByIdQuery query) {
return null;
}

@QueryHandler
public List<ProductResponse> handle(GetAllProductQuery query) {
	return List.of();
	}
	}
