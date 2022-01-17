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
    private static String fileProperties;

    static {
        try {
            String fileName = "data/database.properties";
            ClassLoader loader = ConnectionFactory.class.getClassLoader();
            URL resource = loader.getResource(fileName);
            if(resource != null) {
                fileProperties = resource.getFile();
            }else{
                logger.log(Level.ERROR,"Resource is null! " + fileName);
                throw new IllegalArgumentException();
            }
            properties.load(new FileReader(fileProperties));
            String driverName = (String) properties.get("db.driver");
            Class.forName(driverName);
        }catch (ClassNotFoundException | IOException e){
            logger.log(Level.FATAL,"File properties exception: " + fileProperties);
            throw new RuntimeException("File properties exception." + e.getMessage());
        }
        DATABASE_URL = (String) properties.get("db.url");
    }
    private  ConnectionFactory(){}
    static Connection createConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(DATABASE_URL,properties);
        }catch (SQLException e){
            throw new ConnectionPoolException("Connection is not received: " + e.getMessage());
        }
    }
}