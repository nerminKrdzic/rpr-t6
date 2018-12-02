package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.util.StringConverter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private DatePicker datumDatePicker;
    @FXML
    private ChoiceBox<String> mjestoChoiceBox;

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

    //message
    @FXML
    private Label messageLabel;

    private String DD =null, MM = null, GGG = null, RR = null, BBB = null, K = null;
    private Integer A = null, B = null, C = null, D = null, E = null, F = null, G = null, H = null, I = null, J = null, L = null, M = null, N = null;
    private String datum = null;
    private String mjesto = "17";
    private final Integer MUSKI = 000, ZENSKI = 999;


    public Boolean validirajImePrezime(String string){
        string.trim();
        if(string == null || string.length() > 20 || string.length() == 0 || string.contains(" ")) return false;
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) >= 'A' && string.charAt(i) <= 'Z' || string.charAt(i) >= 'a' && string.charAt(i) <= 'z') return true;
        }
        return false;
    }

    public Boolean validirajMail(String mail) {
        mail.trim();
        if(mail.endsWith("@hotmail.com") || mail.endsWith("@outlook.com")
                || mail.endsWith("@gmail.com") || mail.endsWith("@etf.unsa.ba")) return true;
        return false;
    }

    public Boolean validirajIndex(String index) {
        if (index.length() == 0 || index.length() > 5) return false;
        for (int i = 0; i < index.length(); i++) {
            if (!Character.isDigit(index.charAt(i))) return false;
        }
        return true;
    }

    public Boolean validirajJMBG(String jmbg){
        if(jmbg == null || jmbg.length() != 13) return false;
        DD = jmbg.substring(0, 2);
        A = Integer.parseInt(DD.substring(0,1));
        B = Integer.parseInt(DD.substring(1,2));

        MM = jmbg.substring(2, 4);
        C = Integer.parseInt(MM.substring(0,1));
        D = Integer.parseInt(MM.substring(1,2));

        GGG = jmbg.substring(4,7);
        E = Integer.parseInt(GGG.substring(0,1));
        F = Integer.parseInt(GGG.substring(1,2));
        G = Integer.parseInt(GGG.substring(2,3));

        RR = jmbg.substring(7, 9);
        H = Integer.parseInt(RR.substring(0,1));
        I = Integer.parseInt(RR.substring(1,2));

        BBB = jmbg.substring(9,12);
        J = Integer.parseInt(BBB.substring(0,1));
        L = Integer.parseInt(BBB.substring(1,2));
        M = Integer.parseInt(BBB.substring(2,3));

        K = jmbg.substring(12, 13);
        N = Integer.parseInt(K);
        if(datum == null || datum.length() != 8) return false;
        if(datum.substring(0,2).equals(DD) && datum.substring(2,4).equals(MM) && datum.substring(5, 8).equals(GGG) // provjra maticnog s datumom
            && mjesto != null && mjesto.equals(RR)  // provjera maticnog s mjestom
            && (Integer.parseInt(BBB) >= MUSKI && Integer.parseInt(BBB) <=ZENSKI) // provjera maticnog s spolom
            ){
                Integer kontrolnaCifra = (7*(A+G) + 6*(B+H) + 5*(C+I) + 4*(D+J) + 3*(E+L) + 2*(F+M));
                kontrolnaCifra = 11 - Math.floorMod(kontrolnaCifra, 11);
                if(kontrolnaCifra.equals(N)) return true;
        }
        return false;

    }

    private void validirajMaticni(String newValue){
        if(validirajJMBG(newValue)){
            jmbgTextField.getStyleClass().removeAll("poljeNijeIspravno");
            jmbgTextField.getStyleClass().add("poljeIspravno");
        } else {
            jmbgTextField.getStyleClass().removeAll("poljeIspravno");
            jmbgTextField.getStyleClass().add("poljeNijeIspravno");
            messageLabel.setText("Maticni broj se ne poklapa sa ostalim podacima!");
        }

    }

    public Boolean validirajDatum(LocalDate date){
        if(date == null) return false;
        if(date.getYear() < LocalDate.now().getYear() || (date.getYear() == LocalDate.now().getYear()
                && date.getDayOfMonth() == LocalDate.now().getDayOfMonth() && date.getMonth() == LocalDate.now().getMonth()))
            return true;
        return false;
    }

    public void postaviMjesto(String m){
        switch (m){
            case "Banja Luka":
                mjesto = "10";
                break;
            case "Bihac":
                mjesto = "11";
                break;
            case "Doboj":
                mjesto = "12";
                break;
            case "Gorazde":
                mjesto = "13";
                break;
            case "Livno":
                mjesto = "14";
                break;
            case "Mostar":
                mjesto = "15";
                break;
            case "Prijedor":
                mjesto = "16";
                break;
            case "Sarajevo":
                mjesto = "17";
                break;
            case "Tuzla":
                mjesto = "18";
                break;
            case "Zenica":
                mjesto = "19";
                break;
        }
    }

    @FXML
    public void initialize(){
        mjestoChoiceBox.setValue("Sarajevo");

        imeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validirajImePrezime(newValue)){
                    imeTextField.getStyleClass().removeAll("poljeNijeIspravno");
                    imeTextField.getStyleClass().add("poljeIspravno");
                } else{
                    imeTextField.getStyleClass().removeAll("poljeIspravno");
                    imeTextField.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });

        prezimeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validirajImePrezime(newValue)){
                    prezimeTextField.getStyleClass().removeAll("poljeNijeIspravno");
                    prezimeTextField.getStyleClass().add("poljeIspravno");
                } else{
                    prezimeTextField.getStyleClass().removeAll("poljeIspravno");
                    prezimeTextField.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });

        emailAdresaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validirajMail(newValue)){
                    emailAdresaTextField.getStyleClass().removeAll("poljeNijeIspravno");
                    emailAdresaTextField.getStyleClass().add("poljeIspravno");
                } else{
                    emailAdresaTextField.getStyleClass().removeAll("poljeIspravno");
                    emailAdresaTextField.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });

        brojIndexaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(validirajIndex(newValue)){
                    brojIndexaTextField.getStyleClass().removeAll("poljeNijeIspravno");
                    brojIndexaTextField.getStyleClass().add("poljeIspravno");
                } else{
                    brojIndexaTextField.getStyleClass().removeAll("poljeIspravno");
                    brojIndexaTextField.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });

        jmbgTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                messageLabel.setText("");
                validirajMaticni(newValue);
            }
        });

        datumDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                messageLabel.setText("");
                if(newValue != null){
                    String d = newValue.toString();
                    datum = d.substring(8, 10) + d.substring(5,7) + d.substring(0,4);
                }
                if(!validirajDatum(newValue)){
                    messageLabel.setText("Datum nije ispravan!");
                }
                else validirajMaticni(jmbgTextField.textProperty().getValue());
            }
        });

        mjestoChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                messageLabel.setText("");
               postaviMjesto(newValue);
               validirajMaticni(jmbgTextField.textProperty().getValue());
            }
        });

    }

}
