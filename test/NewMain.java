/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import car_parking_escape.Map;
import car_parking_escape.Move;
import car_parking_escape.ai.Tree;
import car_parking_escape.Vehicle;

/**
 *
 * @author Armin
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {

            int[][] ii = {
                {1, 1, 1, -1, 3, -1},
                {2, 2, 2, -1, 3, 4},
                {-1, -1, -1, -1, -1, 4},
                {0, 0, 5, -1, -1, -1},
                {-1, -1, 5, 6, 6, 6},
                {-1, -1, 5, -1, 7, 7}

            };

            Map map1 = new Map(ii, 8);
            Tree tree1 = new Tree(map1);
            //tree1.start();
            System.out.println("Map:");
            System.out.println(map1.toString());
            //
            System.out.println("Veh:");
            ArrayList<Vehicle> vehicles1 = map1.getVehicles();
            for (int j = 0; j < vehicles1.size(); j++) {
                System.out.println(vehicles1.get(j).toString());
            }

            //
            System.out.println("Moves:");
            ArrayList<Move> moves1 = tree1.getMoves();
            for (int j = 0; j < moves1.size(); j++) {
                System.out.println("Move: " + moves1.get(j).getId() + " " + moves1.get(j).getMove());
            }

            //
            System.out.println("NumberOfCirculation=" + tree1.getNumberOfCirculation());
            //System.out.println("Gn=" + tree1.getLast().getGn());
            //System.out.println("Hn=" + tree1.getRoot().getHn());

            if (true) {
                return;
            }
            boolean DEBUGMODE = true;

            Scanner scanner = new Scanner(new File("test4.txt"));

            scanner.hasNextLine();
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {

                String[] strings;

                scanner.hasNextLine();
                strings = scanner.nextLine().split(" ");
                int N = Integer.parseInt(strings[0]);
                int M = Integer.parseInt(strings[1]);
                int V = Integer.parseInt(strings[2]);

                Map map = new Map(N, M, V);

                for (int j = 0; j < map.getVehicleNumbers(); j++) {
                    scanner.hasNextLine();
                    strings = scanner.nextLine().split(" ");
                    int I = Integer.parseInt(strings[0]);
                    int J = Integer.parseInt(strings[1]);
                    char HV = strings[2].charAt(0);
                    int L = Integer.parseInt(strings[3]);
                    Vehicle vehicle = new Vehicle(I - 1, J - 1, HV, L);
                    map.addVehicle(vehicle);
                }
                //
                //Node root = new Node(null, map, 0, 0, null);
                Tree tree = new Tree(map);
                tree.start();
                //
                if (DEBUGMODE) {
                    System.out.println("Map:");
                    System.out.println(map.toString());
                    //
                    System.out.println("Veh:");
                    ArrayList<Vehicle> vehicles = map.getVehicles();
                    for (int j = 0; j < vehicles.size(); j++) {
                        System.out.println(vehicles.get(j).toString());
                    }
                    //
                    System.out.println("Moves:");
                    ArrayList<Move> moves = tree.getMoves();
                    for (int j = 0; j < moves.size(); j++) {
                        System.out.println("Move: " + moves.get(j).getId() + " " + moves.get(j).getMove());
                    }
                    //
                    System.out.println("NumberOfCirculation=" + tree.getNumberOfCirculation());
                    System.out.println("Gn=" + tree.getLast().getGn());
                    System.out.println("Hn=" + tree.getRoot().getHn());
                }
                System.out.println("Test #" + (i + 1) + ": " + tree.getLast().getGn());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
