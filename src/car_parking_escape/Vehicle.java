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
public class Vehicle {

    private int i;
    private int j;
    private char hv;
    private int length;
    private int moveFrom;
    private int moveTo;

    public Vehicle(int i, int j, char hv, int length) {
        this.i = i;
        this.j = j;
        this.hv = hv;
        this.length = length;
    }

    public void Move(Move move) {
        Move(move.getMove());
    }

    public void Move(int move) {
        if (getHV() == 'h') {
            setJ(getJ() + move);
        } else {
            setI(getI() + move);
        }
    }

    @Override
    protected Object clone() {
        Vehicle vehicle = new Vehicle(i, j, hv, length);
        return vehicle;
    }

    @Override
    public boolean equals(Object obj) {
        Vehicle vehicle = (Vehicle) obj;
        return this.getI() == vehicle.getI() && this.getJ() == vehicle.getJ();
    }

    @Override
    public String toString() {
        return "I=" + i + " J=" + j + " HV=" + hv + " l=" + length;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public char getHV() {
        return hv;
    }

    public void setHV(char hv) {
        this.hv = hv;
    }

    public int getLenght() {
        return length;
    }

    public int getMoveFrom() {
        return moveFrom;
    }

    public void setMoveFrom(int moveFrom) {
        this.moveFrom = moveFrom;
    }

    public int getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(int moveTo) {
        this.moveTo = moveTo;
    }
}
