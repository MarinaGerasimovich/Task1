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

        Session session=null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY," +
                    "name VARCHAR (255)," +
                    "lastname VARCHAR (255)," +
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

        Session session = null;
        try {
            session = sessionFactory.openSession();
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

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем - " + name + " добавлен в базу данных Hibernate");
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

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(session.get(User.class, id));
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

        Session session = null;
        List<User> users1 = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            users1 = session.createQuery("from User", User.class).list();
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

        Session session = null;
        try {
            session = sessionFactory.openSession();
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


