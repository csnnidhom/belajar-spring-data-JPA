package belajar.springdatajpa.repository;

import belajar.springdatajpa.entity.Category;
import belajar.springdatajpa.entity.Product;
import belajar.springdatajpa.model.ProductPrice;
import belajar.springdatajpa.model.SimpleProduct;
import org.hibernate.engine.jdbc.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct() {
        Category category = categoryRepository.findById(2L).orElse(null);
        assertNotNull(category);

        {
            Product product = new Product();
            product.setName("Apple");
            product.setPrice(5_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }

        {
            Product product = new Product();
            product.setName("Xiaomi");
            product.setPrice(3_000_000L);
            product.setCategory(category);
            productRepository.save(product);
        }
    }

    @Test
    void findByCategoryName() {
        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH SEKALI");
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Apple", products.get(0).getName());
        assertEquals("Xiaomi", products.get(1).getName());
    }

    @Test
    void sort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH SEKALI", sort);

        assertEquals("Xiaomi", products.get(0).getName());
        assertEquals("Apple", products.get(1).getName());
    }

    @Test
    void Pageable() {
        Pageable pageable = PageRequest.of(0,1, Sort.by(Sort.Order.desc("id")));
        Page<Product> products = productRepository.findAllByCategory_Name("GADGET MURAH SEKALI", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Xiaomi", products.getContent().get(0).getName());

        pageable = PageRequest.of(1,1, Sort.by(Sort.Order.desc("id")));
        products = productRepository.findAllByCategory_Name("GADGET MURAH SEKALI", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals(1, products.getNumber());
        assertEquals(2, products.getTotalElements());
        assertEquals(2, products.getTotalPages());
        assertEquals("Apple", products.getContent().get(0).getName());

    }

    @Test
    void count() {
        Long count = productRepository.count();
        assertEquals(2L, count);

        count = productRepository.countByCategory_Name("GADGET MURAH SEKALI");
        assertEquals(2L, count);

        count = productRepository.countByCategory_Name("Gk ADA");
        assertEquals(0L, count);
    }

    @Test
    void testExists() {
        boolean exists = productRepository.existsByName("APPLE");
        assertTrue(exists);

        exists = productRepository.existsByName("Apple salah");
        assertFalse(exists);
    }

    @Test
    void deleteOld() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(2L).orElse(null);
            assertNotNull(category);

            Product product = new Product();
            product.setName("Samsung");
            product.setPrice(1_000_000L);
            product.setCategory(category);
            productRepository.save(product);

            int delete = productRepository.deleteByName("Samsung");
            assertEquals(1, delete);

            delete = productRepository.deleteByName("Samsung");
            assertEquals(0, delete);

        });
    }

    @Test
    void deleteNew() {
            Category category = categoryRepository.findById(2L).orElse(null);
            assertNotNull(category);

            Product product = new Product();
            product.setName("Samsung");
            product.setPrice(1_000_000L);
            product.setCategory(category);
            productRepository.save(product);

            int delete = productRepository.deleteByName("Samsung");
            assertEquals(1, delete);

            delete = productRepository.deleteByName("Samsung");
            assertEquals(0, delete);
    }

    @Test
    void namedQuery() {
        Pageable pageable = PageRequest.of(0,1);
        List<Product> products = productRepository.searchProductUsingName("Apple", pageable);
        assertEquals(1, products.size());
        assertEquals("Apple", products.get(0).getName());
    }

    @Test
    void searchProducts() {
        Pageable pageable = PageRequest.of(0,1, Sort.by("id"));
        Page<Product> products = productRepository.searchProduct("Apple", pageable);
        assertEquals(1, products.getContent().size());

        assertEquals(0, products.getNumber());
        assertEquals(1, products.getTotalPages());
        assertEquals(1, products.getTotalElements());

        products = productRepository.searchProduct("%GADGET%", pageable);
        assertEquals(1, products.getContent().size());

        assertEquals(0, products.getNumber());
        assertEquals(2, products.getTotalPages());
        assertEquals(2, products.getTotalElements());
    }

    @Test
    void modifying() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            int total = productRepository.deleteProductUsingName("Salah");
            assertNotNull(total);

            total = productRepository.updateProductPriceToZero(String.valueOf(2L));
            assertEquals(1, total);

            Product product = productRepository.findById(2L).orElse(null);
            assertNotNull(product);
            assertEquals(0L, product.getPrice());
        });
    }

    @Test
    void stream() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Category category = categoryRepository.findById(2L).orElse(null);
            assertNotNull(category);

            Stream<Product> stream = productRepository.streamAllByCategory(category);
            stream.forEach(product -> System.out.println(product.getId() + " : " + product.getName()));
        });
    }

    @Test
    void slice() {
        Pageable firstPage = PageRequest.of(0, 1);

        Category category = categoryRepository.findById(2L).orElse(null);
        assertNotNull(firstPage);

        Slice<Product> sliceProducts = productRepository.findAllByCategory(category, firstPage);

        while(sliceProducts.hasNext()){
            sliceProducts = productRepository.findAllByCategory(category, sliceProducts.nextPageable());
        }
    }

    @Test
    void lock1() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            try{
                Product product = productRepository.findFirstById(1L).orElse(null);
                assertNotNull(product);
                product.setPrice(30_000_000L);

                Thread.sleep(20_000);
                productRepository.save(product);
            }catch (InterruptedException exception){
                throw new RuntimeException(exception);
            }
        });
    }

    @Test
    void lock2() {
        transactionOperations.executeWithoutResult(transactionStatus -> {
            Product product = productRepository.findFirstById(1L).orElse(null);
            assertNotNull(product);
            product.setPrice(10_000_000L);

            productRepository.save(product);
        });
    }

    @Test
    void specification() {
        Specification<Product> specification = (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaQuery.where(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("name"), "Apple"),
                            criteriaBuilder.equal(root.get("name"), "Xiaomi")
                    )
            ).getRestriction();
        };

        List<Product> products = productRepository.findAll(specification);
        assertEquals(2, products.size());
    }

    @Test
    void projection() {
        List<SimpleProduct> simpleProducts = productRepository.findAllByNameLike("%Apple%", SimpleProduct.class);
        assertEquals(1,simpleProducts.size());

        List<ProductPrice> productPrices = productRepository.findAllByNameLike("%Xiaomi%", ProductPrice.class);
        assertEquals(1,productPrices.size());
    }
}