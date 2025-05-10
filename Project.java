import java.util.*;
import java.io.*;

public class Project {

    String name = new String();
    Scanner s;

    void showMenu() {
        // Rooms
        String[][] R = new String[7][9];

        // Creating File & Read
        try {
            File f = new File(name + ".txt");
            f.createNewFile();
            @SuppressWarnings("resource")
            Scanner s = new Scanner(f);
            if(!s.hasNextLine()) { // File Empty
                for(int i = 0; i < 7; i++) {
                    for(int j = 0; j < 9; j++) {
                        R[i][j] = "        ";
                    }
                }
            } else {
                int i = 0;
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    String[] words = line.split(",");
                    R[i] = words; i++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error Occurred!");
            e.printStackTrace();
            System.exit(1);
        }

        // Day Based Menu
        s = new Scanner(System.in);
        while (true) {
            boolean flag1 = false;
            System.out.println("\nDay: " + name);
            System.out.println("1. Show Current Schedule");
            System.out.println("2. Edit Schedule");
            System.out.println("3. Go back to Main Menu");
            System.out.println("\n   Enter your option: ");
            int input1 = s.nextInt(); s.nextLine();
            if (input1 == 3) { // Save and Go Back
                try { // Write to File
                    FileWriter w = new FileWriter(name + ".txt");
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (R[i][j] == null) w.write("        ");
                            else w.write(R[i][j]);
                            w.write(",");
                        }
                        if (i < 6) w.write("\n");
                    }
                    w.close();
                } catch (IOException e) {
                    System.out.println("Error Occurred!");
                    e.printStackTrace();
                    System.exit(1);
                }
                flag1 = true;
            }
            else if (input1 == 1) { // Show Schedule
                System.out.println("\t\t\t\t\t\t***SCHEDULE***");
                System.out.println("Room No | Period 1 | Period 2 | Period 3 | Period 4 | Period 5 | Period 6 | Period 7 | Period 8 | Period 9 |\n");

                int[] rooms = {101, 102, 103, 104, 201, 202, 203};
                for (int r = 0; r < 7; r++) {
                    System.out.print(rooms[r] + ":    | ");
                    for (int i = 0; i < 9; i++) {
                        System.out.print((R[r][i] == null ? "        " : R[r][i]) + " | ");
                    }
                    System.out.println();
                }
                System.out.println();
            } else if (input1 == 2) { // Editing Schedule
                System.out.print("Enter Room No: ");
                int room = s.nextInt(); s.nextLine();
                int r = -1; // Room Index
                boolean flag = true;
                switch (room) {
                    case 101:
                    r = 0;
                    break;
                    case 102:
                    r = 1;
                    break;
                    case 103:
                    r = 2;
                    break;
                    case 104:
                    r = 3;
                    break;
                    case 201:
                    r = 4;
                    break;
                    case 202:
                    r = 5;
                    break;
                    case 203:
                    r = 6;
                    break;
                    default: System.out.println("\nInvalid Room Number!"); flag = false;
                }
                if (flag) {
                    System.out.print("Enter Period No: ");
                    int period = s.nextInt(); s.nextLine();
                    if (period == 0 || period > 9) System.out.println("\nInvalid Period Number!");
                    else {
                        if (!R[r][period-1].equals("        ")) {
                            System.out.print("Already Occupied. Overwrite? [Y/N]: ");
                            String res = s.nextLine();
                            if (res.equals("Y")) {
                                System.out.print("Enter the Course Code [Example: CSE 2101]: ");
                                R[r][period-1] = s.nextLine();
                                System.out.println("\nSuccessfully Updated!");
                            } else {
                                System.out.println("\nNothing Updated!");
                            }
                        } else {
                            System.out.print("Enter the Course Code [Example: CSE 2101]: ");
                            R[r][period-1] = s.nextLine();
                            System.out.println("\nSuccessfully Updated!");
                        }
                        
                    }
                }
            } else {
                System.out.println("\nInvalid Option!"); flag1 = true;
            }
            if (flag1) break;
        }
    };

    public static void main(String[] args) {
        Project[] DAY = new Project[6];
        for (int i = 1; i < 6; i++) DAY[i] = new Project();

        Scanner s = new Scanner(System.in);
        while (true) {
            boolean flag = false;
            System.out.println("\n*** Main Menu ***\n");
            System.out.println("Which Day:");
            System.out.println("1. Saturday");
            System.out.println("2. Sunday");
            System.out.println("3. Monday");
            System.out.println("4. Tuesday");
            System.out.println("5. Wednesday");
            System.out.println("6. Exit");
            System.out.print("\n   Enter Your Option: ");
    
            int input = s.nextInt(); s.nextLine();
            switch (input) {
                case 1:
                    DAY[input].name = "SATURDAY";
                    DAY[input].showMenu();
                break;
                case 2:
                    DAY[input].name = "SUNDAY";
                    DAY[input].showMenu();
                break;
                case 3:
                    DAY[input].name = "MONDAY";
                    DAY[input].showMenu();
                break;
                case 4:
                    DAY[input].name = "TUESDAY";
                    DAY[input].showMenu();
                break;
                case 5:
                    DAY[input].name = "WEDNESDAY";
                    DAY[input].showMenu();
                break;
                case 6:
                    flag = true;
                break;
                default: System.out.println("\nInvalid Option!"); flag = true;
            }
            if (flag) break;
        }
        s.close();
    }
}