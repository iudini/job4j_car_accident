package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Collection<Accident> getAccidents() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(
                            "select distinct a from Accident a " +
                                    "left join fetch a.type " +
                                    "left join fetch a.rules " +
                                    "order by a.id"
                            , Accident.class)
                    .list();
        }
    }

    public Collection<AccidentType> getTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class).list();
        }
    }

    public Collection<Rule> getRules() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class).list();
        }
    }

    public void create(Accident accident, String[] ids) {
        try (Session session = sf.openSession()) {
            for (String id : ids) {
                Rule rule = session.get(Rule.class, Integer.parseInt(id));
                accident.addRule(rule);
            }
            session.save(accident);
        }
    }

    public void update(Accident accident, String[] ids) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            for (String id : ids) {
                Rule rule = session.get(Rule.class, Integer.parseInt(id));
                accident.addRule(rule);
            }
            session.update(accident);
            session.getTransaction().commit();
        }
    }

    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            Accident accident = session.createQuery("select distinct a from Accident a " +
                    "left join fetch a.type " +
                    "left join fetch a.rules " +
                    "where a.id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return accident == null ? Optional.empty() : Optional.of(accident);
        }
    }
}
