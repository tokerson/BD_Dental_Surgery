package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DentistDialog extends Dialog {

    GridPane gridPane;
    //i == 0 -> createAddDialog, i == 1 -> createEditDialog, i == 2 ->createDeleteDialog
    DentistDialog(int i ){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 0) createAddDialog();
    }

    DentistDialog(int i, ArrayList list){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 1) createEditDialog(list);
        if(i == 2) createDeleteDialog(list);
    }

    private void createAddDialog(){
        this.setTitle("Dodawanie nowego dentysty");
        this.setHeaderText("Podaj dane nowego dentysty");
        TextField name = new TextField();
        TextField surname = new TextField();
        TextField salary = new TextField();
        TextField hireDate = new TextField();
        hireDate.setPromptText("format yyyy-dd-mm");
        TextField phone = new TextField();
        gridPane.add(new Label("Podaj imię:"), 0, 0);
        gridPane.add(new Label("Podaj nazwisko:"), 0, 1);
        gridPane.add(new Label("Podaj zarobki:"), 0, 2);
        gridPane.add(new Label("Podaj date zatrudnienia:"), 0, 3);
        gridPane.add(new Label("Podaj numer telefonu:"), 0, 4);
        gridPane.add(name, 1, 0);
        gridPane.add(surname, 1, 1);
        gridPane.add(salary, 1, 2);
        gridPane.add(hireDate, 1, 3);
        gridPane.add(phone, 1, 4);
        setResultConverter(button->{
            if(button == ButtonType.OK)
                return new Dentist(0,name.getText(),surname.getText(),Integer.parseInt(salary.getText()),hireDate.getText(),phone.getText());
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    private void createDeleteDialog(ArrayList list) {
        this.setTitle("Usuwanie dentysty");
        this.setHeaderText("Wybierz ID dentysty, którego chcesz usunąć");
        ComboBox choiceBox = new ComboBox();
        ObservableList<String> ids = FXCollections.observableArrayList(list);
        choiceBox.setItems(ids);
        gridPane.add(new Label("Wybierz ID dentysty:"),0,0);
        gridPane.add(choiceBox,1,0);
        setResultConverter(button->{
            if(button == ButtonType.OK)
                return Integer.parseInt(choiceBox.getValue().toString());
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    private void createEditDialog(ArrayList list){
        this.setTitle("Edytowanie dentysty");
        this.setHeaderText("Wybierz ID dentysty, którego chcesz edytować");
        ComboBox choiceBox = new ComboBox();
        ObservableList<String> ids = FXCollections.observableArrayList(list);
        choiceBox.setItems(ids);
        ArrayList<TextField> textFields = new ArrayList<>();
        TextField name = new TextField();
        TextField surname = new TextField();
        TextField salary = new TextField();
        TextField phone = new TextField();
        textFields.add(name);
        textFields.add(surname);
        textFields.add(salary);
        textFields.add(phone);
        for(TextField textField : textFields){
            textField.setDisable(true);
            textField.setPromptText("Pole nieobowiązkowe");
        }
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for(TextField textField : textFields){
                textField.setDisable(false);
            }
        });
        gridPane.add(new Label("Wybierz ID:"),0,0);
        gridPane.add(choiceBox,1,0);
        gridPane.add(new Label("Zmień imię:"),0,1);
        gridPane.add(new Label("Zmień nazwisko:"),0,2);
        gridPane.add(new Label("Zmień zarobki:"),0,3);
        gridPane.add(new Label("Zmień numer telefonu:"),0,4);
        gridPane.add(name,1,1);
        gridPane.add(surname,1,2);
        gridPane.add(salary,1,3);
        gridPane.add(phone,1,4);

        setResultConverter(button->{
            if(button == ButtonType.OK) {
                int SALARY;
                if(salary.getText().isEmpty()) SALARY = -1; // this if to not parse nothing to Integer
                else SALARY = Integer.parseInt(salary.getText());
                return new Dentist(Integer.parseInt(choiceBox.getValue().toString()),name.getText(), surname.getText(),SALARY,"" ,phone.getText());
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }
}