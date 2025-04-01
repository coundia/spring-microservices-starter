package .Users.pcoundia.projects.spring-microservices-starter.tmp.domain.valueObject;


import java.io.Serializable;

public class ProductName implements Serializable {

private final String name;

public ProductName(String name) {
this.name = name;
}

public static ProductName create(String name) {
return new ProductName(name);
}

public String value() {
return this.name;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof ProductName that)) return false;
return this.name.equals(that.name);
}

@Override
public int hashCode() {
return name.hashCode();
}

@Override
public String toString() {
return String.valueOf(name);
}
}
