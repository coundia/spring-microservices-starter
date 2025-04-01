package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.exception;

	import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.ProductId;

public class ProductNotFoundException extends RuntimeException {

public ProductNotFoundException(ProductId id) {
super("Product with ID " + id + " not found");
}
}
