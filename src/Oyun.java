import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oyun extends JPanel implements ActionListener {

    private double yolCizgiY = -160;
    private BufferedImage imageYol; // yol resmini yüklemek için obje oluştururuz
    private Timer timer;
    Coin coin;
    private int carX = 160; //main aracın başlangıç konumu
    private int carY = 400;
    public int cani = 3; // can sayısı

    boolean puantamamladi = false;  // Elli puan tamamlanırsa oyun kazandı
    boolean gameEnd;    // 3 canı kullanırsa oyunu kaybeder
    int level = 0;  //leveli gösteren değişken

//    private int yolY = 0;
    public int bekle = 2000;    // 2sn de bir 2 puan eklemek için
    public int bekle2= 15000;   //15 sn de bir 1 level artışı için
    public int score = 0;   //score puanları göstermek için kullanılan değişken
    public int dscore = 2;  //değişim miktarı +2 artmasını sağlar

    int gecen_sure = 0;     //
    private MainCar mainCar;
    private EnemyCar enemyCar;


    TextOperation t1;

    Random random = new Random();
    //int[] carYol = {160,180,200,220,240,300,320,330,360,375};

    public Oyun() {     // constructor
        initOyun();

        try {
            t1 = new TextOperation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initOyun() {

        addKeyListener(new TAdapter());     //klavyeyi dinler
        setFocusable(true);     // klavyeye odaklanmayı sağlar
        coin = new Coin((int)(Math.random()*200+160),random.nextInt(0,600));    // coin nesnesini sürekli farklı noktalarda oluşmasını sağladık

        try {
            imageYol = ImageIO.read(new FileImageInputStream(new File("src//resources//yol.jpg"))); // yol resmini yükledik
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainCar = new MainCar(carX, carY);  // başlangıç konum değerlerini verdik
        enemyCar = new EnemyCar((int)(Math.random()*200+160), -100);    // düşman arabaları başlangıç konum değerleri verdik
        //    carsList = new ArrayList<>();

        timer = new Timer(10, this);
        timer.start();
    }

    private void drawing(Graphics g) {

//        gecen_sure += 5;

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(imageYol, 0, 0, 580, 700, this); // yolu yazdırdık



        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));    // yazı tipi
        g2d.drawString("Puanınız: " + score,  10,550 ); // puan yazdırma kısmı

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));    // font
        g2d.drawString("Kalan Can: " + cani,  10,530 );     // kalan canı yazan kısım

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2d.drawString("Level: " + level,  10,510 );        // leveli yazdıran kısım

        // Yol çizgisi oluşturma

        g2d.setColor(Color.yellow);
        g2d.fillRect(280, (int) yolCizgiY, 15,80);
        yolCizgiY += 0.8;

        g2d.fillRect(280, (int) yolCizgiY + 160, 15,80);

        g2d.fillRect(280,(int) yolCizgiY + 320, 15,80);

        g2d.fillRect(280,(int) yolCizgiY + 480, 15,80);

        g2d.fillRect(280,(int) yolCizgiY + 640, 15,80);
        yolCizgiY += 0.8;

        if (yolCizgiY > 0)
            yolCizgiY = -160;

        // düşman araç çizdirme
        g2d.drawImage(enemyCar.getImage(), enemyCar.getX(),
                enemyCar.getY(), this);

        // kullanıcı araç çizdirme
        g2d.drawImage(mainCar.getImage(), mainCar.getX(),
                mainCar.getY(), this);

        // coin resmini çizfirme
        g2d.drawImage(coin.getCoinImage(),coin.getX(),coin.getY(),this);

        // 15 sn de bir level artması ve araç hızlanması işlemi
        bekle2 -= 10;
        while (bekle2 < 0) {
            System.out.println("Level : " + level);
            bekle2 = 15000;
            level += 1;
            enemyCar.eCarY +=2;
        }
        // 4sn bir 2 scor ekleme kısmı
        bekle -= 10;
        while (bekle < 0) {
            System.out.println("score : " + score);
            bekle = 2000;
            score += dscore;

        }

        // skor 50 üzerinde ise oyunu kazanırsınız
        if (score > 50){
            gameEnd = true;
            puantamamladi= true;
        }


        oyunKontrol(g2d); // oyun sonu kazandın kaybettin metodu

    }

    // coin aldığında puan ekleme
    private boolean carpismaCheckPoint(){
        if (new Rectangle(coin.getX(),coin.getY(),coin.getWidth(),coin.getHeight()).intersects(mainCar.getX(),
                mainCar.getY(),mainCar.getWidth(),mainCar.getHeight())){
            return true;
        }
        return false;
    }
    // çarpışmada yeniden konumlandırma
    public void defaultDegerler(){
        carX = 160;
        carY = 400;
        enemyCar.setX(160,0);
        enemyCar.eCarY = 0;

    }


    public void oyunKontrol(Graphics2D g2d) {
        if (gameEnd) {  // oyun biter
            int width = 612;
            int height = 612;

            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));

            if (puantamamladi == true){
                g2d.drawString("KAZANDINIZ", width / 2 - g2d.getFontMetrics().stringWidth("KAZANDINIZ") / 2, height / 2);
            }else {
                g2d.drawString("ÇARPTINIZ", width / 2 - g2d.getFontMetrics().stringWidth("ÇARPTINIZ") / 2, height / 2);

            }


            // oyun sonu puanı yazdırma
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
            g2d.drawString("Puanınız: " + score, width / 2 - g2d.getFontMetrics().stringWidth("Puanınız:----") / 2, height / 2 + 55);
            timer.stop();



        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawing(g);
    }

    // düşmanları oluşturma metodu
    private void createEnemy() {

        if (enemyCar.getY() <= getHeight()) {
            enemyCar.move();
        } else if (enemyCar.getY() > getHeight()) {
            enemyCar.setVisible(false);
            enemyCar = new EnemyCar((int)(Math.random()*200+160), -100);

        }
    }

    // main aracı hareketi
    private void aracYürü() {

        mainCar.move();
    }

    // araçların çarpışması can azalır ve sol kısımda gösterir
    private boolean carpısmaKontrol() throws IOException {
        if (new Rectangle(mainCar.getX(), mainCar.getY(), mainCar.getWidth(), mainCar.getHeight())
                .intersects(enemyCar.getX(), enemyCar.getY(), enemyCar.getWidth(), enemyCar.getHeight())) {
            cani--;

            if (cani<1){

                t1.addText(score);

                //int[] a = t1.getText();

                gameEnd = true;
            }else {
                defaultDegerler();
            }

            return false;
        }
        gameEnd = false;
        return true;
    }

    // jppanlei extend ettiği için metodu doldururuz
    @Override
    public void actionPerformed(ActionEvent e) {

        if(carpismaCheckPoint()){
            score +=10; // coin alırsa 10 puan skor ekleme
            coin = new Coin((int)(Math.random()*200+160),random.nextInt(0,600));
        }

        try {
            if (!carpısmaKontrol()) {

               // String mesaj = "Bitti";
                //JOptionPane.showMessageDialog(this, mesaj);

                //System.exit(0);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // araç yürüme ve tekrar düşman oluşturma
        aracYürü();
        createEnemy();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        int x = 0;


        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            mainCar.KeyPressed(e);
        // pause olayı
            if(x == 0 && key == KeyEvent.VK_SPACE){
                x=1;
                timer.stop();
            } else if (x==1 && key == KeyEvent.VK_SPACE) {
                x = 0;
                timer.start();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            mainCar.keyReleased(e);
        }
    }
}
