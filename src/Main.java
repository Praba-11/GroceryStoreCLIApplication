import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.repository.jdbc.JdbcProductDAO;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Creating Product Object
        Product product = new Product("101r", "banana", "kg", "fruits", 10);

        JdbcProductDAO jdbcProductDAO = new JdbcProductDAO();

        jdbcProductDAO.delete("101b");
    }
}