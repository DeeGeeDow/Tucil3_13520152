import java.util.*;
import java.lang.Math;

public class Tree {
    private Node root;
    private boolean isPossible;
    private PriorityQueue<Node> pq;
    private List<Move> solution;
    private int costUpperBound;

    public Tree(Node root){
        this.root = root;
        this.isPossible = this.root.possibleChecker();
        this.pq = new PriorityQueue<Node>(new NodeComparator());
        this.costUpperBound = Integer.MAX_VALUE;
        this.solution = new ArrayList<Move>();
    }

    public void start(){
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
    }

    public List<Move> getSolution(){
        return this.solution;
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