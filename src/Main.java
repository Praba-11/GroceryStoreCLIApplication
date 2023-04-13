import com.billing.app.domain.entity.Product;
import com.billing.app.domain.entity.Store;
import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.repository.jdbc.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Product product = new Product("101v", "apple", "kg", "fruits", 10);
        Unit unit = new Unit("piece", "pc", "quantity", true);

        ProductDAO productDAO = new JdbcProductDAO();
        UnitDAO unitDAO = new JdbcUnitDAO();

        Store store = new Store("Kannan Departmental Store", 8254387976L, "Surveyor Colony, Madurai", 123456789123456L);
        StoreDAO storeDAO = new JdbcStoreDAO();

        storeDAO.create(store);

    }

}