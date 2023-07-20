package bg.devasoft.multibag.eagerfetch.entity.repository;

import bg.devasoft.multibag.eagerfetch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select distinct p from Product p left join fetch p.attributes as attributes")
    @QueryHints(@QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
    List<Product> findAllProductsAndAttributes();

// if you uncomment the below code, then the compilation is going to lead to MultipleBagFetchException
//    @Query("select distinct p from Product p " +
//            "left join fetch p.attributes as at " +
//            "left join fetch at.attributeValues")
//    @QueryHints(@QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
//    List<Product> findAllProductsAndAttributesAndAttributeValues();
}
