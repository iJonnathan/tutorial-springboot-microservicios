package ucuenca.ejemplo.store.shopping.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Data
public class Region {
    private Long id;
    private String name;
}
