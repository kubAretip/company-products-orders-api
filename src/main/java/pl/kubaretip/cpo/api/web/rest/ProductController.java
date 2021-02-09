package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.service.ProductService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.validation.groups.Pk;
import pl.kubaretip.cpo.api.validation.groups.Update;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ExceptionUtils exceptionUtils;

    public ProductController(ProductService productService,
                             ExceptionUtils exceptionUtils) {
        this.productService = productService;
        this.exceptionUtils = exceptionUtils;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Validated({Default.class, Pk.class}) @RequestBody ProductDTO productDTO,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        var product = productService.createProduct(productDTO);
        var locationUri = uriComponentsBuilder.path("/products/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(locationUri).body(product);
    }

    @PatchMapping(path = "/{id}", params = {"remove"})
    public ResponseEntity<Void> markProductAsDeleted(@PathVariable("id") long productId,
                                                     @RequestParam boolean remove) {
        productService.markProductAsDeleted(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> modifyProducts(@PathVariable("id") long productId,
                                                     @Validated({Default.class, Pk.class, Update.class}) @RequestBody ProductDTO productDTO) {

        if (productId != productDTO.getId())
            throw exceptionUtils.pathIdNotEqualsBodyId();

        return ResponseEntity.ok(productService.modifyProduct(productDTO));
    }


}
