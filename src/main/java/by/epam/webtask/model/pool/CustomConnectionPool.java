package by.epam.webtask.model.pool;

import by.epam.webtask.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static by.epam.webtask.model.pool.ConnectionFactory.POOL_SIZE;

public class CustomConnectionPool {
    static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final AtomicBoolean create = new AtomicBoolean(false);
    private static final ReentrantLock lockerCreator = new ReentrantLock();
    private static CustomConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> giveAwayConnections;

    public static void main(String[] args) {
        CustomConnectionPool customConnectionPool = new CustomConnectionPool();
    }

    private CustomConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        giveAwayConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                ProxyConnection connection = new ProxyConnection(ConnectionFactory.createConnection());
                boolean isCreated = freeConnections.offer(connection);
                logger.log(Level.DEBUG, "Connection is " + isCreated);
            } catch (ConnectionPoolException e) {
                logger.log(Level.ERROR, "Connection was not created " + e.getMessage());
            }
        }
        if (freeConnections.isEmpty()) {
            logger.log(Level.FATAL, "There are not connections!");
            throw new RuntimeException("There are not connections!");
        } else if (freeConnections.size() < POOL_SIZE) {
            int connectionSize = freeConnections.size();
            while (connectionSize != POOL_SIZE) {
                try {
                    ProxyConnection connection = new ProxyConnection(ConnectionFactory.createConnection());
                    freeConnections.offer(connection);
                } catch (ConnectionPoolException e) {
                    logger.log(Level.ERROR, "Connection was not created " + e.getMessage());
                    throw new RuntimeException("Connection was not created." + e.getMessage());
                }
                connectionSize++;
            }
        }
    }

    public static CustomConnectionPool getInstance() {
        if (!create.get()) {
            try {
                lockerCreator.lock();
                if (instance == null) {
                    instance = new CustomConnectionPool();
                    create.set(true);
                }
            } finally {
                lockerCreator.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            giveAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "The thread was interrupted!" + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) { //TODO boolean?
        try {
            if (connection.getClass() != ProxyConnection.class) {
                throw new ConnectionPoolException("Illegal connection!");
            }
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            giveAwayConnections.remove(proxyConnection);
            freeConnections.put(proxyConnection);
        } catch (ConnectionPoolException | InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
            Thread.currentThread().interrupt();
        }
        return false; //TODO great?
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
                logger.log(Level.INFO, "Connection closed!");
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "The thread was interrupted!" + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        logger.log(Level.DEBUG, "Deregister driver method.");
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                logger.log(Level.INFO, "Deregister driver.");
            } catch (SQLException e) {
                logger.log(Level.ERROR, "The driver was not removed");
            }
        });
    }
}
