import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.repository.jdbc.JdbcProductDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Creating Product Object
        Product product = new Product("101vvv", "apple", "kg", "fruits", 10);

        JdbcProductDAO jdbcProductDAO = new JdbcProductDAO();

        ArrayList arrayList = new ArrayList();
        arrayList.add("name");
        arrayList.add("water");
        arrayList.add("unitCode");
        arrayList.add("l");
        arrayList.add("type");
        arrayList.add("drinks");

        jdbcProductDAO.edit("101a", arrayList);
    }
}