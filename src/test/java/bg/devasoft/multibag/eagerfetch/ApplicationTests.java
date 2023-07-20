package bg.devasoft.multibag.eagerfetch;

import bg.devasoft.multibag.eagerfetch.entity.AttributeValue;
import bg.devasoft.multibag.eagerfetch.entity.Product;
import bg.devasoft.multibag.eagerfetch.entity.Attribute;
import bg.devasoft.multibag.eagerfetch.entity.repository.AttributeRepository;
import bg.devasoft.multibag.eagerfetch.entity.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AttributeRepository attributeRepository;

	@BeforeAll
	@Transactional
	void setUp() {
		// db seed
		Product product1 = new Product("Product1");
		Attribute attribute1_1 = new Attribute("attribute1-1", product1);
		AttributeValue av1_1_1 = new AttributeValue("attribute-value-1-1-1", attribute1_1);
		AttributeValue av1_1_2 = new AttributeValue("attribute-value-1-1-2", attribute1_1);
		AttributeValue av1_1_3 = new AttributeValue("attribute-value-1-1-2", attribute1_1);
		attribute1_1.getAttributeValues().add(av1_1_1);
		attribute1_1.getAttributeValues().add(av1_1_2);
		attribute1_1.getAttributeValues().add(av1_1_3);
		Attribute attribute1_2 = new Attribute("attribute1-2", product1);
		Attribute attribute1_3 = new Attribute("attribute1-3", product1);
		Attribute attribute1_4 = new Attribute("attribute1-4", product1);

		product1.getAttributes().add(attribute1_1);
		product1.getAttributes().add(attribute1_2);
		product1.getAttributes().add(attribute1_3);
		product1.getAttributes().add(attribute1_4);

		Product product2 = new Product("Product2");
		Attribute attribute2_1 = new Attribute("attribute2-1", product2);
		AttributeValue av2_1_1 = new AttributeValue("attribute-value-2-1-1", attribute2_1);
		AttributeValue av2_1_2 = new AttributeValue("attribute-value-2-1-2", attribute2_1);
		AttributeValue av2_1_3 = new AttributeValue("attribute-value-2-1-2", attribute2_1);
		attribute2_1.getAttributeValues().add(av2_1_1);
		attribute2_1.getAttributeValues().add(av2_1_2);
		attribute2_1.getAttributeValues().add(av2_1_3);

		Attribute attribute2_2 = new Attribute("attribute2-2", product2);
		Attribute attribute2_3 = new Attribute("attribute2-3", product2);
		Attribute attribute2_4 = new Attribute("attribute2-4", product2);

		product1.getAttributes().add(attribute2_1);
		product1.getAttributes().add(attribute2_2);
		product1.getAttributes().add(attribute2_3);
		product1.getAttributes().add(attribute2_4);

		productRepository.saveAll(Arrays.asList(product1, product2));
		productRepository.flush();
	}

	@Test
	@Transactional
	void nPlusOneExample() {
		logger.debug("1 query to select all products");
		List<Product> allProducts = productRepository.findAll();
		logger.debug("Number of product records: " + allProducts.size());
		allProducts.forEach(product -> {
			logger.debug("Product " + product.getName());
			product.getAttributes().forEach(productAttribute -> {
				logger.debug("\tAttribute " + productAttribute.getName());
			});
		});
	}

	@Test
	@Transactional
	void eagerFetchExample() {
		logger.debug("1 query to select all products");
		List<Product> allProducts = productRepository.findAllProductsAndAttributes();
		logger.debug("Number of product records: " + allProducts.size());
		allProducts.forEach(product -> {
			logger.debug("Product " + product.getName());
			product.getAttributes().forEach(productAttribute -> {
				logger.debug("\tAttribute " + productAttribute.getName());
			});
		});
	}

	@Test
	@Transactional
	void eagerFetchWithSeparateQueries() {
		logger.debug("Query to select all products and their attributes");
		List<Product> allProducts = productRepository.findAllProductsAndAttributes();
		logger.debug("Query to select all attributes and their attribute values");
		List<Attribute> allAttributes = attributeRepository.findAllAttributesAndAttributeValues();
		logger.debug("Number of product records: " + allProducts.size());
		allProducts.forEach(product -> {
			logger.debug("Product " + product.getName());
			product.getAttributes().forEach(productAttribute -> {
				logger.debug("\tAttribute " + productAttribute.getName());
				productAttribute.getAttributeValues().forEach(attributeValue -> {
					logger.debug("\t\tAttributeValue " + attributeValue.getVal());
				});
			});
		});
	}
}
