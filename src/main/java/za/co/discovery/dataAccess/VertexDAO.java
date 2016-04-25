package za.co.discovery.dataAccess;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.models.Vertex;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;


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

    public Vertex update(Vertex vertex) {
        Session session = sessionFactory.getCurrentSession();
        return (Vertex) session.merge(vertex);
    }

    public int delete(String node) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete Vertex AS v where v.node = :nodeS");
        query.setParameter("nodeS", node);
        return query.executeUpdate();
    }


    public List<Vertex> retrieveVertexList() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        return (List<Vertex>) criteria.list();
    }
    public Vertex retrieveVertex(String nodeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(eq("node", nodeId));
        return (Vertex) criteria.uniqueResult();
    }

    public Vertex retrieveVertexByName(String planetName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(eq("planetName", planetName));
        return (Vertex) criteria.uniqueResult();
    }
}
