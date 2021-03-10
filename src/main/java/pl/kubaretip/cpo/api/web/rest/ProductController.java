package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.dto.mapper.ProductMapper;
import pl.kubaretip.cpo.api.service.ProductService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.EditProductRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewProductRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody NewProductRequest request,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        var product = productService.createProduct(productMapper.mapNewProductRequestToProductDTO(request));
        var locationUri = uriComponentsBuilder.path("/products/{id}")
                .buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(locationUri).body(productMapper.mapToDTO(product));
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> markProductAsDeleted(@PathVariable("id") long productId) {
        productService.markProductAsDeleted(productId);
        return ResponseEntity.accepted().build();
    }

    @Secured(AuthoritiesConstants.Code.USER)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productMapper.mapToListDTO(productService.getAllProducts()));
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> modifyProducts(@PathVariable("id") long productId,
                                                     @Valid @RequestBody EditProductRequest request) {
        if (productId != request.getId()) {
            throw ExceptionUtils.invalidRequestId();
        }
        var productDTO = productMapper.mapEditProductRequestToProductDTO(request);
        var product = productService.modifyProduct(productDTO);
        return ResponseEntity.ok(productMapper.mapToDTO(product));
    }


}
