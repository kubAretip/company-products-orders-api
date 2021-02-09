package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Product;
import pl.kubaretip.cpo.api.dto.ProductDTO;
import pl.kubaretip.cpo.api.dto.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final Translator translator;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              UnitService unitService,
                              ProductMapper productMapper,
                              Translator translator) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.productMapper = productMapper;
        this.translator = translator;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.mapToListDTO(productRepository.findAll());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        if (productRepository.existsByName(productDTO.getName()))
            throw productWithNameAlreadyExists(productDTO.getName());

        var category = categoryService.getCategoryById(productDTO.getCategory().getId());
        var unit = unitService.getUnitById(productDTO.getUnit().getId());

        productDTO.setId(null);
        productDTO.setCategory(category);
        productDTO.setUnit(unit);
        var product = productMapper.mapToEntity(productDTO);
        productRepository.save(product);
        return productMapper.mapToDTO(product);
    }

    @Override
    public void markProductAsDeleted(long productId) {
        var product = findProductById(productId);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductDTO modifyProduct(ProductDTO productDTO) {

        if (productRepository.existsById(productDTO.getId())) {
            productRepository.findByName(productDTO.getName())
                    .ifPresent(existingProduct -> {
                        if (!existingProduct.getId().equals(productDTO.getId()))
                            throw productWithNameAlreadyExists(productDTO.getName());
                    });

            var category = categoryService.getCategoryById(productDTO.getCategory().getId());
            var unit = unitService.getUnitById(productDTO.getUnit().getId());
            productDTO.setUnit(unit);
            productDTO.setCategory(category);
            var product = productMapper.mapToEntity(productDTO);
            productRepository.save(product);
            return productMapper.mapToDTO(product);
        } else {
            throw productWithIdNotFound(productDTO.getId());
        }
    }

    Product findProductById(long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> productWithIdNotFound(productId));
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
