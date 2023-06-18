package com.ismail.homesystem.api.mysql.daos;

import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import com.ismail.homesystem.api.mysql.utils.HibernateManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayerHouseDAO {
    public CompletableFuture<Void> savePlayerHouse(PlayerHouse playerHouse) {
        return CompletableFuture.runAsync(() -> {
            Transaction transaction = null;
            try (Session session = HibernateManager.getHibernate().openSession()) {
                // start a transaction
                transaction = session.beginTransaction();
                // save the object
                session.persist(playerHouse);
                // commit transaction
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                if (e instanceof ConstraintViolationException) {
                    throw new RuntimeException(ErrorCodes.UNKNOWN_ERROR.name(), e);
                }
                throw new RuntimeException(ErrorCodes.UNKNOWN_ERROR.name(), e);
            }
        });
    }

    public CompletableFuture<List<PlayerHouse>> getPlayerHouses() {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = HibernateManager.getHibernate().openSession()) {
                return session.createQuery("from PlayerHouse", PlayerHouse.class).list();
            } catch (Exception e) {
                throw new RuntimeException("Failed to retrieve PlayerHouses", e);
            }
        });
    }
}
