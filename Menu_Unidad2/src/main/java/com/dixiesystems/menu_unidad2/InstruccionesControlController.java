package com.dixiesystems.menu_unidad2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InstruccionesControlController {

    @FXML private TextField inputNumero;
    @FXML private TextArea salida;
    @FXML private Label estado;

    // ===== Utilidad secuencial (validación básica) =====
    private Integer leerEnteroOAdvertir() {
        // Estructura SECUENCIAL: pasos en orden
        // 1) leer, 2) validar, 3) parsear, 4) retornar o advertir
        String texto = inputNumero.getText().trim();
        if (texto.isEmpty()) {
            estado.setText("Ingresa un número entero.");
            return null;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            estado.setText("Formato inválido. Ej: 15, -3, 0");
            return null;
        }
    }

    // ===== Selectiva: if/else y switch =====
    @FXML
    private void onClasificar(ActionEvent e) {
        Integer n = leerEnteroOAdvertir();
        if (n == null) return;

        // if/else: signo
        String signo;
        if (n > 0) {
            signo = "positivo";
        } else if (n < 0) {
            signo = "negativo";
        } else {
            signo = "cero";
        }

        // switch: paridad y rango aproximado
        String paridad = (n % 2 == 0) ? "par" : "impar";
        String rango;
        int magnitud = Math.abs(n);
        switch (magnitud / 10) {
            case 0:  rango = "entre -9 y 9"; break;
            case 1:  rango = "entre ±10 y ±19"; break;
            case 2:  rango = "entre ±20 y ±29"; break;
            default: rango = "≥ ±30"; break;
        }

        salida.appendText(String.format(
                "[Clasificar] %d es %s, %s y su magnitud está %s.\n",
                n, signo, paridad, rango
        ));
        estado.setText("Clasificación realizada (if/else + switch).");
    }

    // ===== Repetitiva: for =====
    @FXML
    private void onContarAsc(ActionEvent e) {
        Integer n = leerEnteroOAdvertir();
        if (n == null) return;

        if (n < 1) {
            estado.setText("Para contar ascendente, usa un entero ≥ 1.");
            return;
        }

        salida.appendText("[Contar Ascendente - for] ");
        for (int i = 1; i <= n; i++) {     // Estructura REPETITIVA: for
            salida.appendText(i + (i < n ? ", " : ""));
        }
        salida.appendText("\n");
        estado.setText("Conteo ascendente completado (for).");
    }

    // ===== Repetitiva: while =====
    @FXML
    private void onContarDesc(ActionEvent e) {
        Integer n = leerEnteroOAdvertir();
        if (n == null) return;

        if (n < 1) {
            estado.setText("Para contar descendente, usa un entero ≥ 1.");
            return;
        }

        salida.appendText("[Contar Descendente - while] ");
        int i = n;
        while (i >= 1) {                   // Estructura REPETITIVA: while
            salida.appendText(i + (i > 1 ? ", " : ""));
            i--;
        }
        salida.appendText("\n");
        estado.setText("Conteo descendente completado (while).");
    }

    // ===== Repetitiva: do-while =====
    @FXML
    private void onMultiplosDe3(ActionEvent e) {
        Integer n = leerEnteroOAdvertir();
        if (n == null) return;

        int limite = Math.abs(n);
        int k = 0;
        salida.appendText("[Múltiplos de 3 hasta N - do/while] ");
        // Estructura REPETITIVA: do-while (se ejecuta al menos una vez)
        do {
            salida.appendText((k * 3) + ( (k*3) + 3 <= limite ? ", " : ""));
            k++;
        } while (k * 3 <= limite);
        salida.appendText("\n");
        estado.setText("Múltiplos generados (do-while).");
    }

    @FXML
    private void onLimpiar(ActionEvent e) {
        salida.clear();
        estado.setText("Listo.");
        inputNumero.requestFocus();
    }
}
