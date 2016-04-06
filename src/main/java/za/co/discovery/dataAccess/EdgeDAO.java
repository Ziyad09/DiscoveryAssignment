package za.co.discovery.dataAccess;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.models.Edge;

import java.util.List;


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

    public List<Edge> retrieveEdge() {
        Session session = sessionFactory.getCurrentSession();
        String hql =
                "SELECT new za.co.discovery.models.Edge(e.node,COUNT(*)) " +
                        "FROM Edge e GROUP BY e.username";
        Query query = session.createQuery(hql);
//        Criteria criteria = session.createCriteria(Vertex.class);
//        criteria.add(eq("id", id));
//        return (Vertex) criteria.uniqueResult();
        return query.list();
    }
}
