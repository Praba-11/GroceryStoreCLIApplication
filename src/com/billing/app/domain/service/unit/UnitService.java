package com.billing.app.domain.service.unit;

import com.billing.app.domain.entity.Unit;
import com.billing.app.domain.exceptions.NotFoundException;
import com.billing.app.domain.exceptions.ObjectNullPointerException;

import java.sql.SQLException;
import java.util.List;

public interface UnitService {

    /**
     * Service layer interface for creating a unit.
     * This interface defines a method to create a unit based on the provided unit object.
     * Implementations of this interface should handle the necessary business logic for creating units.
     * If any SQLException occurs during the process or if a required object is null, the respective exceptions will be thrown.
     * @param unit The Unit object representing the unit to be created.
     * @return The created Unit object with additional details, such as the generated unit ID.
     * @throws SQLException If an error occurs during the unit creation process.
     * @throws ObjectNullPointerException If any required object, such as the unit object itself, is null.
     */
    Unit create(Unit unit) throws SQLException, ObjectNullPointerException;


    /**
     * Service layer interface for editing a unit.
     * This interface defines a method to edit a unit based on the provided unit object.
     * Implementations of this interface should handle the necessary business logic for editing units.
     * If any SQLException occurs during the process, if a required object is null, or if the unit's code or ID is not found, the respective exceptions will be thrown.
     * @param unit The Unit object representing the unit to be edited.
     * @return The updated Unit object with the modified details.
     * @throws SQLException If an error occurs during the unit editing process.
     * @throws NotFoundException If the unit's code or ID is not found.
     * @throws ObjectNullPointerException If any required object, such as the unit object itself, is null.
     */
    Unit edit(Unit unit) throws SQLException, NotFoundException, ObjectNullPointerException;


    /**
     * Service layer interface for deleting a unit.
     * This interface defines a method to delete a unit based on the provided unit ID.
     * Implementations of this interface should handle the necessary business logic for deleting units.
     * If any SQLException occurs during the process or if the unit's code or ID is not found, the respective exceptions will be thrown.
     * @param id The ID of the unit to be deleted.
     * @return A boolean indicating whether the deletion was successful or not.
     * @throws SQLException If an error occurs during the unit deletion process.
     * @throws NotFoundException If the unit's code or ID is not found.
     */
    boolean delete(int id) throws SQLException, NotFoundException;


    /**
     * Service layer interface for listing units.
     * This interface defines a method to retrieve a list of all units.
     * Implementations of this interface should handle the necessary business logic for listing units.
     * If any SQLException occurs during the process, the respective exception will be thrown.
     * @return A list of Unit objects representing all the units.
     * @throws SQLException If an error occurs during the unit listing process.
     */
    List<Unit> list() throws SQLException;


}
