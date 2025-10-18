package com.dixiesystems.menu_unidad2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VariablesyConstantesController {

    @FXML
    private TextField expressionInput;

    @FXML
    private TableView<Symbol> tableView;

    @FXML
    private TableColumn<Symbol, String> symbolColumn;

    @FXML
    private TableColumn<Symbol, String> typeColumn;

    @FXML
    private TableColumn<Symbol, String> valueColumn;

    @FXML
    private TextArea codeArea;

    @FXML
    private void handleAnalyze() {
        String expr = expressionInput.getText().trim();
        if(expr.isEmpty()) {
            return;
        }

        // Limpiar tabla y código
        tableView.getItems().clear();
        codeArea.clear();

        // Analizar la expresión (muy simple, solo suma y asignación)
        String[] parts = expr.split("=");
        if(parts.length != 2) {
            codeArea.setText("Expresión no válida");
            return;
        }

        String var = parts[0].trim();
        String rhs = parts[1].trim();

        ObservableList<Symbol> symbols = FXCollections.observableArrayList();

        // La variable de la izquierda
        symbols.add(new Symbol(var, "Variable", "Desconocido"));

        // Analizar el lado derecho
        String[] rhsParts = rhs.split("\\+");
        int tempCount = 1;
        StringBuilder code = new StringBuilder();

        for(String part : rhsParts) {
            part = part.trim();
            if(part.matches("\\d+")) {
                symbols.add(new Symbol(part, "Constante", part));
                code.append("t").append(tempCount).append(" = ").append(part).append("\n");
            } else {
                symbols.add(new Symbol(part, "Variable", "Desconocido"));
                code.append("t").append(tempCount).append(" = ").append(part).append("\n");
            }
            tempCount++;
        }

        // Generar asignación final
        StringBuilder tempVars = new StringBuilder();
        for(int i = 1; i < tempCount; i++) {
            if(i > 1) tempVars.append(" + ");
            tempVars.append("t").append(i);
        }
        code.append(var).append(" = ").append(tempVars).append("\n");

        // Configurar columnas
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.setItems(symbols);
        codeArea.setText(code.toString());
    }

    public static class Symbol {
        private String symbol;
        private String type;
        private String value;

        public Symbol(String symbol, String type, String value) {
            this.symbol = symbol;
            this.type = type;
            this.value = value;
        }

        public String getSymbol() { return symbol; }
        public String getType() { return type; }
        public String getValue() { return value; }
    }
}

