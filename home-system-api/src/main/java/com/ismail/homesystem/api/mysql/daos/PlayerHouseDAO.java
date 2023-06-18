package com.ismail.homesystem.api.mysql.daos;

import com.ismail.homesystem.api.mysql.models.PlayerHouse;
import com.ismail.homesystem.api.mysql.utils.ErrorCodes;
import com.ismail.homesystem.api.mysql.utils.HibernateManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;
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
            } catch (ConstraintViolationException e) {
                rollback(transaction);
                throw new RuntimeException(ErrorCodes.CONSTRAINT_VIOLATION.name());
            }catch (Exception e){
                rollback(transaction);
                throw new RuntimeException(ErrorCodes.UNHANDLED_ERROR.name(), e);
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

    public CompletableFuture<PlayerHouse> findByID(UUID playerUUID, String homeName, boolean andIsDelete) {
        return CompletableFuture.supplyAsync(() -> {
            Transaction transaction = null;
            try (Session session = HibernateManager.getHibernate().openSession()) {
                Query<PlayerHouse> query = session.createQuery(
                        "from PlayerHouse where homeName = :homeName and playerUUID = :playerId", PlayerHouse.class);
                query.setParameter("homeName", homeName);
                query.setParameter("playerId", playerUUID);
                PlayerHouse playerHouse = query.uniqueResult();
                if (playerHouse == null){
                    throw new EntityNotFoundException("PlayerHouse not found" + playerUUID + " " + homeName);
                }
                if (andIsDelete){
                    transaction = session.beginTransaction();
                    session.remove(playerHouse);
                    transaction.commit();
                }
                return playerHouse;
            }catch(EntityNotFoundException | ObjectNotFoundException e){
                if (andIsDelete){
                    rollback(transaction);
                }
                throw new RuntimeException(ErrorCodes.NOT_FOUND.name(), e);
            }catch (Exception e) {
                if (andIsDelete){
                    rollback(transaction);
                }
                throw new RuntimeException(ErrorCodes.UNHANDLED_ERROR.name(), e);
            }
        });
    }
    private void rollback(Transaction transaction){
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
