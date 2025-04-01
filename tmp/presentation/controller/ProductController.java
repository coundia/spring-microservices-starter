package .Users.pcoundia.projects.spring-microservices-starter.tmp.presentation.controller;


@RestController
@RequestMapping("/products")
public class ProductController {

@PostMapping
public void create(@RequestBody ProductDto dto) {
// call command handler or application service
}

@GetMapping
public List<ProductDto> getAll() {
// call query handler
return List.of();
}

@GetMapping("/{id}")
public ProductDto getById(@PathVariable UUID id) {
// call query handler
return null;
}

@PutMapping("/{id}")
public void update(@PathVariable UUID id, @RequestBody ProductDto dto) {
// call command handler or application service
}

@DeleteMapping("/{id}")
public void delete(@PathVariable UUID id) {
// call command handler or application service
}
}
