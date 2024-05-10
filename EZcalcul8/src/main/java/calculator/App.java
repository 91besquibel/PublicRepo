package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Description: This java application is a terminal-based calculator.
 * Its capabilities and functionality are listed in the readme.txt.
 * Publisher: Zachary Esquibel-Crostic
 * GitHub: https://github.com/91besquibel/
 * LinkedIn: 
 */

/**
 * This class represents a terminal-based calculator application.
 * It provides various functionalities such as performing calculations,
 * viewing history, exporting history, and exiting the program.
 */
public class App {

    private static Scanner input;
    // History Storage
    static ArrayList<String> sesHist = new ArrayList<String>();

    /**
     * Initialization method that starts the calculator.
     */
    public static void main(String[] args) {
        input = new Scanner(System.in);
        // Introduction
        System.out.println();
        System.out.println("Welcome to My EZcalcul8!");
        System.out.println();
        // How it works statement
        explanation();
    }

    /**
     * Starts the menu selection process allowing the user to select a function.
     */
    public static void menu() {
        // Scanner for user input
        String selection;
        // while loop for user input
        while (true) {
            // Header for menu
            System.out.println("Menu");
            // Body for the menu
            print_Menu();
            // User input
            System.out.println("Please enter a selection:");
            selection = input.nextLine();
            System.out.println();
            // if statement for user input
            // switch statement for user input
            switch (selection.toLowerCase()) {
                case "1":
                case "new calculation":
                    new_Calculation();
                    return; // Exit loop after valid selection
                case "2":
                case "view history":
                    view_History();
                    return; // Exit loop after valid selection
                case "3":
                case "export history":
                    export_History();
                    return; // Exit loop after valid selection
                case "4":
                case "exit program":
                    exit_Program();
                    return; // Exit loop after valid selection
                default:
                    System.out.println("Invalid selection please try again.");
            }
        }
    }
    /**
     * Helper method that displays the possible menu selections.
     */
    public static void print_Menu() {
        // Prints the menu body
        System.out.println(
            "1. New Calculation" +
            "\n2. View History" +
            "\n3. Export History" +
            "\n4. Exit Program");
            System.out.println();
    }

    /**
     * Begins and Ends the calculation function of the program.
     */
    public static void new_Calculation() {
        System.out.println("Please enter a new equation:");
        // User input
        String equation = input.nextLine();
        // Add the equation to the history
        sesHist.add(equation);
        // Send equation to Solve class
        String answer = Solve.eq(equation); // Call the eq method correctly
        // Add the answer to the session history
        sesHist.add(answer);
        // Print the answer
        System.out.println("The answer is: " + answer);
        // Close the scanner
        menu();
    }

    /**
     * Allows user to view the strings stored in history.
     */
    public static void view_History() {
        //for loop prints each item in sesHist
        for(String s : sesHist){
            System.out.println(s);
        }
        //gives the user the menu after printing the history
        menu();
    }

    /**
     * Sends session history to a text document.
     */
    public static void export_History() {
        try{
            System.out.println("Please enter a file name. We recommend using Date and Time.");
            String fileName = input.nextLine().trim();
            System.out.println("Please enter a file location: Example 'C:\\Users\\MyName\\'");
            String fileLocation = input.nextLine().trim();

            //Checks if the user input answers are empty if so return to menu();
            if(fileName.isEmpty()||fileLocation.isEmpty()){
                System.out.println("File name or location cannot be empty.");
                return;
            }
            //Assigns the user input as a file in java
            File file = new File(fileLocation,fileName);
            //Checks if the file allready exists
            if(file.exists()){
                System.out.println("File already exists.");
                return;
            }
            //Creates the file in the location provided by user input
            if ( file.createNewFile()){
                System.out.println("File created: "+file.getAbsolutePath());
            } else {
                System.out.println("Failed to create file.");
                return;
            }
            //Creates a file writer object
            FileWriter writer = new FileWriter(file);
            //Writes the session history to the .txt 
            for(String historyEntry : sesHist){
                writer.write(historyEntry+ "\n");
            }
            writer.close();
            System.out.println("Session history saved to "+ file.getAbsolutePath());
        } catch (IOException e){
            System.out.println("An error occurred while creating the file..");
            e.printStackTrace();
        }
        menu();
    }

    public static void import_History() throws IOException{
        System.out.println("Please enter the file path: Example C:\\Users\\MyName\\FileName.txt");
        System.out.println();
        String location = input.nextLine().trim();
        Path filePath = Paths.get(location);
        try{
            if(Files.exists(filePath)){
                try{
                    List<String> lines = Files.readAllLines(filePath);
                    sesHist = new ArrayList<>(lines);
                }catch(IOException e){
                    System.err.println("Could not read file in.");
                    System.out.println();
                    e.printStackTrace();
                }
            }else{
                System.out.println("File does not exist according to path.");
                System.out.println();
            }
        }finally{
            input.close();
        }
        menu();
    }

    /**
     * Ends the session.
     */
    public static void exit_Program() {
        // Exit program warning
        System.out.println("Warning! "+
        "\nExiting without saving will result in a loss of session data. "+
        "\nDo you wish to continue? Yes/No: ");
        // User input 
        String answer = input.nextLine();
        // User input response loop
        while(true){
            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")){
                System.out.println("Thank you for using My EZcalcul8");
                System.out.println();
                input.close();
                System.exit(0);
            }else if(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")){
                break;
            }else{
                System.out.println("Invalid Selection please try again");
                System.out.println();
            }
        }
        // Takes user back to menu if no is selected 
        menu();
        // Scanner Close
    }

    /**
     * Explanation of how to use the calculator.
     */
    public static void explanation() {
        System.out.println(
            "This calculator operations:"+
            "\nAddition(+), Subtraction(-), Multiplication(*), Division(/), and Exponents(^)"+
            "\n " +
            "\nConstructing an expression: "+
            "\nAn equation is processed by finding encapsulated operations."+
            "\nWhen using an exponent encapsulate the operand and operators like so (x^((y/z)*a)) "+
            "\nWhen using multiplication make sure to use *. "+
            "\nIf you do not and your equation looks like this ((9/3)7). "+
            "\nIt will not be read properly. "+
            "\nTo avoid this be sure to structure your expression like so ((9/3)*7). "+
            "\n "
        );
        menu();
    }
}
