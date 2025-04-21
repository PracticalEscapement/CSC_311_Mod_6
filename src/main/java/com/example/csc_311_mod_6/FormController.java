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

public class FormController {
    // Fields
    @FXML private TextField firstName, lastName, email, dateOfBirth, zipCode;

    // Errors for each field
    @FXML
    private Label firstNameError, lastNameError, emailError, dateOfBirthError, zipCodeError;
    private boolean isFirstNameValid, isLastNameValid, isEmailValid, isDateOfBirthValid, isZipCodeValid;

    @FXML private ListView<String> listView;

    @FXML private Button addButton;

    @FXML
    private final ObservableList<String> addedUsers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        listView.setItems(addedUsers);

        setValidation(firstName, firstNameError, "^[A-Za-z]{2,25}$", val -> isFirstNameValid = val);
        setValidation(lastName, lastNameError, "^[A-Za-z]{2,25}$", val -> isLastNameValid = val);
        setValidation(email, emailError, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(edu|com)$", val -> isEmailValid = val);
        setValidation(dateOfBirth, dateOfBirthError, "^(0[1-9]|1[0-2])/([0][1-9]|[12][0-9]|3[01])/\\d{4}$", val -> isDateOfBirthValid = val);
        setValidation(zipCode, zipCodeError, "^\\d{5}$", val -> isZipCodeValid = val);
    }

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


    private void checkAllValid() {
        addButton.setDisable(!(isFirstNameValid && isLastNameValid && isEmailValid && isDateOfBirthValid && isZipCodeValid));
    }

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