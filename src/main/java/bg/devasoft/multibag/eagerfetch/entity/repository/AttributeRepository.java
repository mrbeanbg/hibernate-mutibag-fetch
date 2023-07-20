package bg.devasoft.multibag.eagerfetch.entity.repository;

import bg.devasoft.multibag.eagerfetch.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_PASS_DISTINCT_THROUGH;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    @Query("select distinct a from Attribute a left join fetch a.attributeValues as attributeValues")
    @QueryHints(@QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false"))
    List<Attribute> findAllAttributesAndAttributeValues();
}
