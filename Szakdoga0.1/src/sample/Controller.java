package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


public class Controller {
    // @FXML változók ------------------------------------
    @FXML
    Button openfilebtn = new Button();
    @FXML
    ImageView mainimgview = new ImageView();
    @FXML
    ImageView smallimgview = new ImageView();
    @FXML
    Button exitbtn = new Button();

    // Egyéb változók ------------------------------------
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter(".png","*.png");
    FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter(".jpg","*.jpg");
    FileChooser.ExtensionFilter extensionFilterJPEG = new FileChooser.ExtensionFilter(".jpeg","*.jpeg");

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    public void initialize() {
        smallimgview.setFitHeight(100);
        smallimgview.setFitWidth(100);
        fileChooser.getExtensionFilters().addAll(extensionFilterJPG, extensionFilterPNG,extensionFilterJPEG);
        fileChooser.setSelectedExtensionFilter(extensionFilterPNG);
        fileChooser.setTitle("Choose your file");
        File initialFile = new File("src");
        fileChooser.setInitialDirectory(initialFile);
    }

    public void handleOpenfilebtn(){
        File pictureFile = fileChooser.showOpenDialog(openfilebtn.getScene().getWindow());
        try {
            Image pictureImg = new Image(pictureFile.toURI().toURL().toString());
            mainimgview.setImage(pictureImg);
            smallimgview.setImage(pictureImg);
        }
        catch (MalformedURLException ex){
            System.err.println("Malformed URL Exception!");
        }
    }

    public void handleMouseClick(MouseEvent event){
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        orgTranslateX = ((ImageView)(event.getSource())).getTranslateX();
        orgTranslateY = ((ImageView)(event.getSource())).getTranslateY();
    }

    public void handleMouseDrag(MouseEvent event){
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        ((ImageView)(event.getSource())).setTranslateX(newTranslateX);
        ((ImageView)(event.getSource())).setTranslateY(newTranslateY);
    }

    public void handleScroll(ScrollEvent e){
        System.out.println(mainimgview.getScaleX());
        if (e.getDeltaY()>0) {
            mainimgview.setScaleX(mainimgview.getScaleX()+0.05);
            mainimgview.setScaleY(mainimgview.getScaleY()+0.05);
        }
        else if (mainimgview.getScaleX()>0.1){
            mainimgview.setScaleX(mainimgview.getScaleX()-0.05);
            mainimgview.setScaleY(mainimgview.getScaleY()-0.05);
        }

    }

    public void handleExitbtn(){
        Stage stage = (Stage) exitbtn.getScene().getWindow();
        stage.close();
    }



}
