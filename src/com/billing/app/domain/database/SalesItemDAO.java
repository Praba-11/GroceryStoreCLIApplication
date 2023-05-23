package com.billing.app.domain.database;

import com.billing.app.domain.entity.PurchaseItem;
import com.billing.app.domain.entity.SalesItem;

import java.sql.SQLException;

public interface SalesItemDAO {
    /**
     * Interface for creating a sales item entry.
     * This interface defines a method to create a sales item entry in the database.
     * Implementations of this interface should handle the necessary operations to insert
     * the provided sales item into the database as a new entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param salesItem The SalesItem object representing the sales item to create.
     * @throws SQLException If an error occurs during the process of creating the sales item entry.
     */
    void create(SalesItem salesItem) throws SQLException;
}
