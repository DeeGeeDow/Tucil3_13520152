/*
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Image;
import java.awt.Font;

import java.io.*;

import java.util.*;

public class GUI {

    public Tree puzzleTree;
    public PuzzlePanel pnl_puzzle;
    public JTextField textfield;
    public GUI(){
        JFrame frame = new JFrame();
        frame.setTitle("Boom Boom Bakudan 15 Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(420,560);
        
        ImageIcon logo = new ImageIcon("src/logo.png");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(new Color(0x9c6b65));
        
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        layout.layoutContainer(frame.getContentPane());
        
        JLabel label1 = new JLabel();
        label1.setText("Puzzle Location: ");
        label1.setForeground(new Color(0x752c18));
        textfield = new JTextField(20);
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
        
        JPanel pnl_info = new JPanel();
        pnl_info.setLayout(new CardLayout());
        pnl_info.setOpaque(false);

        JButton btn_start = new JButton("Start!");
        btn_start.setBackground(new Color(0x752c18));
        btn_start.setForeground(new Color(0xd5beaa));
        
        JLabel lbl_process = new JLabel("Processing...");
        lbl_process.setVerticalAlignment(JLabel.CENTER);
        lbl_process.setHorizontalAlignment(JLabel.CENTER);
        
        pnl_info.add("start", btn_start);
        pnl_info.add("process", lbl_process);
        
        JPanel pnl_north = new JPanel();
        pnl_north.setLayout(new GridLayout(2,1));
        pnl_north.setOpaque(false);
        pnl_north.add(pnl_browseFolder);
        pnl_north.add(pnl_info);
        
        JPanel pnl_center = new JPanel();
        pnl_center.setOpaque(false);
        
        pnl_puzzle = new PuzzlePanel();
        pnl_center.add(pnl_puzzle);
        btn_start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                File puzzleFile = new File(textfield.getText());
                if(puzzleFile.exists()){
                    CardLayout cl = (CardLayout) pnl_info.getLayout();
                    cl.next(pnl_info);
                    pnl_puzzle.generatePuzzle(textfield.getText());
                    pnl_puzzle.repaint();
                    pnl_puzzle.animateSolution();
                    cl.first(pnl_info);
                }else{
                    JOptionPane.showMessageDialog(frame, "File not found!");
                }
            }
        });

        frame.add(pnl_north, BorderLayout.NORTH);
        frame.add(pnl_center, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new GUI();
    }
}

class PuzzleElement extends JLabel{
    public int[] darkfont = {6,15};

    PuzzleElement(int i){
        if(i == 16){
            this.setSize(50,50);
            this.setBackground(new Color(0xd5beaa));
            this.setOpaque(true);
        }else{
            boolean dark = false;
            for(int df : darkfont) if(i == df) dark = true;
            Color darkFont = new Color(0x6b5f55);
            Color lightFont = new Color(0xd5beaa);
            Border border = BorderFactory.createLineBorder(new Color(0xd5beaa),1);
    
            this.setSize(50,50);
            this.setText("" + i);
            this.setIcon(new ImageIcon(new ImageIcon("src/assets/klee-" + i + ".png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            this.setBackground(new Color(0x752c18));
            this.setForeground(dark ? darkFont : lightFont);
            this.setHorizontalTextPosition(JLabel.CENTER);
            this.setVerticalTextPosition(JLabel.CENTER);
            this.setVerticalAlignment(JLabel.CENTER);
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setFont(new Font("Serif", Font.BOLD, 30));
            this.setBorder(border);
            this.setBounds(0,0,50,50);
            this.setOpaque(true);

        }
    }
}

class PuzzlePanel extends JPanel{
    public JPanel[] pnl_puzzleCard = new JPanel[16];
    public JLabel[] lbl_puzzlePiece = new JLabel[16];
    public Tree puzzleTree;
    public int[][] pieces;

    PuzzlePanel(){
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        for(int i=1; i<=16; i++){
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = (i-1)%4;
            c.gridy = (i-1)/4;
            c.weightx = 0;
            c.weighty = 1;

            pnl_puzzleCard[i-1] = new JPanel();
            pnl_puzzleCard[i-1].setLayout(new CardLayout());
            CardLayout cl = (CardLayout)(pnl_puzzleCard[i-1].getLayout());
            for(int j=1; j<=16; j++){
                pnl_puzzleCard[i-1].add("" + j, new PuzzleElement(i));
            }
            this.add(pnl_puzzleCard[i-1],c);
            cl.show(pnl_puzzleCard[i-1], "" + i);
        }
    }

    public void generateArray(String filepath){
        this.pieces = new int[4][4];
        try{
            FileReader fr = new FileReader(filepath);
            // Declaring loop variable
            int i;
            int tmp=0;
            int x = 0;
            int y = 0;
            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1){
        
                // Print all the content of a file
                if(i >= '0' && i <= '9') {
                    tmp = tmp*10 + (i - '0');
                }
                else if(tmp!=0){
                    System.out.println(x + " " + y + " " + tmp);
                    this.pieces[x][y] = tmp;
                    tmp = 0;
                    y++;
                    if(y==4){
                        x++;
                        y=0;
                    }
                }
            }
            if(tmp!=0){
                System.out.println(x + " " + y + " " + tmp);
                this.pieces[x][y] = tmp;
            }
            System.out.print((char)i);
            fr.close();
        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void generatePuzzle(String filepath){
        generateArray(filepath);
        Node root = new Node(pieces);
        puzzleTree = new Tree(root);
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                CardLayout cl = (CardLayout)(pnl_puzzleCard[4*i+j].getLayout());
                cl.show(pnl_puzzleCard[4*i+j], "" + (4*i+j) );
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    
    public void animateSolution(){
        puzzleTree.start();
        List<Move> solutions = puzzleTree.getSolution();
        for(Move m : solutions){
            System.out.println(m);
            animateMove(m);
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getVoidIdx(){
        int idx = 0;
        while(this.pieces[idx/4][idx%4] != 16){
            idx++;
        }
        return idx;
    }

    public void swapLabel(int x1, int y1, int x2, int y2){

        int temp = pieces[y1][x1];
        pieces[y1][x1] = pieces[y2][x2];
        pieces[y2][x2] = temp;

        System.out.println(this.getComponentCount());
        this.removeAll();
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                GridBagConstraints c = new GridBagConstraints();
                
                c.gridx = j;
                c.gridy = i;
                c.weightx = 0;
                c.weighty = 1;
                
                this.add(new PuzzleElement(pieces[i][j]), c);
            }
        }
        this.repaint();
        this.revalidate();
    }
    public void animateMove(Move m){
        int void_idx = getVoidIdx();
        int voidx = void_idx%4;
        int voidy = void_idx/4;
        if(m == Move.UP){
            swapLabel(voidx, voidy, voidx, voidy-1);
        }else if(m == Move.DOWN){
            swapLabel(voidx, voidy, voidx, voidy+1);
        }else if(m == Move.RIGHT){
            swapLabel(voidx, voidy, voidx+1, voidy);
        }else if(m == Move.LEFT){
            swapLabel(voidx, voidy, voidx-1, voidy+1);
        }
        
    }
}
class GUIbackup {
    
}
*/