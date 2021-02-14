package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.ProductRepository;
import pl.kubaretip.cpo.api.service.CategoryService;
import pl.kubaretip.cpo.api.service.ProductService;
import pl.kubaretip.cpo.api.service.UnitService;
import pl.kubaretip.cpo.api.util.Translator;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final Translator translator;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              UnitService unitService,
                              Translator translator) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.translator = translator;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {

        if (productRepository.existsByName(productDTO.getName()))
            throw productWithNameAlreadyExists(productDTO.getName());

        var category = categoryService.getCategoryById(productDTO.getCategory().getId());
        var unit = unitService.getUnitById(productDTO.getUnit().getId());
        return productRepository.save(new Product(productDTO.getName(), category, unit));
    }

    @Override
    public void markProductAsDeleted(long productId) {
        var product = findProductById(productId);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Product modifyProduct(ProductDTO productDTO) {

        var product = productRepository.findByName(productDTO.getName())
                .map(existingProduct -> {
                    if (!existingProduct.getId().equals(productDTO.getId()))
                        throw productWithNameAlreadyExists(productDTO.getName());
                    return existingProduct;
                })
                .or(() -> productRepository.findById(productDTO.getId()))
                .orElseThrow(() -> productWithIdNotFound(productDTO.getId()));

        product.setName(productDTO.getName());

        if (!product.getCategory().getId().equals(productDTO.getCategory().getId())) {
            product.setCategory(categoryService.getCategoryById(productDTO.getCategory().getId()));
        }

        if (!product.getUnit().getId().equals(productDTO.getUnit().getId())) {
            product.setUnit(unitService.getUnitById(productDTO.getUnit().getId()));
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> productWithIdNotFound(productId));
    }

    @Override
    public boolean existsProductsWithIds(List<Long> productsIds) {
        return productRepository.existsByIdIn(productsIds);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productsIds) {
        return productRepository.findByIdIn(productsIds);
    }

    private AlreadyExistsException productWithNameAlreadyExists(String name) {
        return new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                translator.translate("product.alreadyExists.name.message", new Object[]{name}));
    }

    private NotFoundException productWithIdNotFound(long productId) {
        return new NotFoundException(translator.translate("common.notFound.title"),
                translator.translate("product.notFound.id.message", new Object[]{productId}));
    }

}
