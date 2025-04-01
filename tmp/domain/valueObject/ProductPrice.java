package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject;


import java.io.Serializable;

public class ProductPrice implements Serializable {

private final BigDecimal price;

public ProductPrice(BigDecimal price) {
this.price = price;
}

public static ProductPrice create(BigDecimal price) {
return new ProductPrice(price);
}

public BigDecimal value() {
return this.price;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof ProductPrice that)) return false;
return this.price.equals(that.price);
}

@Override
public int hashCode() {
return price.hashCode();
}

@Override
public String toString() {
return String.valueOf(price);
}
}
