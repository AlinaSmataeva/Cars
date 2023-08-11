package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static model.connection.DatasourceFactory.DB_POOL_SIZE;

public class ConnectionPool {
    private static ConnectionPool instance = null;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> reservedConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(DB_POOL_SIZE);
        reservedConnections = new LinkedBlockingQueue<>(DB_POOL_SIZE);
        try {
            for (int i = 0; i < DB_POOL_SIZE; i++) {
                Connection connection = null;
                connection = ConnectionFactory.createConnection();
                freeConnections.add(new ProxyConnection(connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance() {
        while (instance == null) {
            if (isInitialized.compareAndSet(false, true)) {
                instance = new ConnectionPool();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection proxyConnection;
        try {
            proxyConnection = freeConnections.take();
            reservedConnections.put(proxyConnection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return proxyConnection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean result = false;
        if (connection instanceof ProxyConnection) {
            try {
                reservedConnections.remove((ProxyConnection) connection);
                freeConnections.put((ProxyConnection) connection);
                result = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public void destroyPool() {
        for (int i = 0; i < DB_POOL_SIZE; i++) {
            try {
                freeConnections.take().closeConnection();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}