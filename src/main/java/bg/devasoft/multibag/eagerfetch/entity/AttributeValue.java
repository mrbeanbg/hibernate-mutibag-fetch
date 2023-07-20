package bg.devasoft.multibag.eagerfetch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String val;

    @ManyToOne()
    private Attribute attribute;

    public AttributeValue() {
    }

    public AttributeValue(String val, Attribute attribute) {
        this.val = val;
        this.attribute = attribute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeValue that = (AttributeValue) o;
        return Objects.equals(id, that.id) && val.equals(that.val) && attribute.equals(that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, val, attribute);
    }

    @Override
    public String toString() {
        return "AttributeValue{" +
                "id=" + id +
                ", value='" + val + '\'' +
                ", attribute.name=" + attribute.getName() +
                '}';
    }
}
