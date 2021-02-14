package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO createProduct(ProductDTO productDTO);

    void markProductAsDeleted(long productId);

    ProductDTO modifyProduct(ProductDTO productDTO);

    Product findProductById(long productId);

    boolean existsProductsWithIds(List<Long> productsIds);

    List<Product> findProductsByIds(List<Long> productsIds);
}
