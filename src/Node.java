import java.util.*;
import java.lang.Math;

public class Node {
    private List<Node> child;
    private Move move;
    private int[][] puzzle;
    private int n;
    private int depth;
    private int cost;
    private Node parent;
    private boolean isActive;
    private boolean isBounded;

    public Node(int[][] puzzle){
        System.out.println("Generating Root Node...");
        this.child = new ArrayList<Node>();
        this.move = Move.NULL;
        this.n = 4;
        this.depth = 0;
        this.puzzle = puzzle;
        this.cost = this.costCounter();
        this.parent = null;
        this.isActive = false;
        this.isBounded = false;
    }

    public Node(Move m, int n, int d, int[][] puzzle, Node parent){
        System.out.println("Generating Node with depth " + d);
        this.child = new ArrayList<Node>();
        this.move = m;
        this.n = n;
        this.depth = d;
        this.puzzle = new int[n][n];
        for(int i=0; i<n; i++) System.arraycopy(puzzle[i], 0, this.puzzle[i], 0, n);
        this.move(move);
        this.parent = parent;
        this.isActive = false;
        this.isBounded = false;
        System.out.println(getVoidIndex() + " " + getCost());
    }

    public Move getMove(){
        return this.move;
    }

    public int getCell(int idx){
        return this.puzzle[idx/this.n][idx%this.n];
    }

    public int getCell(int x, int y){
        return this.puzzle[x][y];
    }

    public int getCost(){
        return this.cost;
    }

    public List<Node> getChildren(){
        return this.child;
    }

    public boolean possibleChecker(){
        int c = 0;
        for(int i=0; i<n*n; i++){
            if(this.puzzle[i/n][i%n] == this.n*this.n && (i/n + i%n)%2 == 1){
                c++;
            }
            for(int j=i+1; j<n*n; j++){
                if(this.puzzle[i/n][i%n] > this.puzzle[j/n][j%n]){
                    c++;
                }
            }
        }

        return c%2 == 0;
    }

    public boolean getIsActive(){
        return this.isActive;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setIsActive(boolean val){
        this.isActive = val;
    }

    public boolean getIsBounded(){
        return this.isBounded;
    }

    public void setBounded(){
        this.isBounded = true;
    }

    public boolean isSolution(){
        return this.depth == this.cost;
    }
    private int costCounter(){
        int c = this.depth;
        for(int i=0; i<this.n*this.n; i++){
            if(this.puzzle[i/this.n][i%this.n] != this.n*this.n && i+1 != this.puzzle[i/this.n][i%this.n]){
                int num = this.puzzle[i/this.n][i%this.n];
                c += Math.abs(num/n-i/n) + Math.abs(num%n - i%n);
            }
        }
        return c;
    }

    public int getVoidIndex(){
        int idx = 0;
        while(this.puzzle[idx/this.n][idx%this.n] != this.n*this.n){
            idx++;
        }
        return idx;
    }

    private boolean isValidMove(Move move){
        int voidX = getVoidIndex()/n;
        int voidY = getVoidIndex()%n;
        if(move == Move.UP){
            return voidX>0;
        }else if(move == Move.DOWN){
            return voidX<n-1;
        }else if(move == Move.LEFT){
            return voidY>0;
        }else if(move == Move.RIGHT){
            return voidY<this.n-1;
        }else return false;
    }
    
    private void move(Move move){        
        int voidX = getVoidIndex()/n;
        int voidY = getVoidIndex()%n;
        if(move == Move.UP){
            int tmp = this.puzzle[voidX][voidY];
            this.puzzle[voidX][voidY] = this.puzzle[voidX-1][voidY];
            this.puzzle[voidX-1][voidY] = tmp;
            System.out.println(puzzle[voidX][voidY] + " " + puzzle[voidX-1][voidY]);
        }else if (move == Move.DOWN){
            int tmp = this.puzzle[voidX][voidY];
            this.puzzle[voidX][voidY] = this.puzzle[voidX+1][voidY];
            this.puzzle[voidX+1][voidY] = tmp;
            System.out.println(puzzle[voidX][voidY] + " " + puzzle[voidX+1][voidY]);
        }else if (move == Move.LEFT){
            int tmp = this.puzzle[voidX][voidY];
            this.puzzle[voidX][voidY] = this.puzzle[voidX][voidY-1];
            this.puzzle[voidX][voidY-1] = tmp;
            System.out.println(puzzle[voidX][voidY] + " " + puzzle[voidX][voidY-1]);
        }else if (move == Move.RIGHT){
            int tmp = this.puzzle[voidX][voidY];
            this.puzzle[voidX][voidY] = this.puzzle[voidX][voidY+1];
            this.puzzle[voidX][voidY+1] = tmp;
            System.out.println(puzzle[voidX][voidY] + " " + puzzle[voidX][voidY+1]);
        }
        this.cost = this.costCounter();
    }

    private void generateChild(){
        Move[] moveset = Move.values();
        for(Move m : moveset){
            if(isValidMove(m)){
                Node nd = new Node(m, this.n, this.depth+1, this.puzzle, this);
                this.child.add(nd);
            }
        }
    }

    public void expand(){
        System.out.println("Expanding Node...");
        if(this.isActive){
            generateChild();
        }
    }
}
