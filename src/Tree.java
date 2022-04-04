import java.util.*;
import java.lang.Math;
import java.io.*;

public class Tree {
    private Node root;
    private boolean isPossible;
    private PriorityQueue<Node> pq;
    private List<Move> solution;
    private int costUpperBound;
    private int numOfNodesGenerated;
    private long start;
    private long end;

    public Tree(Node root){
        this.root = root;
        this.isPossible = this.root.possibleChecker();
        System.out.println("this is run");
        this.pq = new PriorityQueue<Node>(new NodeComparator());
        this.costUpperBound = Integer.MAX_VALUE;
        this.solution = new ArrayList<Move>();
        this.numOfNodesGenerated = 1;
    }

    public void start(){
        start = System.currentTimeMillis();
        if(isPossible){
            System.out.println("Generating Tree..");
            Node bestLastNode = null;
            pq.add(this.root);
            while(pq.peek() != null){
                Node nodeCheck = pq.poll();
                System.out.print(nodeCheck.getCost() + " ");
                System.out.print(nodeCheck.getMove() + " ");
                System.out.print(nodeCheck.getVoidIndex() + " ");
                if(nodeCheck.isSolution()){
                    this.costUpperBound = Math.min(this.costUpperBound,nodeCheck.getCost());
                    if(bestLastNode == null || bestLastNode.getCost() > nodeCheck.getCost()){
                        bestLastNode = nodeCheck;
                        System.out.println("New bestLastNode!");
                    }
                }else{
                    if(nodeCheck.getCost() > costUpperBound){
                        nodeCheck.setBounded();
                    }else{
                        nodeCheck.setIsActive(true);
                        nodeCheck.expand();
                        for(Node c : nodeCheck.getChildren() ){
                            pq.add(c);
                            numOfNodesGenerated++;
                        }
                        nodeCheck.setIsActive(false);                       
                    }
                }
            }
            
            Node SolutionNode = bestLastNode;
            while(SolutionNode != this.root){
                solution.add(SolutionNode.getMove());
                System.out.println(SolutionNode.getMove() + " added to solution");
                SolutionNode = SolutionNode.getParent();
            }
            Collections.reverse(solution);
        }
        end = System.currentTimeMillis();
    }

    public int getNumOfNodesGenerated(){
        return this.numOfNodesGenerated;
    }
    public Node getRoot(){
        return this.root;
    }

    public List<Move> getSolution(){
        return this.solution;
    } 

    public long getTimeElapsed(){
        return this.end - this.start;
    }
    public static void main(String[] args){
        int[][] intarray = new int[4][4];
        try{
            FileReader fr = new FileReader(args[0]);
        
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

    public void SaveFile(String filepath, String text){

        try{
            File myObj = new File(filepath);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
            PrintWriter out = new PrintWriter(filepath);
            out.println(text);
            out.close();
        }catch(IOException e){
            System.out.println("Error has occured");
        }
    }
}

class NodeComparator implements Comparator<Node> {
    
    public int compare(Node n1, Node n2)
    {
        if(n1.getCost() < n2.getCost()){
            return -1;
        }else if (n1.getCost() > n2.getCost()){
            return 1;
        }
        return 0;
    }
}