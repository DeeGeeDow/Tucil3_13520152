import javax.swing.*;
import java.awt.*;
import java.io.File;

public class test {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        File logofile = new File("Tucil3_13520152/src/logo.png");
        System.out.println(logofile.getAbsolutePath());
        ImageIcon logo = new ImageIcon("src/logo.png");
        frame.setIconImage(logo.getImage());
        frame.setVisible(true);
        frame.setLayout(new GridLayout());
        PuzzleElement pnl = new PuzzleElement(1);
        PuzzleElement pzl = new PuzzleElement(2);
        frame.add(pnl);
        frame.add(pzl);
        frame.revalidate();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        System.out.println(frame.getComponentCount());
        frame.remove(pnl);
        System.out.println(frame.getComponentCount());
        frame.remove(pzl);
        System.out.println(frame.getComponent(0));
        pzl = new PuzzleElement(1);
        pnl = new PuzzleElement(2);
        System.out.println(frame.getComponentCount());
        frame.add(pnl);
        System.out.println(frame.getComponentCount());
        frame.add(pzl);
        frame.revalidate();
        System.out.println(frame.getComponentCount());

        JPanel panel = new JPanel();
        JLabel lbl = new JLabel();
        panel.add(lbl);
        panel.remove(lbl);
        System.out.println(panel.getComponentCount());
    }
}
