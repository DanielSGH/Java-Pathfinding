import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class BadGuy {
    Image myImage;
    int x=0,y=0;
    boolean hasPath=false;
    Node[][] nodes = new Node[40][40];
    LinkedList<Node> openList = new LinkedList<>();
    Stack<Node> finalPath = new Stack<>();

    public BadGuy( Image i ) {
        myImage=i;
        x = 30;
        y = 10;

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 40; y++) {
                nodes[x][y] = new Node();
                nodes[x][y].x = x;
                nodes[x][y].y = y;
            }
        }
    }

    public void reCalcPath(boolean map[][],int targx, int targy) {
        // TO DO: calculate A* path to targx,targy, taking account of walls defined in map[][]
        openList.clear();
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 40; y++) {
                nodes[x][y] = new Node();
                nodes[x][y].x = x;
                nodes[x][y].y = y;
                if (map[x][y]) {
                    nodes[x][y].isClosed = true;
                    nodes[x][y].isOpen = false;
                }
            }
        }

        Node startNode = nodes[x][y];
        Node targNode = nodes[targx][targy];
        startNode.isOpen = true;
        openList.add(startNode);

        while (!openList.isEmpty()){
            hasPath = false;
            Node current = new Node();
            current.f = 999999999;

            Iterator i = openList.iterator();
            while (i.hasNext()){
                Node currIt = (Node) i.next();
                if (currIt.f < current.f) current = currIt;
            }

            if (current.f != 999999999) {
                openList.remove(current);
                current.isClosed = true;
                if (current == targNode) {
                    hasPath = true;
                    return;
                }

                for (int xx = -1; xx <= 1; xx++) {
                    for (int yy = -1; yy <= 1; yy++) {
                        if (xx != 0 || yy != 0) {
                            int x_plus_xx = Math.floorMod(current.x + xx, 40);
                            int y_plus_yy = Math.floorMod(current.y + yy, 40);
                            System.out.println(nodes[x_plus_xx][y_plus_yy].x + " " + nodes[x_plus_xx][y_plus_yy].y);

                            if (!nodes[x_plus_xx][y_plus_yy].isClosed && !nodes[x_plus_xx][y_plus_yy].isOpen) {
                                nodes[x_plus_xx][y_plus_yy].isOpen = true;
                                openList.add(nodes[x_plus_xx][y_plus_yy]);
                                nodes[x_plus_xx][y_plus_yy].parent = current;

                                int gcost = (int) (Math.sqrt(Math.pow(x_plus_xx - current.x, 2) + Math.pow(y_plus_yy - current.y, 2)) * 10);
                                int hcost = (int) (Math.sqrt(Math.pow(targNode.x - x_plus_xx, 2) + Math.pow(targNode.y - y_plus_yy, 2)) * 10);

                                nodes[x_plus_xx][y_plus_yy].g = gcost;
                                nodes[x_plus_xx][y_plus_yy].h = hcost;
                                nodes[x_plus_xx][y_plus_yy].f = gcost + hcost;
                            }
                        }
                    }
                }
            } else return;
        }
    }

    public void move(boolean map[][],int targx, int targy) {
        if (hasPath) {
            // TO DO: follow A* path, if we have one defined
            Node targetNode = nodes[targx][targy];
            Node parent = targetNode.parent;
            finalPath.push(targetNode);

            while (parent != null) {
                finalPath.push(parent);
                parent = parent.parent;
            }

            Node popped = finalPath.pop();
            popped = finalPath.pop();
            x = popped.x;
            y = popped.y;
        }
        else {
            // no path known, so just do a dumb 'run towards' behaviour
            int newx=x, newy=y;
            if (targx<x)
                newx--;
            else if (targx>x)
                newx++;
            if (targy<y)
                newy--;
            else if (targy>y)
                newy++;
            if (!map[newx][newy]) {
                x=newx;
                y=newy;
            }
        }
        reCalcPath(map, targx, targy);
    }

    public void paint(Graphics g) {
        g.drawImage(myImage, x*20, y*20, null);
    }

}

