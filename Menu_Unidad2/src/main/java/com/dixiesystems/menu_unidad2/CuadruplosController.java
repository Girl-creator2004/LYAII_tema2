package com.dixiesystems.menu_unidad2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CuadruplosController {

    // --- Modelo ---
    public static class Cuadruplo {
        private final String op, arg1, arg2, res;
        public Cuadruplo(String op, String arg1, String arg2, String res) {
            this.op = op; this.arg1 = arg1; this.arg2 = arg2; this.res = res;
        }
        public String getOp()   { return op; }
        public String getArg1() { return arg1; }
        public String getArg2() { return arg2; }
        public String getRes()  { return res; }
    }

    private final ObservableList<Cuadruplo> data = FXCollections.observableArrayList();

    // --- Enlaces FXML ---
    @FXML private TextField txtExpresion;
    @FXML private TableView<Cuadruplo> tabla;
    @FXML private TableColumn<Cuadruplo,String> colOp, colArg1, colArg2, colRes;

    // Se ejecuta al cargar el FXML
    @FXML
    private void initialize() {
        // Puedes usar PropertyValueFactory (requiere getters) o lambdas:
        colOp.setCellValueFactory(new PropertyValueFactory<>("op"));
        colArg1.setCellValueFactory(new PropertyValueFactory<>("arg1"));
        colArg2.setCellValueFactory(new PropertyValueFactory<>("arg2"));
        colRes.setCellValueFactory(new PropertyValueFactory<>("res"));

        // Alternativa con lambdas:
        // colOp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOp()));
        // ...

        tabla.setItems(data);
    }

    // Acción del botón "Generar Cuádruplos"
    @FXML
    private void onGenerarClick() {
        data.clear();
        String expresion = txtExpresion.getText() == null ? "" : txtExpresion.getText().trim();

        if (expresion.equalsIgnoreCase("a = b + c * d")) {
            data.add(new Cuadruplo("*", "c", "d", "t1"));
            data.add(new Cuadruplo("+", "b", "t1", "t2"));
            data.add(new Cuadruplo("=", "t2", "-", "a"));
        } else if (expresion.equalsIgnoreCase("a = b + c")) {
            data.add(new Cuadruplo("+", "b", "c", "t1"));
            data.add(new Cuadruplo("=", "t1", "-", "a"));
        } else {
            new Alert(Alert.AlertType.INFORMATION,
                    "Ejemplo no soportado todavía.\nPrueba con 'a = b + c * d' o 'a = b + c'.",
                    ButtonType.OK).showAndWait();
        }
    }
}
