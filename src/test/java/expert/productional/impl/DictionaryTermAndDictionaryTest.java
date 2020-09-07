package expert.productional.impl;


import expert.productional.Dictionary;
import expert.productional.KnowledgeBase;
import expert.productional.Production;
import expert.productional.Term;
import expert.productional.except.ExistsException;
import expert.productional.except.ProductionalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class DictionaryTermAndDictionaryTest {

    @Test
    void InitDictionaryTest() {
        String termName = "test";
        String termDescription = "very important term in programming";
        Term t = new UserTerm(termName, termDescription);
        Dictionary dict = new InMemoryStorage();
        try {
            dict.addTerm(t);
            Term dt = dict.getTerm(termName);
            if (!(dt instanceof DictionaryTerm)) {
               Assertions.fail("Term from dictionary not instanse of DictionaryTerm");
            }
            Assertions.assertEquals(dt.getName(), termName);
            Assertions.assertEquals(dt.getDescription(), termDescription);
        } catch (ProductionalException exp) {
            Assertions.fail("exception! Message: " + exp.getMessage());
        }
    }
    @Test
    void AddExistTermTest() {
        String termName = "test";
        String termDescription = "very important term in programming";
        String term2Description = "...";
        Term t = new UserTerm(termName, termDescription);
        Term t2 = new UserTerm(termName, term2Description);
        Dictionary dict = new InMemoryStorage();
        try {
            dict.addTerm(t);
        } catch(ProductionalException exp) {
            Assertions.fail("exception! Message: " + exp.getMessage());
        }
        Assertions.assertThrows(ExistsException.class, () -> dict.addTerm(t2));
    }

    @Test
    void CreateAndFindRuleTest() {
        Term t1 = new UserTerm("1", "11");
        Term t2 = new UserTerm("2", "22");
        Term t3 = new UserTerm("3", "33");
        Term t4 = new UserTerm("4", "44");
        Production p1 = new UserProduction(Arrays.asList(t1, t2, t3), t4);
        Production p2 = new UserProduction(Arrays.asList(t2, t4), t1);
        Production p3 = new UserProduction(Arrays.asList(t3, t1), t2);
        InMemoryStorage storage = new InMemoryStorage();
        Dictionary dict = storage;
        KnowledgeBase base = storage;
        try {
            dict.addTerm(t1);
            dict.addTerm(t2);
            dict.addTerm(t3);
            dict.addTerm(t4);
            base.addProduction(p1);
            base.addProduction(p2);
            base.addProduction(p3);

            Set<Production> prod = base.getProductionByTerm(t1.getName());
            Assertions.assertEquals(2, prod.size());
            Assertions.assertTrue(prod.contains(p1));
            Assertions.assertTrue(prod.contains(p3));

            prod = base.getProductionByTerm(t4.getName());
            Assertions.assertEquals(1, prod.size());
            Assertions.assertTrue(prod.contains(p2));

        } catch (ProductionalException exp) {
            Assertions.fail("Exception! Message: " + exp.getMessage());
        }
     }

     @Test
     void ActivateUserProductionTest() {
         Term t1 = new UserTerm("1", "11");
         Term t2 = new UserTerm("2", "22");
         Term t3 = new UserTerm("3", "33");
         Term t4 = new UserTerm("4", "44");
         UserProduction p1 = new UserProduction(Arrays.asList(t1, t2, t3), t4);

         Assertions.assertTrue(p1.isActivate(Arrays.asList(t3, t2, t1)));
         Assertions.assertFalse(p1.isActivate(Arrays.asList(t3, t2, t4)));
     }

    @Test
    void ActivateStorageProductionTest() {
        Term t1 = new UserTerm("1", "11");
        Term t2 = new UserTerm("2", "22");
        Term t3 = new UserTerm("3", "33");
        Term t4 = new UserTerm("4", "44");
        Production p1 = new UserProduction(Arrays.asList(t1, t2, t3), t4);

        InMemoryStorage storage = new InMemoryStorage();
        Dictionary dict = storage;
        KnowledgeBase base = storage;
        try {
            dict.addTerm(t1);
            dict.addTerm(t2);
            dict.addTerm(t3);
            dict.addTerm(t4);
            base.addProduction(p1);
            Set<Production> prods =  base.getProductionByTerm(t1.getName());

            Assertions.assertEquals(1, prods.size());

            Production prod = prods.iterator().next();

            Assertions.assertTrue(prod instanceof StorageProduction);

            StorageProduction p = (StorageProduction) prod;

            Assertions.assertTrue(p.isActivate(Arrays.asList(t3, t2, t1)));
            Assertions.assertFalse(p.isActivate(Arrays.asList(t3, t2, t4)));
        } catch (ProductionalException exp) {
            Assertions.fail("Exception! Message: " + exp.getMessage());
        }
    }

}