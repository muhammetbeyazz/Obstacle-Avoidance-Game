import javax.swing.*;

public class Form extends JFrame {

    public Form(){
        initUI();
    }

    private void initUI(){
        Oyun oyun = new Oyun();

        add(oyun);
        setResizable(false);
        setTitle("Araba Oyunu");
        setSize(612,612);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
