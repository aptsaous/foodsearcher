package gr.efood.foodsearcher;

import gr.efood.foodsearcher.model.Address;
import gr.efood.foodsearcher.services.DeliveryAddressService;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UIController {

    @FXML
    private Label currentAddress;

    @FXML
    private AnchorPane addrsPane;

    @FXML
    private Button saveButton;

    private final DeliveryAddressService deliveryAddressService;

    public UIController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    public void initialize() {
        log.info("Initializing scene");
        currentAddress.setText(deliveryAddressService.getPrimaryAddress());
    }

    @FXML
    public void onAddressTextChanged(KeyEvent event) {
        List<TextField> textFields = addrsPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());
        boolean disableSaveButton = textFields.stream()
                .anyMatch(textField -> StringUtils.isBlank(textField.getText()));
        saveButton.setDisable(disableSaveButton);
        final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        for (TextField textField : textFields) {
            textField.pseudoClassStateChanged(errorClass, StringUtils.isBlank(textField.getText()));
        }
    }

    @FXML
    public void saveButtonClicked(MouseEvent event) {
        log.info("Save Button clicked");
        List<TextField> textFields = addrsPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());
        Address address = new Address();
        address.setStreetName(textFields.get(0).getText());
        address.setStreetNumber(textFields.get(1).getText());
        address.setPostalCode(textFields.get(2).getText());
        address.setSuburb(textFields.get(3).getText());
        deliveryAddressService.setPrimaryAddress(address);
        currentAddress.setText(String.format("Current address: %s", address.toFormattedString()));
    }
}
