package com.example.csc_311_mod_6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Controller class for managing the form functionality.
 * Handles user input validation, adding users to a list, and enabling/disabling the Add button.
 */
public class FormController {
    // Fields
    @FXML private TextField firstName, lastName, email, dateOfBirth, zipCode;

    // Errors for each field
    @FXML
    private Label firstNameError, lastNameError, emailError, dateOfBirthError, zipCodeError;

    // If all the field inputs are valid allow the add button to work
    private boolean isFirstNameValid, isLastNameValid, isEmailValid, isDateOfBirthValid, isZipCodeValid;

    @FXML private ListView<String> listView;

    @FXML private Button addButton;

    @FXML
    private final ObservableList<String> addedUsers = FXCollections.observableArrayList();

    /**
     * Initializes the controller. Sets up validation for all input fields and binds the ListView to the added users list.
     */
    @FXML
    public void initialize() {
        listView.setItems(addedUsers);

        setValidation(firstName, firstNameError, "^[A-Za-z]{2,25}$", val -> isFirstNameValid = val);
        setValidation(lastName, lastNameError, "^[A-Za-z]{2,25}$", val -> isLastNameValid = val);
        setValidation(email, emailError, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(edu|com)$", val -> isEmailValid = val);
        setValidation(dateOfBirth, dateOfBirthError, "^(0[1-9]|1[0-2])/([0][1-9]|[12][0-9]|3[01])/\\d{4}$", val -> isDateOfBirthValid = val);
        setValidation(zipCode, zipCodeError, "^\\d{5}$", val -> isZipCodeValid = val);
    }

    /**
     * Sets up validation for a given TextField.
     *
     * @param field The TextField to validate.
     * @param errorLabel The Label to display validation errors.
     * @param regex The regular expression to validate the input.
     * @param validitySetter A Consumer to set the validity flag for the field.
     */
    private void setValidation(TextField field, Label errorLabel, String regex, java.util.function.Consumer<Boolean> validitySetter) {
        Pattern pattern = Pattern.compile(regex);

        field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                Matcher matcher = pattern.matcher(field.getText());
                boolean valid = matcher.matches();

                errorLabel.setText(valid ? "" : "Invalid input.");
                validitySetter.accept(valid);
                checkAllValid();
            }
        });
    }

    /**
     * Checks if all input fields are valid and enables/disables the Add button accordingly.
     */
    private void checkAllValid() {
        addButton.setDisable(!(isFirstNameValid && isLastNameValid && isEmailValid && isDateOfBirthValid && isZipCodeValid));
    }



    /**
     * Checks if all input fields are valid and enables/disables the Add button accordingly.
     */
    @FXML
    private void handleAdd() {
        if (addButton.isDisabled()) return;

        String fullName = firstName.getText() + " " + lastName.getText();
        addedUsers.add(fullName);

        firstName.clear();
        lastName.clear();
        email.clear();
        dateOfBirth.clear();
        zipCode.clear();

        isFirstNameValid = false;
        isLastNameValid = false;
        isEmailValid = false;
        isDateOfBirthValid = false;
        isZipCodeValid = false;

        checkAllValid();
    }

}