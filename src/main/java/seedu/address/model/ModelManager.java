package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.customer.Customer;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ReserveMate ReserveMate;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;

    /**
     * Initializes a ModelManager with the given ReserveMate and userPrefs.
     */
    public ModelManager(ReadOnlyReserveMate ReserveMate, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(ReserveMate, userPrefs);

        logger.fine("Initializing with address book: " + ReserveMate + " and user prefs " + userPrefs);

        this.ReserveMate = new ReserveMate(ReserveMate);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.ReserveMate.getCustomerList());
    }

    public ModelManager() {
        this(new ReserveMate(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getReserveMateFilePath() {
        return userPrefs.getReserveMateFilePath();
    }

    @Override
    public void setReserveMateFilePath(Path ReserveMateFilePath) {
        requireNonNull(ReserveMateFilePath);
        userPrefs.setReserveMateFilePath(ReserveMateFilePath);
    }

    //=========== ReserveMate ================================================================================

    @Override
    public void setReserveMate(ReadOnlyReserveMate ReserveMate) {
        this.ReserveMate.resetData(ReserveMate);
    }

    @Override
    public ReadOnlyReserveMate getReserveMate() {
        return ReserveMate;
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return ReserveMate.hasCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        ReserveMate.removeCustomer(target);
    }

    @Override
    public void addCustomer(Customer customer) {
        ReserveMate.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        ReserveMate.setCustomer(target, editedCustomer);
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedReserveMate}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return ReserveMate.equals(otherModelManager.ReserveMate)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredCustomers.equals(otherModelManager.filteredCustomers);
    }

}
