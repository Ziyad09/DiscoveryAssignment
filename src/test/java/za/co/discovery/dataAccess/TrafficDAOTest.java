//package za.co.discovery.dataAccess;
//
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.transaction.annotation.Transactional;
//import za.co.discovery.DAOConfig;
//import za.co.discovery.configuration.DataSourceConfig;
//import za.co.discovery.configuration.PersistenceConfig;
//import za.co.discovery.models.Traffic;
//
//import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//import static za.co.discovery.models.TrafficBuilder.aTraffic;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = {PersistenceConfig.class, DataSourceConfig.class, DAOConfig.class, TrafficDAO.class},
//        loader = AnnotationConfigContextLoader.class)
//public class TrafficDAOTest {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//    private TrafficDAO trafficDAO;
//    private Traffic firstTraffic;
//
//    @Before
//    public void init() {
//        trafficDAO = new TrafficDAO(sessionFactory);
//    }
//
//    @Test
//    public void testSave() throws Exception {
//
//        // Set up Fixture
//        getTraffic();
//        Session session = sessionFactory.getCurrentSession();
//
//        // Exercise SUT
//        trafficDAO.save(firstTraffic);
//        Criteria criteria = session.createCriteria(Traffic.class);
//        Traffic actualTraffic = (Traffic) criteria.uniqueResult();
//
//        // Verify Behaviour
//        assertThat(actualTraffic, is(sameBeanAs(firstTraffic)));
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//
//        // Set up Fixture
//        getTraffic();
//
//        // Exercise SUT
//        trafficDAO.save(firstTraffic);
//        Traffic expectedTraffic = aTraffic()
//                .withRouteId(1)
//                .withSource("C")
//                .withDestination("D")
//                .withDistance(5.0)
//                .build();
//        trafficDAO.update(expectedTraffic);
//        Traffic actualTraffic = trafficDAO.retrieveTraffic(1);
//
//        // Verify Behaviour
//        assertThat(actualTraffic, is(sameBeanAs(expectedTraffic)));
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//
//        // Set up Fixture
//        getTraffic();
//
//        // Exercise SUT
//        trafficDAO.save(firstTraffic);
//        int result = trafficDAO.delete(firstTraffic.getRouteId());
//
//        // Verify Behaviour
//        assertThat(result, is(1));
//    }
//
//    @Test
//    public void testRetrieveTraffic() throws Exception {
//
//        // Set up Fixture
//        getTraffic();
//
//        // Exercise SUT
//        Traffic returnedTraffic = trafficDAO.save(firstTraffic);
//        Traffic actualTraffic = trafficDAO.retrieveTraffic(returnedTraffic.getRouteId());
//
//        // Verify Behaviour
//        assertThat(actualTraffic, is(sameBeanAs(firstTraffic)));
//    }
//
//    public void getTraffic() {
//        firstTraffic = aTraffic()
//                .withRouteId(1)
//                .withSource("A")
//                .withDestination("B")
//                .withDistance(1.0)
//                .build();
//    }
//}