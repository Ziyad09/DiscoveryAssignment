package za.co.discovery.dataAccess;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.models.Edge;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
@Transactional
public class EdgeDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public EdgeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Edge save(Edge edge) {
        Session session = sessionFactory.getCurrentSession();
        return (Edge) session.merge(edge);
    }

    public Edge update(Edge edge) {
        Session session = sessionFactory.getCurrentSession();
        return (Edge) session.merge(edge);
    }

    public int delete(int routeId) {
        Session session = sessionFactory.getCurrentSession();
//        session.delete(edge);
        Query query = session.createQuery("delete Edge AS e where e.routeId = :routeNumber");
        query.setParameter("routeNumber", routeId);
        int result = query.executeUpdate();
        return result;
    }

    public List<Edge> retrieveEdgeList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
//        criteria.add(eq("id", id));
        List<Edge> newEdgeList = (List<Edge>) criteria.list();
        return newEdgeList;
//        return query.list();
    }

    public Edge retrieveEdge(int routeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Edge.class);
        criteria.add(eq("routeId", routeId));
        return (Edge) criteria.uniqueResult();
    }
}
