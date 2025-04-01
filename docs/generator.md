cd vo

mvn clean install -DskipTests
 
java -jar ../cqrs-generator/target/cqrs-generator-0.0.1-SNAPSHOT.jar \
--entityName=com.pcoundia.products.infrastructure.entity.Product \
--sourceRoot=src/main/java \
--outputDir=src/main/java/com/pcoundia/products/generated

### http
curl -N -X POST http://localhost:8080/api/v1/generator/all \
-H "Content-Type: application/json" \
-H "Accept: text/event-stream" \
-d '{
"outputDir": "/Users/coundia/dev/generated-files",
"entity": {
"name": "Product",
"fields": [
{ "name": "id", "type": "UUID" },
{ "name": "name", "type": "String" },
{ "name": "price", "type": "BigDecimal" }
]
}
}
'
###