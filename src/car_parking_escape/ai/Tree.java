/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_parking_escape.ai;

import java.util.ArrayList;
import car_parking_escape.Move;
import car_parking_escape.Map;
import car_parking_escape.Vehicle;

/**
 *
 * @author Armin
 */
public class Tree {

    private Node root;
    private int numberOfCirculation;
    private ArrayList<Node> parents;
    private ArrayList<Node> leafs;
    private Node last;

    public Tree(Map map) {
        this.root = new Node(null, map, null, 0);
        this.numberOfCirculation = 0;
        this.parents = new ArrayList<>();
        this.leafs = new ArrayList<>();
        this.last = null;
    }

    public void start() {
        this.AStarSearch(root);
    }

    private void AStarSearch(Node node) {
        numberOfCirculation++;
        Map map = node.getMap();
        if (map.canGo()) {
            last = node;
            return;
        }
        parents.add(node);
        ArrayList<Move> moves = node.Moves();
        for (int i = 0; i < moves.size(); i++) {
            Move move1 = moves.get(i);
            int id = move1.getId();

            Map map1 = (Map) map.clone();
            map1.moveVehicle(move1);
            Vehicle vehicle1 = map1.getVehicles().get(id);
            Node node1 = new Node(node, map1, move1, node.getGn() + 1);

            //remove loops (repeater moves)
            int flag = 0;
            Node temp = node;
            while (temp != null) {
                Map map2 = temp.getMap();
                Move move2 = temp.getMove();
                Vehicle vehicle2 = map2.getVehicles().get(id);
                if (move2 != null && move2.getId() == move1.getId()) {
                    if (vehicle1.getMoveFrom() == vehicle2.getMoveFrom() && vehicle1.getMoveTo() == vehicle2.getMoveTo()) {
                        flag = 1;
                    }
                }
                temp = temp.getParent();
            }

            if (flag == 0) {
                int r = Search(map1);//remove equals and likes
                if (r == -1) {
                    leafs.add(node1);
                    //node.addChild(node1);
                } else if (node1.getGn() < leafs.get(r).getGn()) {
                    leafs.set(r, node1);
                    //node.addChild(node1);
                }
            }
        }
        Sort();
        AStarSearch(leafs.remove(0));
    }

    private void Sort() {
        leafs.sort(Node.getComparator());
    }

    private int Search(Map map) {
        for (int i = 0; i < leafs.size(); i++) {
            Map map1 = leafs.get(i).getMap();
            if (map.equals(map1) || map.like(map1)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        Node node = getLast();
        while (node != null) {
            Move move = node.getMove();
            if (move != null) {
                moves.add(0, move);
            }
            node = node.getParent();
        }
        return moves;
    }

    public int getNumberOfCirculation() {
        return numberOfCirculation;
    }

    public Node getRoot() {
        return root;
    }

    public Node getLast() {
        return last;
    }

}
