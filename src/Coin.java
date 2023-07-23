import javax.swing.*;
import java.awt.*;

public class Coin {
    private int x;
    private int y;
    public int width;
    public int height;
    Image coinImage;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon("src//resources//coins.png");
        coinImage = ii.getImage();
        getImageDimensions();

    }

    public void getImageDimensions() {

        width = coinImage.getWidth(null);
        height = coinImage.getHeight(null);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(Image coinImage) {
        this.coinImage = coinImage;
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


