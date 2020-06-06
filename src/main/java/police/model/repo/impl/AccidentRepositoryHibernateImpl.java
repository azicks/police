package police.model.repo.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import police.model.Accident;
import police.model.repo.AccidentRepository;

import java.util.List;

@Repository
public class AccidentRepositoryHibernateImpl implements AccidentRepository {
    private final SessionFactory sf;

    @Autowired
    public AccidentRepositoryHibernateImpl(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public boolean addAccident(Accident accident) {
        Transaction transaction = null;
        try(Session session = sf.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.save(accident);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        Transaction transaction = null;
        try(Session session = sf.openSession()) {
            Accident accident = session.load(Accident.class, id);
            transaction = session.getTransaction();
            transaction.begin();
            session.delete(accident);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean update(Accident accident) {
        Transaction transaction = null;
        try(Session session = sf.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.update(accident);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public List<Accident> getAllAccidents() {
        try(Session session = sf.openSession()) {
            return session.createQuery("from Accident", Accident.class).list();
        }
    }

    @Override
    public Accident getById(long id) {
        try(Session session = sf.openSession()) {
            return session.get(Accident.class, id);
        }
    }
}
