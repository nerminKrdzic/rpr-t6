package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BackgroundFill;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class Controller {
    //osnovni podaci
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField brojIndexaTextField;

    //detaljni licni podaci
    @FXML
    private TextField jmbgTextField;
    @FXML
    private DatePicker datumTextField;
    @FXML
    private TextField placeTextField;

    //kontakt podaci
    @FXML
    private TextField kontaktAdresaTextField;
    @FXML
    private TextField kontaktTelefonTextField;
    @FXML
    private TextField emailAdresaTextField;

    //podaci o studiju
    @FXML
    private ChoiceBox<String> odsjekChoiceBox;
    @FXML
    private ChoiceBox<String> godinaStudijaChoiceBox;
    @FXML
    private ChoiceBox<String> CiklusStudijaChoiceBox;
    @FXML
    private RadioButton redovanRadioButton;
    @FXML
    private RadioButton samofinansirajuciRadioButton;
    @FXML
    private CheckBox borackaKategorijaCheckBox;


    private Boolean validirajImePrezime(String string){
        string.trim();
        if(string == null || string.length() > 20 || string.length() == 0 || string.contains(" ")) return false;
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) >= 'A' && string.charAt(i) <= 'Z' || string.charAt(i) >= 'a' && string.charAt(i) <= 'z') return true;
        }
        return false;
    }

    public boolean validirajMail(String mail) {
        mail.trim();
        if(mail.endsWith("@hotmail.com") || mail.endsWith("@outlook.com")
                || mail.endsWith("@gmail.com") || mail.endsWith("@etf.unsa.ba")) return true;
        return false;
    }

    public boolean validanindex(String index) {
        if (index.length() == 0 || index.length() > 5) return false;
        for (int i = 0; i < index.length(); i++) {
            if (!Character.isDigit(index.charAt(i))) return false;
        }
        return true;
    }

    @FXML
    public void initialize(){
        imeTextField.getStyleClass().add("poljeNijeIspravno");
        prezimeTextField.getStyleClass().add("poljeNijeIspravno");
        brojIndexaTextField.getStyleClass().add("poljeNijeIspravno");
        datumTextField.getStyleClass().add("poljeNijeIspravno");
        emailAdresaTextField.getStyleClass().add("poljeNijeIspravno");
        jmbgTextField.getStyleClass().add("poljeNijeIspravno");

        
    }

}
