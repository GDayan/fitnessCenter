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

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static final String DATABASE_PROPERTIES_FILE_NAME = "data/database.properties";
    private static final String DB_URL = "db.url";
    protected static final int POOL_SIZE;
    private static String fileProperties;

    static {
        try {
            ClassLoader loader = ConnectionFactory.class.getClassLoader();
            URL resource = loader.getResource(DATABASE_PROPERTIES_FILE_NAME);
            if (resource != null) {
                fileProperties = resource.getFile();
            } else {
                logger.log(Level.ERROR, "Resource is null! " + DATABASE_PROPERTIES_FILE_NAME);
                throw new IllegalArgumentException("Resource is null! " + DATABASE_PROPERTIES_FILE_NAME);
            }
            properties.load(new FileReader(fileProperties));
            String driverName = (String) properties.get("db.driver");
            POOL_SIZE = Integer.parseInt((String) properties.get("poolSize"));
            Class.forName(driverName);
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.FATAL, "File properties exception: " + fileProperties);
            throw new RuntimeException("File properties exception." + e.getMessage());
        }
        DATABASE_URL = (String) properties.get(DB_URL);
    }

    private ConnectionFactory() {
    }

    static Connection createConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(DATABASE_URL, properties);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Connection is not received: " + e.getMessage());
        }
    }
}