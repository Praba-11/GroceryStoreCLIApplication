package com.billing.app.domain.presentation;
import com.billing.app.domain.application.ProductService;
import com.billing.app.domain.application.ProductServiceInterface;
import com.billing.app.domain.entity.Product;
import com.billing.app.domain.repository.CustomException;
import com.billing.app.domain.repository.ProductJdbcDAO;
import com.billing.app.domain.repository.ProductDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductAction {
    private Product product;
    private ProductDAO productDAO;
    private ProductServiceInterface productServiceInterface;

    public Product create(ArrayList arrayList) throws Throwable {
        try {
            Product product = new Product();
            product.setCode(arrayList.get(2).toString());
            product.setName(arrayList.get(3).toString());
            product.setUnitCode(arrayList.get(4).toString());
            product.setType(arrayList.get(5).toString());
            product.setPrice(Float.parseFloat(arrayList.get(6).toString()));
            productServiceInterface = new ProductService();
            return productServiceInterface.create(product);
        } catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public Product edit(ArrayList arrayList) throws Throwable {
        productDAO = new ProductJdbcDAO();
        String code = arrayList.get(3).toString();
        ArrayList editArrayList = new ArrayList<>(arrayList.subList(4, arrayList.size()));
        product = productDAO.getProduct(code);
        productServiceInterface = new ProductService();
        return productServiceInterface.edit(product, editArrayList);
    }

    public boolean delete(ArrayList arrayList) throws Throwable {
        try {
            String code = arrayList.get(2).toString();
            productServiceInterface = new ProductService();

            productDAO = new ProductJdbcDAO();
            if (productDAO.getStock(code) != 0) {
                productDAO.delete(code);
                return true;
            } else {
                throw new CustomException("Product stock not zero.");
            }
        } catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
    }

    public ArrayList<Product> list(ArrayList arrayList) throws CustomException {
        try {
            ArrayList list;
            int range, page;
            String searchText;
            if (arrayList.size() == 2) {
                return productDAO.list();
            } else if (arrayList.get(2).equals("-p")) {
                if (arrayList.size() == 4) {
                    range = Integer.parseInt(arrayList.get(4).toString());
                    return productDAO.list(range);
                } else if (arrayList.size() == 5) {
                    range = Integer.parseInt(arrayList.get(4).toString());
                    page = Integer.parseInt(arrayList.get(5).toString());
                    return productDAO.list(range, page);
                } else throw new CustomException("Template mismatch.");
            } else if (arrayList.get(2).equals("-s")) {

                searchText = arrayList.get(4).toString();
                return productDAO.list(searchText);
            }
        } catch (Throwable exception) {
            throw new CustomException(exception.getMessage());
        }
        return null;
    }
}
