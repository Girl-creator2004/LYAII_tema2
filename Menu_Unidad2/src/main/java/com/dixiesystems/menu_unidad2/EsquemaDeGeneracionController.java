package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.*;

public class EsquemaDeGeneracionController {

    @FXML
    private TextArea inputArea;

    @FXML
    private TextArea outputArea;

    private int tempCounter;

    // ---------------------------
    // Generador de TAC básico
    // ---------------------------
    private String generateTAC(String expr, boolean optimize) {
        tempCounter = 1;
        Map<String, String> subexprMap = new HashMap<>();
        List<String> code = new ArrayList<>();

        // Eliminar espacios y el "x = ..." si existe
        String cleanExpr = expr.replaceAll("\\s+", "");
        String[] parts = cleanExpr.split("=");
        String leftVar = parts.length > 1 ? parts[0] : "x";
        String rightExpr = parts.length > 1 ? parts[1] : cleanExpr;

        String result = buildTAC(rightExpr, code, subexprMap, optimize);
        code.add(leftVar + " = " + result);

        return String.join("\n", code);
    }

    // Construcción recursiva del TAC
    private String buildTAC(String expr, List<String> code,
                            Map<String, String> subexprMap, boolean optimize) {

        // Caso base: número o variable
        if (expr.matches("[a-zA-Z]\\w*|\\d+")) {
            return expr;
        }

        // Manejo de paréntesis
        if (expr.startsWith("(") && expr.endsWith(")")) {
            return buildTAC(expr.substring(1, expr.length() - 1), code, subexprMap, optimize);
        }

        // Buscar operador de menor precedencia (+ o - primero, luego * o /)
        int pos = findMainOperator(expr);
        char op = expr.charAt(pos);

        String left = expr.substring(0, pos);
        String right = expr.substring(pos + 1);

        String leftVar = buildTAC(left, code, subexprMap, optimize);
        String rightVar = buildTAC(right, code, subexprMap, optimize);

        String subexpr = leftVar + " " + op + " " + rightVar;

        // Optimización: reutilizar subexpresiones comunes
        if (optimize && subexprMap.containsKey(subexpr)) {
            return subexprMap.get(subexpr);
        }

        String temp = "t" + (tempCounter++);
        code.add(temp + " = " + subexpr);

        if (optimize) {
            subexprMap.put(subexpr, temp);
        }

        return temp;
    }

    // Encontrar operador principal respetando precedencia
    private int findMainOperator(String expr) {
        int level = 0;
        int pos = -1;
        int minPrecedence = Integer.MAX_VALUE;

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') level++;
            else if (c == ')') level--;
            else if (level == 0 && "+-*/".indexOf(c) >= 0) {
                int prec = (c == '+' || c == '-') ? 1 : 2;
                if (prec <= minPrecedence) {
                    minPrecedence = prec;
                    pos = i;
                }
            }
        }
        return pos;
    }

    // ---------------------------
    // Botones de la interfaz
    // ---------------------------
    @FXML
    private void generateCode() {
        String expr = inputArea.getText().trim();
        if (expr.isEmpty()) {
            outputArea.setText("⚠️ Ingresa una expresión.");
            return;
        }
        outputArea.setText(generateTAC(expr, false));
    }

    @FXML
    private void optimizeCode() {
        String expr = inputArea.getText().trim();
        if (expr.isEmpty()) {
            outputArea.setText("⚠️ Ingresa una expresión.");
            return;
        }
        outputArea.setText(generateTAC(expr, true));
    }
}

