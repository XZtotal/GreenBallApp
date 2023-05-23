package GreenBallApp.util;


import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Utils {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static ImageView circularCutout(ImageView image) {
        double originalWidth = image.getFitWidth();
        double originalHeight = image.getFitHeight();
        image.setPreserveRatio(false);

        // Obtener el lado más corto de la imagen
        double minSize = Math.min(originalWidth, originalHeight);

        // Crear una forma circular
        Circle clip = new Circle();
        clip.setCenterX(originalWidth / 2); // Establecer el centro en la mitad del ancho original
        clip.setCenterY(originalHeight / 2); // Establecer el centro en la mitad del alto original
        clip.setRadius(minSize / 2); // Establecer el radio como la mitad del lado más corto

        // Establecer la forma circular como recorte para el ImageView
        image.setClip(clip);
        return image;
    }



}
