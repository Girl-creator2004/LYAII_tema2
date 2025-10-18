package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class AsignacionController {

    @FXML
    private TextField variableName; // Nombre de la variable

    @FXML
    private TextField expression1; // Expresión 1

    @FXML
    private TextField expression2; // Expresión 2

    @FXML
    private Button assignButton;

    @FXML
    private Label outputLabel;

    @FXML
    private ListView<String> historyList; // Para mostrar historial de asignaciones

    // Diccionario para almacenar las variables y sus valores
    private final Map<String, String> memory = new HashMap<>();
    private final ObservableList<String> history = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        historyList.setItems(history);
    }

    @FXML
    protected void onAssignClick() {
        String variable = variableName.getText().trim();
        String exp1 = expression1.getText().trim();
        String exp2 = expression2.getText().trim();

        if (variable.isEmpty()) {
            outputLabel.setText("⚠️ Debes escribir un nombre de variable.");
            return;
        }

        // Concatenar ambas expresiones
        String resultado = exp1 + " " + exp2;

        // Guardar en memoria
        memory.put(variable, resultado);

        // Mostrar en la etiqueta
        outputLabel.setText("Asignación: " + variable + " = " + resultado);

        // Agregar al historial
        history.add(variable + " = " + resultado);

        // Limpiar campos
        expression1.clear();
        expression2.clear();
        variableName.clear();
    }
}


