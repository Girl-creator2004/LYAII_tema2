package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Stack;

public class PrefijaController {

    @FXML
    private TextField inputExpresion;

    @FXML
    private Button btnConvertir;

    @FXML
    private TextArea outputResultado;

    @FXML
    private void initialize() {
        btnConvertir.setOnAction(e -> {
            String expresion = inputExpresion.getText();
            if (expresion != null && !expresion.isEmpty()) {
                String prefija = convertirInfijaAPrefija(expresion);
                outputResultado.setText("Prefija: " + prefija);
            } else {
                outputResultado.setText("⚠️ Ingresa una expresión válida.");
            }
        });
    }

    // Algoritmo para convertir de infija a prefija
    private String convertirInfijaAPrefija(String infija) {
        // Paso 1: Invertir la expresión infija
        StringBuilder invertida = new StringBuilder();
        for (int i = infija.length() - 1; i >= 0; i--) {
            char c = infija.charAt(i);
            if (c == '(') {
                invertida.append(')');
            } else if (c == ')') {
                invertida.append('(');
            } else {
                invertida.append(c);
            }
        }

        // Paso 2: Convertir la expresión invertida a postfija
        String postfija = convertirInfijaAPostfija(invertida.toString());

        // Paso 3: Invertir la postfija para obtener prefija
        String[] tokens = postfija.split(" ");
        StringBuilder prefija = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            prefija.append(tokens[i]).append(" ");
        }

        return prefija.toString().trim();
    }

    private String convertirInfijaAPostfija(String infija) {
        StringBuilder salida = new StringBuilder();
        Stack<Character> pila = new Stack<>();

        for (char c : infija.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                salida.append(c).append(" ");
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    salida.append(pila.pop()).append(" ");
                }
                if (!pila.isEmpty()) pila.pop();
            } else {
                while (!pila.isEmpty() && precedencia(pila.peek()) >= precedencia(c)) {
                    salida.append(pila.pop()).append(" ");
                }
                pila.push(c);
            }
        }

        while (!pila.isEmpty()) {
            salida.append(pila.pop()).append(" ");
        }

        return salida.toString().trim();
    }

    private int precedencia(char operador) {
        return switch (operador) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }
}

