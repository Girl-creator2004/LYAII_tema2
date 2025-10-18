package com.dixiesystems.menu_unidad2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Stack;

public class NotacionController {

    @FXML private TextField tfExpresion;
    @FXML private TextField tfPostfija;
    @FXML private TextField tfPrefija;
    @FXML private TextArea taCodigo;

    @FXML
    private void onProcesar(ActionEvent e) {
        String exp = tfExpresion.getText();
        if (exp == null || exp.trim().isEmpty()) {
            alert("Ingresa una expresión.", Alert.AlertType.WARNING);
            return;
        }
        exp = exp.trim();

        // Para las conversiones usamos la parte derecha si hay asignación (a = ...),
        // de lo contrario usamos toda la expresión.
        String derecha = ladoDerechoSiAsignacion(exp);

        tfPostfija.setText(infijaAPostfija(derecha));
        tfPrefija.setText(infijaAPrefija(derecha));

        // Generación de código intermedio (usa asignación si existe).
        taCodigo.setText(generarCodigo(exp));
    }

    @FXML
    private void onLimpiar(ActionEvent e) {
        tfExpresion.clear();
        tfPostfija.clear();
        tfPrefija.clear();
        taCodigo.clear();
        tfExpresion.requestFocus();
    }

    // =================== LÓGICA (antes en NotacionConverter + IntermediateCodeGenerator) ===================

    /** Si hay asignación (a=...), devuelve solo la parte derecha; si no, devuelve la expresión completa. */
    private String ladoDerechoSiAsignacion(String exp) {
        if (exp.contains("=")) {
            String[] partes = exp.split("=", 2);
            return partes[1].trim();
        }
        return exp.trim();
    }

    /** Prioridad de operadores para las conversiones. */
    private int prioridad(char c) {
        switch (c) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
            default:  return -1;
        }
    }

    /** Convierte expresión infija a postfija (shunting-yard simplificado). */
    private String infijaAPostfija(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : exp.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // saca '('
            } else { // operador
                while (!stack.isEmpty() && prioridad(c) <= prioridad(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /** Convierte expresión infija a prefija. */
    private String infijaAPrefija(String exp) {
        StringBuilder input = new StringBuilder(exp).reverse();
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == ')') {
                stack.push(c);
            } else if (c == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // saca ')'
            } else { // operador
                while (!stack.isEmpty() && prioridad(c) < prioridad(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.reverse().toString();
    }

    /** Genera código intermedio estilo tres direcciones (t1 = a op b, etc.). */
    private String generarCodigo(String expOriginal) {
        String varAsignacion = null;
        String expresion = expOriginal;

        if (expOriginal.contains("=")) {
            String[] partes = expOriginal.split("=", 2);
            varAsignacion = partes[0].trim();
            expresion = partes[1].trim();
        }

        String postfija = infijaAPostfija(expresion);
        Stack<String> stack = new Stack<>();
        StringBuilder codigo = new StringBuilder();
        int tempCount = 1;

        for (char c : postfija.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));
            } else { // operador
                if (stack.size() < 2) {
                    return "Error: expresión inválida en código intermedio.\n";
                }
                String op2 = stack.pop();
                String op1 = stack.pop();
                String temp = "t" + tempCount++;
                codigo.append(temp)
                        .append(" = ")
                        .append(op1).append(" ").append(c).append(" ").append(op2)
                        .append("\n");
                stack.push(temp);
            }
        }

        if (varAsignacion != null && !stack.isEmpty()) {
            String ultimaTemp = stack.pop();
            codigo.append(varAsignacion).append(" = ").append(ultimaTemp).append("\n");
        }

        return codigo.toString();
    }

    // =================== util ===================

    private void alert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

