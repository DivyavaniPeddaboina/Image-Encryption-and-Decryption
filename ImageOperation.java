import javax.swing.*; 
import java.awt.*;
import java.io.*;

public class ImageOperation
 {
     public static void operate(int key) 
     {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue != JFileChooser.APPROVE_OPTION)
         {
            JOptionPane.showMessageDialog(null, "No file selected!");
            return;
        }
    
    File file = fileChooser.getSelectedFile();
    if (file == null || !file.exists()) {
        JOptionPane.showMessageDialog(null, "Invalid file selection!");
        return;
    }

    try (FileInputStream fis = new FileInputStream(file);
         ByteArrayOutputStream bos = new ByteArrayOutputStream()) 
         {
             byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1)
             {
            for (int i = 0; i < bytesRead; i++)
             {
                buffer[i] ^= key; // XOR operation
             }
            bos.write(buffer, 0, bytesRead);
        }
        
        try (FileOutputStream fos = new FileOutputStream(file))
         {
            fos.write(bos.toByteArray());
         }
        
        JOptionPane.showMessageDialog(null, "Operation completed successfully!");
     }
     catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error processing the file!");
        e.printStackTrace();
    }
}

public static void main(String[] args) {
    JFrame f = new JFrame("Image Operation");
    f.setSize(400, 400);
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Font font = new Font("Roboto", Font.BOLD, 20);
    JButton button = new JButton("Open Image");
    button.setFont(font);
    JTextField textField = new JTextField(10);
    textField.setFont(font);
    
    button.addActionListener(e -> {
        try {
            int key = Integer.parseInt(textField.getText());
            operate(key);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number!");
        }
    });
    
    f.setLayout(new FlowLayout());
    f.add(button);
    f.add(textField);
    f.setVisible(true);
