import java.io.*;
import java.util.Arrays;

public class TextOperation  {

    public File file;
   int  textVeri[] = new int[10];
    public TextOperation() throws IOException {
        textOlustur();
    }
    private void textOlustur () throws IOException {
        file = new File("score.txt"); // txt dosyası oluşturduk
        file.exists();  // veriyi kayetmemek için çıkış yapıyoruz bu sayede ynei oyunda eski skoru görebiliriz

        if(!file.exists()){
            file.createNewFile();
        }
    }
    // skoru txt dosyasına yazmak
    public void addText(int gelenLine) throws IOException {
        String s=String.valueOf(gelenLine);
        FileWriter fWriter = new FileWriter(file,true);
        BufferedWriter bWriter = new BufferedWriter(fWriter);
        s+="\n";
        bWriter.write(s);
        bWriter.close();


    }
    private void veriOku(){

    }

    // veriyi göstermek için kullanılan method
    public int[] getText() throws IOException{

        FileReader fReader = new FileReader(file);

        String gelenLine;
        BufferedReader bReader = new BufferedReader(fReader);

        int i =0;
        while((gelenLine = bReader.readLine())!= null){
            textVeri[i] = Integer.parseInt(gelenLine);
            System.out.println("asdsa " + textVeri[i]);
            i++;

        }
        //TEKRAR BAK

        bReader.close();
        sirala();

        return textVeri;
    }
    // txt dosyasında veriyi sıralamak için kullanılan metot
    private   void sirala() throws IOException {
        Arrays.sort(textVeri); // veriyi kücükten büyüğe sıralıyor
        //////////kücükten büyüğe sıralı olan veriyi büyükten küçüğe çeviriyor////////////
        int tut;
        int don = textVeri.length/2;
        for(int i=0; i<don;i++){
            tut = textVeri[i];
            textVeri[i]=textVeri[textVeri.length-i-1];
            textVeri[textVeri.length-i-1] = tut;
            //System.out.println( "i : " + i+" don : " + (textVeri.length-i-1));
        }
        /////////////////////////////////////////////////////////////////////////////////////

        //veriyi sıraladıktan sonra tekrar score texte atıyor
        textOlustur();
        for (int i=0; i<textVeri.length;i++){
            addText(textVeri[i]);
        }
    }
}
