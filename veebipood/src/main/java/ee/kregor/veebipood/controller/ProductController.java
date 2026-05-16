package ee.kregor.veebipood.controller;

import ee.kregor.veebipood.entity.Product;
import ee.kregor.veebipood.repository.ProductRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // turvaviga, päris arendustes seda ei teeks
// @CrossOrigin(origins = "http://localhost:5173").
// @CrossOrigin(origins = "https://www.arvutitark.ee").
@RestController
public class ProductController {
    // localhost:8080/products
    // application.properties  server.port=8090
//    @GetMapping("products")
//    public String helloworld(){
//        return "Hello World";
//    }

    // 1xx -> (harva) informatiivne
    // 2xx -> õnnestuv
    // 3xx -> (harva) redirect
    // 4xx -> päringu tegija (client error / front-end error)
    // 5xx -> päringu vastuvõtja viga (server error)

    @Autowired
    private ProductRepository productRepository;

    // localhost:8080/products?page=0&size=4&sort=price,asc
    @GetMapping("products")
    public Page<@NonNull Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @GetMapping("products/admin")
    public List<Product> getAdminProducts(){
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getOneProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id); // kustutan
        return productRepository.findAll(); // uuenenud seis
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if (product.getId()!=null){
            throw new RuntimeException("Cannot add with ID");
        }
        productRepository.save(product); // siin salvestab
        return productRepository.findAll(); // siin on uuenenud seis
    }

    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        // File -> Settings -> Plugins -> Lombok -> Install
        if (product.getId()==null){
            throw new RuntimeException("Cannot edit without ID");
        }
        if (!productRepository.existsById(product.getId())){
            throw new RuntimeException("Product ID does not exist");
        }
        productRepository.save(product); // siin salvestab
        return productRepository.findAll(); // siin on uuenenud seis
    }


}