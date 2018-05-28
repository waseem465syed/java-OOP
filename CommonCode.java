package coursework;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 *
 * @author waseem syed
 */
public class CommonCode {

    private static final String UK_DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    private String ukDateAndTime;
    private Object calledBy;
    public final String userName = System.getProperty("user.name");
    public final String appDir = System.getProperty("user.dir");
    public final String os = System.getProperty("os.name");
    public final String fileSeparator = System.getProperty("file.separator");
    public static final String ORDERED_DATE_TIME_FORMAT_NOW = "yyyy=MM-dd HH:mm:ss";
    public static final String UK_DATE_TIME_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
    public static final String US_DATE_TIME_FORMAT_NOW = "MM-dd-yyyy HH:mm:ss";
    public static final String ORDERED_DATE_FORMAT_NOW = "yyyy-MM-dd";

    public static final String US_DATE_FORMAT_NOW = "MM-dd-yyyy";

    CommonCode() {

    }

    String showMessage(JFrame f, String title, String message) {
        // a jframe here isn't strictly necessary, but it makes the example a little more real
        JFrame frame = new JFrame(title);

        // prompt the user to enter their name
        String name = JOptionPane.showInputDialog(frame, message);

        // get the user's input. note that if they press Cancel, 'name' will be null
        System.out.printf("The user's name is '%s'.\n", name);
        return name;
    }

    public ArrayList<String> readTextFile(String fileName) throws IOException {
        ArrayList file = new ArrayList();
        String line;
        if ((fileName == null) || (fileName.equals(""))) {
            System.out.println("No file name specified. ");
        } else {

        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            if (!in.ready()) {
                throw new IOException();
            }
            while ((line = in.readLine()) != null) {
                file.add(line);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
            file.add("File not found");
        }

        return file;
    }

    public void writeTextFile(String fn, ArrayList<String> outputText) throws FileNotFoundException, IOException {
        writeTextFile();

        File fileName = new File(fn);
        Writer output = new BufferedWriter(new FileWriter(fileName));

//        
//        // we could have saved another whole line of code by having
        // Writer output = new BufferedWriter(new FileWriter(new File(fn)));
        try {
            for (int i = 0; i < outputText.size(); i++) {
                output.write(outputText.get(i).toString() + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            output.close();
        }
    }

    CommonCode(ActionListener call) {
        calledBy = call;
        initialiseVariables();
    }

//     
    public String getDateAndTime() {
        String UK_DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
        String ukDateAndTime;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat uksdf = new SimpleDateFormat(UK_DATE_FORMAT_NOW);
        ukDateAndTime = uksdf.format(cal.getTime());

        return ukDateAndTime;
    }

    void writeTextFile() {

    }

    private void initialiseVariables() {

    }

    void writeObjectToFile(Course[] crs) {
        try {

            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"), false);
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(crs);
//			o.writeObject(p2);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        // TODO Auto-generated catch block

    }

    void emptyFile() throws FileNotFoundException, IOException {
        new FileOutputStream("myObjects.txt").close();
    }

    Course[] readObjectFromFile() throws ClassNotFoundException {
        try {

            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            Course[] crc = (Course[]) oi.readObject();

            System.out.println("Readign from File");
            for (Course c : crc) {
                System.out.println(c.getCourseName());
            }

            oi.close();
            fi.close();
            return crc;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        // TODO Auto-generated catch block

        return null;
    }

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }

    }
    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);

    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {

            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }

    }

    public void highlight(JTextComponent textComp, String pattern) {
        removeHighlights(textComp);
        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }

        } catch (Exception e) {

        }

    }

}
