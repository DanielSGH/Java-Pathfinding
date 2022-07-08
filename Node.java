public class Node {
    public int x, y,
            f, g, h; // f cost, g cost and h cost respectively.
    public boolean isOpen, isClosed;
    public Node parent;

    public Node(){
        this(0, 0, false, false, null);
    }

    public Node(int g, int h, boolean isOpen, boolean isClosed, Node parent){
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.isOpen = isOpen;
        this.isClosed = isClosed;
        this.parent = parent;
    }

    public void printNode(){
        System.out.print("f cost = " + g + " + " + h + " = " + f + "\nisOpen = " + isOpen + "\nisClosed = " + isClosed + "\n\n");
    }
}
