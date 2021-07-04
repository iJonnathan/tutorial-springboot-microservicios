package ucuenca.ejemplo.store.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ucuenca.ejemplo.store.product.entity.Category;
import ucuenca.ejemplo.store.product.entity.Product;
import ucuenca.ejemplo.store.product.repository.ProductRepository;
import ucuenca.ejemplo.store.product.service.ProductService;
import ucuenca.ejemplo.store.product.service.ProductServiceImpl;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);

        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .stock(Double.parseDouble("5"))
                .price(Double.parseDouble("12.55"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetID_ThenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdatedStick_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L, Double.parseDouble("10"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(15);
    }
}
