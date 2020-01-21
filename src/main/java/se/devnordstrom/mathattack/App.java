
package se.devnordstrom.mathattack;

import java.io.File;
import java.io.PrintStream;
import se.devnordstrom.mathattack.gui.controller.GuiController;
import se.devnordstrom.mathattack.util.Util;

/**
 *
 * @author Orville Nordström
 */
public class App {
    
    public static final boolean SET_OUTPUT_TO_FILE = false;
        
    public static final String APP_NAME = "Math Attack";
    public static final String LOG_DIR = "log";
    public static final String OUTPUT_FILE = LOG_DIR + File.separator + "output.txt";
    public static final String OUTPUT_ERROR_FILE = LOG_DIR + File.separator + "output-error.txt";
    
    public static final String AUTHOR_NAME = "Orville Nordström";
    
    public static final String HELP_TEXT = "Wellcome to " + APP_NAME + ".\n"
                + "\nThe objective is to solve "
                + "\n math problems before they fall to "
                + "\nthe ground and \"crash\". "
                + "\n"
                + "\nIn order to write the answer make "
                + "\nsure the GUI is in focus and type"
                + "\nthe answer/number and press Enter to"
                + "\n\"fire\" the answer at the questions."
                + "\n"
                + "\nThe problems will "
                + "\nbecome harder and move faster for each "
                + "\nsuccsessive wave."
                + "\n"
                + "\nThere are also bonus questions which will move horizontally "
                + "\nout of the screen and provide more points than normal questions."
                + "\n"
                + "\n"
                + ""
                + "\nAuthor: "+AUTHOR_NAME;
    
    public static void main(String[] args) {
        setOutput();
        
        GuiController guiController = new GuiController();
        guiController.startGui();
        
        System.out.println("");
        System.err.println("");
    }
    
    /**
     * 
     */
    public static void exitApplication() {
        Util.showMessage("Thank you so much for playing " + App.APP_NAME + "."
                + "\nHave a nice day and please play again.", App.APP_NAME);
        System.exit(0);
    }
    
    private static void setOutput()
    {
        try {
            if(SET_OUTPUT_TO_FILE) {
                new File(LOG_DIR).mkdir();
                
                System.setOut(new PrintStream(new File(OUTPUT_FILE)));
                System.setErr(new PrintStream(new File(OUTPUT_ERROR_FILE)));    
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
