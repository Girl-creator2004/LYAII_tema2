package com.dixiesystems.menu_unidad2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TriploController {

    @FXML
    private TableView<TriploItem> tablaTriplos;

    @FXML
    private TableColumn<TriploItem, String> colOperador;

    @FXML
    private TableColumn<TriploItem, String> colArg1;

    @FXML
    private TableColumn<TriploItem, String> colArg2;

    @FXML
    private TableColumn<TriploItem, String> colExplicacion; // Nueva columna

    @FXML
    private TextField txtOperador;

    @FXML
    private TextField txtArg1;

    @FXML
    private TextField txtArg2;

    private final ObservableList<TriploItem> listaTriplos = FXCollections.observableArrayList();
    private int contadorTemp = 1; // Para generar nombres temporales (t1, t2,...)

    @FXML
    public void initialize() {
        colOperador.setCellValueFactory(cell -> cell.getValue().operadorProperty());
        colArg1.setCellValueFactory(cell -> cell.getValue().arg1Property());
        colArg2.setCellValueFactory(cell -> cell.getValue().arg2Property());
        colExplicacion.setCellValueFactory(cell -> cell.getValue().explicacionProperty());

        tablaTriplos.setItems(listaTriplos);
    }

    @FXML
    private void agregarTriplo() {
        String operador = txtOperador.getText().trim();
        String arg1 = txtArg1.getText().trim();
        String arg2 = txtArg2.getText().trim();

        if (!operador.isEmpty() && !arg1.isEmpty() && !arg2.isEmpty()) {
            // Generamos un nombre temporal para mostrar explicación
            String temp = "t" + contadorTemp++;
            String explicacion = temp + " = " + arg1 + " " + operador + " " + arg2;

            listaTriplos.add(new TriploItem(operador, arg1, arg2, explicacion));

            txtOperador.clear();
            txtArg1.clear();
            txtArg2.clear();
        }
    }

    // Clase interna que representa un Triplo con explicación
    public static class TriploItem {
        private final SimpleStringProperty operador;
        private final SimpleStringProperty arg1;
        private final SimpleStringProperty arg2;
        private final SimpleStringProperty explicacion;

        public TriploItem(String operador, String arg1, String arg2, String explicacion) {
            this.operador = new SimpleStringProperty(operador);
            this.arg1 = new SimpleStringProperty(arg1);
            this.arg2 = new SimpleStringProperty(arg2);
            this.explicacion = new SimpleStringProperty(explicacion);
        }

        public SimpleStringProperty operadorProperty() { return operador; }
        public SimpleStringProperty arg1Property() { return arg1; }
        public SimpleStringProperty arg2Property() { return arg2; }
        public SimpleStringProperty explicacionProperty() { return explicacion; }

        public String getOperador() { return operador.get(); }
        public String getArg1() { return arg1.get(); }
        public String getArg2() { return arg2.get(); }
        public String getExplicacion() { return explicacion.get(); }
    }
}

