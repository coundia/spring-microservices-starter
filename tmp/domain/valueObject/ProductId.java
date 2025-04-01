package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject;


import java.io.Serializable;

public class ProductId implements Serializable {

private final UUID id;

public ProductId(UUID id) {
this.id = id;
}

public static ProductId create(UUID id) {
return new ProductId(id);
}

public UUID value() {
return this.id;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof ProductId that)) return false;
return this.id.equals(that.id);
}

@Override
public int hashCode() {
return id.hashCode();
}

@Override
public String toString() {
return String.valueOf(id);
}
}
