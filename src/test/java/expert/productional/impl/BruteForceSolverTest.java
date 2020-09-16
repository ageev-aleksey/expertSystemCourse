//package expert.productional.impl;
//import expert.productional.*;
//import expert.productional.Dictionary;
//import expert.productional.except.ExistsException;
//import expert.productional.except.InvalidProduction;
//import expert.productional.except.NotFoundException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//
//class DataMock {
//       public static UserTerm t1 = new UserTerm("t1", "t1");
//       public static UserTerm t2 = new UserTerm("t2", "t2");
//       public static UserTerm t3 = new UserTerm("t3", "t3");
//
//}
//
//
//class MockDictionary implements Dictionary {
//
//    @Override
//    public Set<Term> getTerms() {
//        return null;
//    }
//
//    @Override
//    public Term addTerm(Term term) throws ExistsException {
//        return null;
//    }
//
//    @Override
//    public Term getTerm(String termName) throws NotFoundException {
//        return null;
//    }
//
//    @Override
//    public boolean isContain(String termName) {
//        return false;
//    }
//}
//
//
//class MockKnowledgeBase implements KnowledgeBase {
//
//    @Override
//    public Production getProduction(String name) throws NotFoundException {
//        return null;
//    }
//
//    @Override
//    public void addProduction(Production production) throws ExistsException, InvalidProduction {
//
//    }
//
//    @Override
//    public Set<String> namesProductions() {
//        return null;
//    }
//
//    @Override
//    public Set<Production> getProductionByTerm(String termName) {
//        Set<Production> p = new HashSet<>();
//        if(termName.equals("t1")) {
//            p.add(new UserProduction(Arrays.asList(DataMock.t1, DataMock.t2), DataMock.t3));
//            p.add(new UserProduction(Arrays.asList(DataMock.t1), DataMock.t2));
//        } else if (termName.equals("t2")) {
//            p.add(new UserProduction(Arrays.asList(DataMock.t1, DataMock.t2), DataMock.t3));
//        }
//        return p;
//    }
//
//    @Override
//    public Set<Production> getProductions() {
//        return null;
//    }
//}
//
//class MockWorkingMem implements WorkingMemory {
//
//    @Override
//    public void addTerm(Term term) {
//
//    }
//
//    @Override
//    public void addTerms(Iterator<Term> iterator) {
//
//    }
//
//    @Override
//    public Set<Term> getTerms() {
//        Set<Term> terms = new HashSet<>();
//        terms.add(DataMock.t1);
//        terms.add(DataMock.t2);
//        terms.add(DataMock.t3);
//        return terms;
//    }
//
//    @Override
//    public boolean isContain(Term term) {
//        return false;
//    }
//}
//
//public class BruteForceSolverTest {
//
//    @Test
//    void simpleSolveTest() {
//        BruteForceSolver solver = new BruteForceSolver();
//
//        Set<Production> res = solver.solve(new MockDictionary(), new MockKnowledgeBase(), new MockWorkingMem());
//        Assertions.assertEquals(2, res.size());
//        List<Term> r = new LinkedList<>();
//        for(Production p : res) {
//            r.add(p.getConclusion());
//        }
//        Assertions.assertTrue(r.contains(DataMock.t2));
//        Assertions.assertTrue(r.contains(DataMock.t3));
//    }
//}
