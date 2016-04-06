package za.co.discovery.dataAccess;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.models.Vertex;

import java.util.List;


@Repository
@Transactional
public class VertexDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public VertexDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Vertex save(Vertex vertex) {
        Session session = sessionFactory.getCurrentSession();
        return (Vertex) session.merge(vertex);
    }

    public List<Vertex> retrieveVertex() {
        Session session = sessionFactory.getCurrentSession();
        String hql =
                "SELECT new za.co.discovery.models.Vertex(v.node,COUNT(*)) " +
                        "FROM Vertex v GROUP BY v.username";
        Query query = session.createQuery(hql);
//        Criteria criteria = session.createCriteria(Vertex.class);
//        criteria.add(eq("id", id));
//        return (Vertex) criteria.uniqueResult();
        return query.list();
    }
}
