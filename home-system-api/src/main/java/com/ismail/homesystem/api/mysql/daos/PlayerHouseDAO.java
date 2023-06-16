package com.ismail.homesystem.api.mysql.daos;

import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.HibernateInitializer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerHouseDAO {
    public void savePlayerHouse(PlayerHouse playerHouse) {
        Transaction transaction = null;
        try (Session session = HibernateInitializer.getHibernate().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            //save the object
            session.persist(playerHouse);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List< PlayerHouse > getPlayerHouses() {
        try (Session session = HibernateInitializer.getHibernate().openSession()) {
            return session.createQuery("from PlayerHouse", PlayerHouse.class).list();
        }
    }
}
