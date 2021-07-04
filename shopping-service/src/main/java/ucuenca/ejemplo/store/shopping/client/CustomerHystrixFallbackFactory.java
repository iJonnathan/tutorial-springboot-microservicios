package ucuenca.ejemplo.store.shopping.client;

import org.springframework.http.ResponseEntity;
import ucuenca.ejemplo.store.shopping.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerHystrixFallbackFactory  implements CustomerClient{
    @Override
    public ResponseEntity<Customer> getCustomer(long id) {
        Customer customer = Customer.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();
        return ResponseEntity.ok(customer);
    }
}
