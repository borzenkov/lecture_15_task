import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Created by vic on 22.11.16.
 */
public class WorkersList extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(WorkersList.class);

    public static final String DATABASE_URL = "jdbc:h2:~/test";

    public static final String SELECT_QUERY = "SELECT * FROM PRODUCT;";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("Can't load jdbc driver", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        try(Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);
            while(resultSet.next()) {
                LOG.info(new StringBuilder(resultSet.getInt(1)).append("|").append(resultSet.getString(2)).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
