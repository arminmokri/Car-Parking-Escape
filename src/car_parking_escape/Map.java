/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_parking_escape;

import java.util.ArrayList;

/**
 *
 * @author Armin
 */
public class Map {

    //
    private int rows;
    private int columns;
    private int vehicleNumbers;
    private ArrayList<Vehicle> vehicles;
    private int[][] matrix;

    public Map(int rows, int columns, int vehicleNumbers) {
        this.rows = rows;
        this.columns = columns;
        this.vehicleNumbers = vehicleNumbers;
        this.vehicles = new ArrayList<>();
        this.matrix = new int[rows][columns];
    }

    public Map(int[][] matrix, int vehicleNumbers) throws Exception {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.vehicleNumbers = vehicleNumbers;
        this.vehicles = new ArrayList<>();
        this.matrix = matrix;
        updateVehicle();
    }

    private void updateVehicle() throws Exception {
        ArrayList<Vehicle> temp_vehicles = new ArrayList<>();
        for (int v = 0; v < this.vehicleNumbers; v++) {
            int x = -1, y = -1;
            for (int i = 0; i < rows && x == -1 && y == -1; i++) {
                for (int j = 0; j < columns && x == -1 && y == -1; j++) {
                    if (this.matrix[i][j] == v) {
                        x = i;
                        y = j;
                    }
                }
            }

            if (x != -1 && y != -1) {
                int l = 1;
                char hv = 'v';

                for (int i = x + 1; i < rows; i++) {
                    if (this.matrix[i][y] == v) {
                        l++;
                        hv = 'v';
                    }
                }

                for (int j = y + 1; j < columns; j++) {
                    if (this.matrix[x][j] == v) {
                        l++;
                        hv = 'h';
                    }
                }
                Vehicle vehicle = new Vehicle(x, y, hv, l);
                temp_vehicles.add(vehicle);
            }
        }

        Map map_temp = new Map(rows, columns, vehicleNumbers);
        for (int i = 0; i < temp_vehicles.size(); i++) {
            map_temp.addVehicle(temp_vehicles.get(i));
        }
        if (this.equals(map_temp.getMatrix())) {
            this.vehicles = temp_vehicles;
        } else {
            throw new Exception("Invalid Map!");
        }

    }

    private void resetMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = -1;
            }
        }
    }

    private void updateMatrix() {
        this.resetMatrix();
        for (int i = 0; i < getVehicleNumbers(); i++) {
            Vehicle vehicle = vehicles.get(i);
            for (int j = 0; j < vehicle.getLenght(); j++) {
                if (vehicle.getHV() == 'h') {
                    matrix[vehicle.getI()][vehicle.getJ() + j] = i;
                } else {
                    matrix[vehicle.getI() + j][vehicle.getJ()] = i;
                }
            }
        }

        for (int i = 0; i < getVehicleNumbers(); i++) {
            int j;
            Vehicle vehicle = vehicles.get(i);
            if (vehicle.getHV() == 'h') {

                j = vehicle.getJ();
                while (j >= 0 && (matrix[vehicle.getI()][j] == i || matrix[vehicle.getI()][j] == -1)) {
                    j--;
                }
                vehicle.setMoveFrom(j + 1);

                j = vehicle.getJ() + vehicle.getLenght() - 1;
                while (j < columns && (matrix[vehicle.getI()][j] == i || matrix[vehicle.getI()][j] == -1)) {
                    j++;
                }
                vehicle.setMoveTo(j - 1);

            } else {
                j = vehicle.getI();
                while (j >= 0 && (matrix[j][vehicle.getJ()] == i || matrix[j][vehicle.getJ()] == -1)) {
                    j--;
                }
                vehicle.setMoveFrom(j + 1);

                j = vehicle.getI() + vehicle.getLenght() - 1;
                while (j < rows && (matrix[j][vehicle.getJ()] == i || matrix[j][vehicle.getJ()] == -1)) {
                    j++;
                }
                vehicle.setMoveTo(j - 1);
            }
        }
    }

    public boolean canGo() {

        Vehicle vehicle = vehicles.get(0);
        for (int i = vehicle.getJ() + vehicle.getLenght(); i < columns; i++) {
            if (matrix[vehicle.getI()][i] != -1) {
                return false;
            }
        }
        return true;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        if (vehicles.size() == getVehicleNumbers()) {
            updateMatrix();
        }
    }

    public void addVehicle(int I, int J, char HV, int L) {
        Vehicle vehicle = new Vehicle(I, J, HV, L);
        addVehicle(vehicle);
    }

    public void moveVehicle(Move move) {
        vehicles.get(move.getId()).Move(move.getMove());
        updateMatrix();
    }

    public boolean like(Object obj) {
        Map map = (Map) obj;
        for (int i = 0; i < getVehicleNumbers(); i++) {
            if (this.vehicles.get(i).getMoveFrom() != map.getVehicles().get(i).getMoveFrom() || this.vehicles.get(i).getMoveTo() != map.getVehicles().get(i).getMoveTo()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object clone() {
        Map map = new Map(rows, columns, vehicleNumbers);
        for (int i = 0; i < getVehicleNumbers(); i++) {
            Vehicle vehicle = (Vehicle) this.vehicles.get(i).clone();
            map.addVehicle(vehicle);
        }
        return map;
    }

    private boolean equals(int[][] matrix) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (this.matrix[i][j] != matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        Map map = (Map) obj;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (this.matrix[i][j] != map.matrix[i][j]) {
                    return false;
                }
            }
        }
        for (int i = 0; i < getVehicleNumbers(); i++) {
            if (!this.vehicles.get(i).equals(map.vehicles.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {

        String string = "   ";

        for (int i = 0; i < columns; i++) {
            string = string + String.format("%2d", i) + " ";
        }
        string = string + "\n";

        for (int i = 0; i < rows; i++) {
            string = string + String.format("%2d", i) + " ";
            for (int j = 0; j < columns; j++) {
                string = string + String.format("%2d", matrix[i][j]) + " ";
            }
            string = string + "\n";
        }
        return string;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getVehicleNumbers() {
        return vehicleNumbers;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public int[][] getMatrix() {
        return matrix;
    }

}
