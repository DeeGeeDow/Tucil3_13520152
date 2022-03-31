import java.io.*;

public class Main {
    public static void main(String[] args){
        int[][] intarray = new int[4][4];
        try{
            FileReader fr = new FileReader("test\\2.txt");
        
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
                    intarray[x][y] = tmp;
                    tmp = 0;
                    y++;
                    if(y==4){
                        x++;
                        y=0;
                    }
                }
            }
            if(tmp!=0){
                intarray[x][y] = tmp;
            }
            System.out.print((char)i);
            fr.close();

            Node root = new Node(intarray);
            Tree tree = new Tree(root);
            tree.start();

            System.out.print(tree.getSolution());
        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
