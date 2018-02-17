/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
/**
 *
 * @author hallgato
 */
public class Teszt extends Application {
     ImageView myImageView;
     double orgSceneX, orgSceneY;
     double orgTranslateX, orgTranslateY;
    @Override
    public void start(Stage primaryStage) {
        
        Button btnLoad = new Button("Load");
        btnLoad.setOnAction(btnLoadEventListener);
         
        myImageView = new ImageView();        
        myImageView.setOnMousePressed(imageOnMousePressedEventHandler); //drag and move a kikommentelt cucc
        myImageView.setOnMouseDragged(imageOnMouseDraggedEventHandler);
        VBox rootBox = new VBox();
        rootBox.getChildren().addAll(btnLoad, myImageView);
         
        Scene scene = new Scene(rootBox, 1920, 1080 );
        primaryStage.setTitle("szakdoga_lvl1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
    EventHandler<MouseEvent> imageOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((ImageView)(t.getSource())).getTranslateX(); //castolni kell majd Image Típusba vagy imageview?
            orgTranslateY = ((ImageView)(t.getSource())).getTranslateY();
        }
    };
    EventHandler<MouseEvent> imageOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
             
            ((ImageView)(t.getSource())).setTranslateX(newTranslateX);
            ((ImageView)(t.getSource())).setTranslateY(newTranslateY);
        }
    };
    
    EventHandler<ActionEvent> btnLoadEventListener = new EventHandler<ActionEvent>(){
 
        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
              
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
                       
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                myImageView.setImage(image);
                //Scene scene = new Scene(rootBox, 300, 300);
            } catch (IOException ex) {
                Logger.getLogger(Teszt.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        }
    };
}
    
    

    

