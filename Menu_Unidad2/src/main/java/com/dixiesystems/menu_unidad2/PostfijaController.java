package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Stack;

public class PostfijaController {

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
                String postfija = convertirInfijaAPostfija(expresion);
                outputResultado.setText("Postfija: " + postfija);
            } else {
                outputResultado.setText("⚠️ Ingresa una expresión válida.");
            }
        });
    }

    // Algoritmo Shunting-yard simplificado
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
                pila.pop();
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


