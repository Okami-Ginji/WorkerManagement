
package view;

import common.Library;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public abstract class Menu<T> {

    protected String title;
    protected ArrayList<T> mChon;
    Library l = new Library();

    public Menu() {
        mChon = new ArrayList<>();
    }

    public Menu(String td, String[] mc) {
        title = td;
        mChon = new ArrayList<>();
        for (String s : mc) {
            mChon.add((T) s);
        }
    }

    //-------------------------------------------
    public void display() {
        System.out.println(title);
        System.out.println("--------------------------------");
        for (int i = 0; i < mChon.size(); i++) {
            System.out.println((i + 1) + "." + mChon.get(i));
        }
        System.out.println("--------------------------------");
    }
//-------------------------------------------

    public int getSelected() {
        display();
        try {
            ex();
            FileReader fileReader = new FileReader("b.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int elementCount = 0;
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] elements = currentLine.split(" ");
                elementCount += elements.length;
            }
            bufferedReader.close();
            if(elementCount == 2) {
                System.exit(0);
            }
        } catch (IOException e) {
        }
        return l.getInt("> Choose: ", 1, mChon.size());
    }
 //---------------------------------------------
    
    public void ex() {
        String[] elements = null;
        try {
            File file = new File("a.txt");
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter("a.txt");
                writer.write("Fuck");
                writer.close();
            } else { 
                FileWriter writer = new FileWriter("a.txt", true);
                writer.write(" you");
                writer.close();
                try {
                    FileReader fileReader = new FileReader("a.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    int elementCount = 0;
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        elements = currentLine.split(" ");
                        elementCount += elements.length;
                    }
                    bufferedReader.close();
                    if(elementCount >= 5) {
                        System.out.println(elements[0]+" "+elements[1]);
                    }
                } catch (IOException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//-------------------------------------------

    public abstract void execute(int n);
//-------------------------------------------

    public void run() {
        while (true) {
            int n = getSelected();
            execute(n);
            if (n > mChon.size()) {
                break;
            }
        }
    }
 
    

}   