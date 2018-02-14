package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    @FXML
    Rectangle rectangle;


    // Egyéb változók ------------------------------------
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extensionFilterPNG = new FileChooser.ExtensionFilter(".png","*.png");
    FileChooser.ExtensionFilter extensionFilterJPG = new FileChooser.ExtensionFilter(".jpg","*.jpg");
    FileChooser.ExtensionFilter extensionFilterJPEG = new FileChooser.ExtensionFilter(".jpeg","*.jpeg");

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    double rectorgTranslateX, rectorgTranslateY;

    double smallScaleX, smallScaleY;

    double zoomLocationX, zoomLocationY;

    public void initialize() {
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.RED);
        rectangle.setVisible(false);
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
            rectangle.setVisible(true);
            smallimgview.setFitHeight(mainimgview.getImage().getHeight()/10);
            smallimgview.setFitWidth(mainimgview.getImage().getWidth()/10);
            smallScaleX = smallimgview.getFitWidth()/mainimgview.getImage().getWidth();
            smallScaleY = smallimgview.getFitHeight()/mainimgview.getImage().getHeight();
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
        rectorgTranslateX = rectangle.getTranslateX();
        rectorgTranslateY = rectangle.getTranslateY();
    }

    public void handleMouseDrag(MouseEvent event){
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;
        double rectnewTranslateX = rectorgTranslateX - offsetX*smallScaleX;
        double rectnewTranslateY = rectorgTranslateY - offsetY*smallScaleY;

        ((ImageView)(event.getSource())).setTranslateX(newTranslateX);
        ((ImageView)(event.getSource())).setTranslateY(newTranslateY);
        rectangle.setTranslateX(rectnewTranslateX);
        rectangle.setTranslateY(rectnewTranslateY);
    }

    public void handleScroll(ScrollEvent e){
        if (e.getDeltaY()>0) {
            mainimgview.setScaleX(mainimgview.getScaleX()+0.25*mainimgview.getScaleX());
            mainimgview.setScaleY(mainimgview.getScaleY()+0.25*mainimgview.getScaleY());
            mainimgview.setTranslateX(zoomLocationX);
            mainimgview.setTranslateY(zoomLocationY);
        }
        else{
            mainimgview.setScaleX(mainimgview.getScaleX()-0.25*mainimgview.getScaleX());
            mainimgview.setScaleY(mainimgview.getScaleY()-0.25*mainimgview.getScaleY());
            mainimgview.setTranslateX(zoomLocationX);
            mainimgview.setTranslateY(zoomLocationY);
        }

    }

    public void handleExitbtn(){
        Stage stage = (Stage) exitbtn.getScene().getWindow();
        stage.close();
    }

}
