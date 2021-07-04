package ucuenca.ejemplo.store.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ucuenca.ejemplo.store.product.entity.Category;
import ucuenca.ejemplo.store.product.entity.Product;
import ucuenca.ejemplo.store.product.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct(){
         Product product01 = Product.builder()
                 .name("computer")
                 .category(Category.builder().id(1L).build())
                 .description("")
                 .stock(Double.parseDouble("10"))
                 .price(Double.parseDouble("128.98"))
                 .status("Created")
                 .createAt(new Date()).build();
         productRepository.save(product01);

        List<Product> founds = productRepository.findByCategory(product01.getCategory());

        Assertions.assertThat(founds.size()).isEqualTo(3);
    }
}