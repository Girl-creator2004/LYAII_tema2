package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalController {
    @FXML
    Stage stage = new Stage();


    @FXML
    protected void onTriplos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/Triplo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Segunda Ventana");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onGenerarClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/Cuadruplos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onGenCodigo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/esquemaDeGeneracion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void handleAnalyze() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/variablesYConstantes-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onExpresiones() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/expresiones-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onNotacionPolaca() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/prefija-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onPostfija() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/postfija-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onInfija() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/infija-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onAsignacion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/asignacion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onInsControl() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/instruccionesControl-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onNotacion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/notacion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void oncodigoP() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalController.class.getResource("/com/dixiesystems/menu_unidad2/CodigoP-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cuádruplos");
        stage.setScene(scene);
        stage.show();
    }
}