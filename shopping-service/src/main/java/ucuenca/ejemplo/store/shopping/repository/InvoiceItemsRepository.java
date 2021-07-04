package ucuenca.ejemplo.store.shopping.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ucuenca.ejemplo.store.shopping.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {
}