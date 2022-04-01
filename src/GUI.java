import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class GUI {

    public GUI(){

        JFrame frame = new JFrame();
        frame.setTitle("Boom Boom Bakudan 15 Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(420,420);
        
        ImageIcon image = new ImageIcon("src\\logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(0x9c6b65));
        
        frame.setLayout(new BorderLayout());
        
        
        JLabel label1 = new JLabel();
        label1.setText("Puzzle Location: ");
        label1.setForeground(new Color(0x752c18));
        JTextField textfield = new JTextField(20);
        JButton btn = new JButton("Browse!");
        btn.setBackground(new Color(0x752c18));
        btn.setForeground(new Color(0xd5beaa));
        btn.setSize(5,5);
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    textfield.setText(file.getAbsolutePath());
                }else{
                    System.out.println("Open command canceled");
                }
            }
        });
        JPanel pnl_browseFolder = new JPanel();
        pnl_browseFolder.setOpaque(false);
        pnl_browseFolder.add(label1);
        pnl_browseFolder.add(textfield);
        pnl_browseFolder.add(btn);

        JPanel pnl_north = new JPanel();
        pnl_north.setOpaque(false);
        pnl_north.add(pnl_browseFolder);
        
        frame.add(pnl_north, BorderLayout.NORTH);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new GUI();
    }
}
