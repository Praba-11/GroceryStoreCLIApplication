package com.billing.app.domain.database;

import com.billing.app.domain.entity.PurchaseItem;

import java.sql.SQLException;

public interface PurchaseItemDAO {

    /**
     * Interface for creating a purchase item entry.
     * This interface defines a method to create a purchase item entry in the database.
     * Implementations of this interface should handle the necessary operations to insert
     * the provided purchase item into the database as a new entry.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @param purchaseItem The PurchaseItem object representing the purchase item to create.
     * @throws SQLException If an error occurs during the process of creating the purchase item entry.
     */
    void create(PurchaseItem purchaseItem) throws SQLException;

}
