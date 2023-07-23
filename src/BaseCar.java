import javax.swing.*;
import java.awt.*;

public class BaseCar {
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean vis;
    public Image image;

    public BaseCar(int x, int y) { // COnstructor

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void loadImage(String imageName) { // her bir aracı obje olarak oluşturuyoruz

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimensions() { // araçların boyutlarını aldığımız kısım

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() { // resmi getir
        return image;
    }

    public int getX() { // x eksenini getiriyor
        return x;
    }
    public void setX(int x,int y){ // değeri değiştirmek için
    this.x = x;
    this.y = y;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
