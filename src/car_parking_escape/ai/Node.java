/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_parking_escape.ai;

import java.util.ArrayList;
import java.util.Comparator;
import car_parking_escape.Move;
import car_parking_escape.Map;
import car_parking_escape.Vehicle;

/**
 *
 * @author Armin
 */
public class Node {

    private Node parent;
    private ArrayList<Node> childs;
    private Map map;
    private Move move;
    private int Gn;
    private int Hn;
    //
    private static Comparator<Node> comparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getFn() < o2.getFn()) {
                return -1;
            } else if (o1.getFn() == o2.getFn()) {
                return 0;
            } else {
                return 1;
            }
        }
    };

    public Node(Node parent, Map map, Move move, int Gn) {
        this.parent = parent;
        this.map = map;
        this.move = move;
        this.Gn = Gn;
        this.Hn = calHn();
        this.childs = new ArrayList<>();
    }

    public ArrayList<Move> Moves() {

        ArrayList<Move> moves = new ArrayList<>();

        for (int i = 0; i < map.getVehicleNumbers(); i++) {
            Vehicle vehicle = map.getVehicles().get(i);
            if (vehicle.getHV() == 'h') {

                for (int j = vehicle.getJ() - 1, m = -1; j >= 0 && map.getMatrix()[vehicle.getI()][j] == -1; j--, m--) {
                    //if (move == null || (move != null && move.getId() != i)) {
                    Move move1 = new Move(i, m);
                    moves.add(move1);
                    //}
                }

                for (int j = vehicle.getJ() + vehicle.getLenght(), m = 1; j < map.getColumns() && map.getMatrix()[vehicle.getI()][j] == -1; j++, m++) {
                    //if (move == null || (move != null && move.getId() != i)) {
                    Move move1 = new Move(i, m);
                    moves.add(move1);
                    //}
                }

            } else {

                for (int j = vehicle.getI() - 1, m = -1; j >= 0 && map.getMatrix()[j][vehicle.getJ()] == -1; j--, m--) {
                    //if (move == null || (move != null && move.getId() != i)) {
                    Move move1 = new Move(i, m);
                    moves.add(move1);
                    //}
                }

                for (int j = vehicle.getI() + vehicle.getLenght(), m = 1; j < map.getRows() && map.getMatrix()[j][vehicle.getJ()] == -1; j++, m++) {
                    //if (move == null || (move != null && move.getId() != i)) {
                    Move move1 = new Move(i, m);
                    moves.add(move1);
                    //}
                }

            }
        }

        return moves;
    }

    private int calHn() {
        int Hn_temp = 0;
        int last = 0;
        Vehicle vehicle = map.getVehicles().get(0);
        for (int i = vehicle.getJ() + vehicle.getLenght(); i < map.getColumns(); i++) {
            if (map.getMatrix()[vehicle.getI()][i] != -1 && map.getMatrix()[vehicle.getI()][i] != last) {
                Hn_temp++;
                last = map.getMatrix()[vehicle.getI()][i];
            }
        }
        //
        return Hn_temp;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node node) {
        childs.add(node);
    }

    public boolean isLeaf() {
        return childs.isEmpty();
    }

    public ArrayList<Node> getChilds() {
        return childs;
    }

    public Map getMap() {
        return map;
    }

    public Move getMove() {
        return move;
    }

    public int getFn() {
        return getGn() + getHn();
    }

    public int getGn() {
        return Gn;
    }

    public int getHn() {
        return Hn;
    }

    public static Comparator<Node> getComparator() {
        return comparator;
    }

}
