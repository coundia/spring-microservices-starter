package .Users.pcoundia.projects.spring-microservices-starter.tmp.infrastructure.repository;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.Product;
import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.ProductId;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, ProductId> {
}
