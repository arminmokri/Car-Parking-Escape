/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_parking_escape;

/**
 *
 * @author Armin
 */
public class Move {

    private int id;
    private int move;

    public Move(int id, int move) {
        this.id = id;
        this.move = move;
    }

    @Override
    public boolean equals(Object obj) {
        Move move1 = (Move) obj;
        return move1.getId() == getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return "ID=" + id + " Move=" + move;
    }

}
