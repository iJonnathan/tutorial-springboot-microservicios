package ucuenca.ejemplo.store.shopping.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucuenca.ejemplo.store.shopping.client.CustomerClient;
import ucuenca.ejemplo.store.shopping.client.ProductClient;
import ucuenca.ejemplo.store.shopping.entity.Invoice;
import ucuenca.ejemplo.store.shopping.entity.InvoiceItem;
import ucuenca.ejemplo.store.shopping.model.Customer;
import ucuenca.ejemplo.store.shopping.model.Product;
import ucuenca.ejemplo.store.shopping.repository.InvoiceItemsRepository;
import ucuenca.ejemplo.store.shopping.repository.InvoiceRepository;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll() {
        return  invoiceRepository.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice ( invoice.getNumberInvoice () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        invoice.setState("CREATED");

        invoiceDB = invoiceRepository.save(invoice);
        // por cada item de nuestra factura actualizamos el stock
        invoiceDB.getItems().forEach(invoiceItem ->{
            productClient.updateStockProduct(invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {


        Invoice invoice =  invoiceRepository.findById(id).orElse(null);
        if (invoice != null ){
            // realizamos la busqueda del cliente
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            // tenemos que recuperar los datos de cada producto de nuestra factura

            List<InvoiceItem> listItem = invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList()); // este flujo lo convertimos en una lista

            invoice.setItems((listItem));
        }
        return invoice;
    }
}