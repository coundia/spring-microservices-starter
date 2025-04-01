package .Users.pcoundia.projects.spring-microservices-starter.tmp.application.mapper;

import .Users.pcoundia.projects.spring-microservices-starter.tmp.application.dto.*;
import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject.*;
import .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.*;

public class ProductMapper {

public static ProductResponse toResponse(Product entity) {
return new ProductResponse(
            entity.id(),
            entity.name(),
            entity.price()
);
}

public static Product toEntity(ProductRequest request) {
return new Product(
            request.id(),
            request.name(),
            request.price()
);
}
}
