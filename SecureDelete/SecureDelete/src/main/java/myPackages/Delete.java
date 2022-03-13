package myPackages;

import java.io.File;  
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.Calendar;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class Delete {  
    public static void secureDelete(File file) throws IOException {
        if (file.exists()) {
            Double length = Math.ceil(file.length()/512.0); // tính số sector của file
            SecureRandom random = new SecureRandom();
            RandomAccessFile raf = new RandomAccessFile(file, "rws");
            raf.seek(0);
            raf.getFilePointer();
            byte[] data = new byte[64];
            int pos = 0;
            while (pos < length) {
                random.nextBytes(data);
                raf.write(data);
                pos += data.length;
            }
            raf.setLength(0);
            raf.close(); 
            Path p = Paths.get(file.getAbsolutePath());
            // đổi thuộc tính file
            Calendar c = Calendar.getInstance();
            c.set(1980, Calendar.JANUARY,1);
            Files.setAttribute(p,"creationTime", FileTime.fromMillis(c.getTimeInMillis()));
            Files.setLastModifiedTime(p, FileTime.fromMillis(c.getTimeInMillis()));
            Files.setAttribute(p,"lastAccessTime", FileTime.fromMillis(c.getTimeInMillis()));
            System.out.println(p);
            Files.delete(p);                 
        }
    }
    public static void main(String[] args) throws InstantiationException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        }
        Demo app = new Demo();
        app.show();
    } 
}
