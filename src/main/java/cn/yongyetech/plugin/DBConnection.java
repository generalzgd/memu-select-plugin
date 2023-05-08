package cn.yongyetech.plugin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOG = Logger.getLogger(DBConnection.class.getName());

    private static DBConnection instance;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e) {
            LOG.info(e.toString());
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection con = null;

        try{
            con = DriverManager.getConnection("jdbc:mysql://10.225.136.240:3306/nlp?useSSL=false","root","!qazxsw2");
        }catch (Exception e){
            LOG.info(e.toString());
            throw e;
        }
        return con;
    }
}
