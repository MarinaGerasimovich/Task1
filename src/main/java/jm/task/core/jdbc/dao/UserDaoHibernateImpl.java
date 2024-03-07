package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = Util.openConnectionHibernate();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY," +
                    "name VARCHAR (255)," +
                    "last_name VARCHAR (255)," +
                    "age INTEGER)").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем - " + name + " добавлен в базу данных Hibernate");
            /*User user = new User(name, lastName, age);
            session.createSQLQuery("INSERT INTO users (name, last_name, age) VALUES (?,?,?)")
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();
            */
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            /*
            session.createSQLQuery("DELETE FROM users WHERE id = ?")
                    .setParameter(1, id)
                    .executeUpdate();
             */
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();

        List<User> users1 = new ArrayList<>();
        try {
            session.beginTransaction();

            users1 = session.createQuery("from User", User.class).list();
            //users1 = session.createSQLQuery("SELECT id, name, last_name, age FROM users").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users1;
    }

    @Override
    public void cleanUsersTable() {

        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            session.createSQLQuery("DELETE FROM users").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }


    }
}


