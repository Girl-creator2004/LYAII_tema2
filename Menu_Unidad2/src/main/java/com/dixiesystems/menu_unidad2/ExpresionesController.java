package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.regex.Pattern;
import javafx.application.Platform;

public class ExpresionesController {

    @FXML
    private TextField txtExpresion;

    @FXML
    public void evaluarExpresion() {
        String expresion = txtExpresion.getText().trim();
        String resultado = comprobarExpresion(expresion);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Resultado de Evaluación");
        alerta.setHeaderText("📢 Evaluación de la Expresión");
        alerta.setContentText(resultado);
        alerta.showAndWait();
    }

    @FXML
    public void salirAplicacion() {
        Platform.exit();
    }

    private String comprobarExpresion(String expresion) {
        try {
            String regexGeneral = "([abc01]|\\(|\\)|\\||\\*|\\+|\\?)+";

            if (!Pattern.matches(regexGeneral, expresion)) {
                return "❌ Expresión no válida.\nSolo se permiten: a,b,c,0,1 y | * + ?";
            }

            if (expresion.contains("|")) {
                return "✔ Expresión con UNIÓN detectada: " + expresion;
            } else if (expresion.contains("*")) {
                return "✔ Expresión con CERRADURA DE KLEENE detectada: " + expresion;
            } else if (expresion.contains("+")) {
                return "✔ Expresión con CERRADURA POSITIVA detectada: " + expresion;
            } else if (expresion.contains("?")) {
                return "✔ Expresión con OPERADOR OPCIONAL detectada: " + expresion;
            } else {
                return "✔ Expresión por concatenación válida: " + expresion;
            }

        } catch (Exception e) {
            return "⚠ Error al evaluar: " + e.getMessage();
        }
    }
}

