package belajar.springdatajpa.service;

import belajar.springdatajpa.entity.Category;
import belajar.springdatajpa.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionOperations;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactionOperations;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void manual(){
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setTimeout(10);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus trasaction = transactionManager.getTransaction(definition);
        try{
            for (int i = 0; i < 5; i++) {
                Category category = new Category();
                category.setName("Category : " + i);
                categoryRepository.save(category);
            }

            error();
            transactionManager.commit(trasaction);
        }catch (Throwable throwable){
            transactionManager.rollback(trasaction);
            throw throwable;
        }
    }

    public void error(){
        throw new RuntimeException("Ups");
    }

    public void createCategories(){
        transactionOperations.executeWithoutResult(transactionStatus -> {
            for (int i = 0; i < 5; i++) {
                Category category = new Category();
                category.setName("Category : " + i);
                categoryRepository.save(category);
            }

            error();
        });
    }

    @Transactional
    public void create(){
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setName("Category : " + i);
            categoryRepository.save(category);
        }
        throw new RuntimeException("Ups rollback please");

    }

    public void test(){
        create();
    }

}
