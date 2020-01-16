/*
 * Copyright (C) 2016 Orville Nordström
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package se.devnordstrom.mathattack.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Orville Nordström
 */
public class Util {
    
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm";
    
    /**
     * @param question
     * @return 
     */
    public static String promptInput(String question) {
        return JOptionPane.showInputDialog(question);
    }
    
    /**
     * Shows an Information message using JOptionPane.showMessageDialog.
     * @param text
     * @param title 
     */
    public static void showMessage(String text, String title) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showErrorMesage(String text, String title) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Object unserialize(File file) throws IOException, ClassNotFoundException {
        
        FileInputStream fileInputStream = new FileInputStream(file);

        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object object = objectInputStream.readObject();
        
        fileInputStream.close();
        
        objectInputStream.close();
        
        return object;
        
    }
    
    /**
     * 
     * @param file
     * @param object
     * @throws IOException 
     */
    public static void serializeObject(File file, Object object) throws IOException {
        
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        
        objectOutputStream.writeObject(object);
        
        fileOutputStream.close();
        
        objectOutputStream.close();
        
    }
    
    /**
     * 
     * @param date
     * @return 
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(date);
    }
    
}
