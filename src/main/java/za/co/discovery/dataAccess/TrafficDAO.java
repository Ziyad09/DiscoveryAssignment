package za.co.discovery.dataAccess;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.models.Traffic;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
@Transactional
public class TrafficDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public TrafficDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Traffic save(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        return (Traffic) session.merge(traffic);
    }

    public Traffic update(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        return (Traffic) session.merge(traffic);
    }

    public List<Traffic> retrieveTrafficList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        return (List<Traffic>) criteria.list();
    }

    public int delete(int routeId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Traffic AS e where e.routeId = :routeNumber");
        query.setParameter("routeNumber", routeId);
        return query.executeUpdate();
    }

    public Traffic retrieveTraffic(int routeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        criteria.add(eq("routeId", routeId));
        return (Traffic) criteria.uniqueResult();
    }
}
