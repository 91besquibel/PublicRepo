package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class provides methods to solve mathematical equations.
 */
public class Solve {

    // Declare ArrayLists to store equation elements and temporary elements
    static ArrayList<String> equationList = new ArrayList<String>();
    static ArrayList<String> tempList = new ArrayList<String>();

    /**
     * This method solves the given equation.
     * It parses the equation, evaluates expressions within parentheses,
     * and then performs operations in order of precedence.
     *
     * @param equation The mathematical equation to be solved.
     * @return The solution of the equation.
     */
    public static String eq(String equation){
        if (equation == null || equation.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty equation");
        }
        equationList.clear();
        tempList.clear();
        // Declare and initialize solution
        String solution = " ";
        // Counter for start of element replacement
        int startCount = 0;
        // Counter for end of element replacement
        int stopCount = 0;
        // Split the equation into array elements using regex
        String[] equationArray = equation.split("(?<=[-+*/^()])|(?=[-+*/^()])");
        // Convert the array to a list and add it to equationList
        equationList.addAll(Arrays.asList(equationArray));
        // Loop until no parentheses detected
        while (equationList.contains("(")) {
            // For loop to clear out parentheses until none left
            for (int i = 0; i < equationList.size(); i++) {
                String s = equationList.get(i);
                if (s.equals("(")) {
                    // Clear tempList
                    tempList.clear();
                    // Set start counter to index
                    startCount = i;
                    // Copy to tempList
                    tempList.add(s);
                } else if (s.equals(")")) {
                    // Set end counter to index
                    stopCount = i;
                    // Copy to tempList
                    tempList.add(s);
                    // Call opsChecker()
                    solution = opsChecker();
                    // Replace inner expression with its result in the equationList
                    equationList.set(startCount, solution);
                    // Remove elements from startCount+1 to stopCount
                    equationList.subList(startCount + 1, stopCount + 1).clear();
                    // Break out of the loop as the list has been modified
                    break;
                } else if (startCount != -1) {
                    // If inside parentheses, add the element to tempList
                    tempList.add(s);
                }
            }
        }
        // Evaluate the final expression
        solution = opsChecker();
        solveFinal();
        // Update the solution with the final result
        solution = equationList.get(0);
        return solution;
    }

    /**
     * This method checks and performs operations within parentheses.
     *
     * @return The solution of the operations within parentheses.
     */
    public static String opsChecker(){
        // Initialize Solution
        String solution = "";
        // Remove the parentheses
        tempList.remove("(");
        tempList.remove(")");
        // Remove empty strings from tempList
        tempList.removeIf(String::isEmpty);
        // Check the number of integers
        if(tempList.size()==3){
            // If two integers
            solution = singleOp();
        }else{
            // If more than two integers
           solution = multiOp();
        }
        return solution;
    }

    /**
     * This method performs a single Op.
     *
     * @return The solution of the single Op.
     */
    public static String singleOp(){
        // Initialize Solution
        String solution = "";
        // Convert strings to integers
        double a = Double.valueOf(tempList.get(0));
        double b = Double.valueOf(tempList.get(2));
        // Perform operation
        if(tempList.contains("+")){
            solution = String.valueOf(Op.add(a , b));
        }else if(tempList.contains("-")){
            solution = String.valueOf(Op.sub(a , b));
        }else if(tempList.contains("*")){
            solution = String.valueOf(Op.mul(a , b));
        }else if(tempList.contains("/")){
            solution = String.valueOf(Op.div(a , b));
        }else if(tempList.contains("^")){
            solution = String.valueOf(Math.pow(a , b));
        }else{
            // Return error code
            throw new IllegalArgumentException("Invalid operation");
        }
        // Return the solution
        return solution;
    }

    /**
     * This method performs multiple operations.
     *
     * @return The solution of the multiple operations.
     */
    public static String multiOp() {
        String solution = "";
        // Array of operators in order of precedence
        char[] operators = {'^', '*', '/', '+', '-'};
        
        for (char op : operators) {
            // Iterate through operators
            while (tempList.contains(Character.toString(op))) {
                int index = tempList.indexOf(Character.toString(op));
                double a = Double.valueOf(tempList.get(index - 1));
                double b = Double.valueOf(tempList.get(index + 1));
    
                switch (op) {
                    case '^':
                        solution = String.valueOf(Math.pow(a, b));
                        break;
                    case '*':
                        solution = String.valueOf(Op.mul(a, b));
                        break;
                    case '/':
                        if(b!=0){
                            solution = String.valueOf(Op.div(a, b));
                        }else{
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        break;
                    case '+':
                        solution = String.valueOf(Op.add(a, b));
                        break;
                    case '-':
                        solution = String.valueOf(Op.sub(a, b));
                        break;
                }
    
                // Replace operands and operator with the result
                tempList.remove(index - 1); // Remove the first operand
                tempList.remove(index - 1); // Remove the operator
                tempList.remove(index - 1); // Remove the second operand
                tempList.add(index - 1, solution); // Replace the operator with the result
            }
        }
    
        return solution;
    }

    /**
     * This method finalizes the solution by performing remaining operations.
     */
    public static void solveFinal() {
        // Debugging: Print equationList before each iteration
        System.out.println("Equation List: " + equationList);

        // Remove empty strings from equationList
        Iterator<String> iterator = equationList.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            if (element.isEmpty()) {
                iterator.remove(); // Remove empty string
            }
        }
        
        // Continue looping until only one element (final result) remains in the equationList
        while (equationList.size() > 1) {
            // Initialize index of the operator to be used for the next operation
            int opIndex = -1;
            double result = 0;

            // Debugging: Print equationList before each iteration
            System.out.println("Equation List: " + equationList);

            // Find the next operator based on precedence
            // Debugging: Print current operators being checked
            System.out.println("Checking for operators...");

            // Check for operators with highest precedence first (^)
            if (equationList.contains("^")) {
                opIndex = equationList.indexOf("^");
                System.out.println("Found '^' operator at index: " + opIndex);
            } 
            // Then check for multiplication and division
            else if (equationList.contains("*") || equationList.contains("/")) {
                // Find the index of the first multiplication or division operator
                int mulIndex = equationList.indexOf("*");
                int divIndex = equationList.indexOf("/");
                // Choose the index of the operator that occurs first
                if (mulIndex != -1 && divIndex != -1) {
                    opIndex = Math.min(mulIndex, divIndex);
                } else if (mulIndex != -1) {
                    opIndex = mulIndex;
                } else {
                    opIndex = divIndex;
                }
                System.out.println("Found '*' or '/' operator at index: " + opIndex);
            }
            // Lastly, handle addition and subtraction
            else if (equationList.contains("+") || equationList.contains("-")) {
                // Find the index of the first addition or subtraction operator
                int addIndex = equationList.indexOf("+");
                int subIndex = equationList.indexOf("-");
                // Choose the index of the operator that occurs first
                if (addIndex != -1 && subIndex != -1) {
                    opIndex = Math.min(addIndex, subIndex);
                } else if (addIndex != -1) {
                    opIndex = addIndex;
                } else {
                    opIndex = subIndex;
                }
                System.out.println("Found '+' or '-' operator at index: " + opIndex);
            }

            // Debugging: Check if an operator is found
            if (opIndex != -1) {
                System.out.println("Operator found at index: " + opIndex);
                // Ensure that the index is within bounds
                if (opIndex - 1 >= 0 && opIndex + 1 < equationList.size()) {
                    // Get the operands for the operation
                    double num1 = Double.parseDouble(equationList.get(opIndex - 1));
                    double num2 = Double.parseDouble(equationList.get(opIndex + 1));
                    System.out.println("Operands: " + num1 + ", " + num2);

                    // Perform the operation based on the operator
                    switch (equationList.get(opIndex)) {
                        case "^":
                            result = Math.pow(num1, num2);
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                throw new ArithmeticException("Cannot divide by zero");
                            }
                            break;
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                    }
                    System.out.println("Result: " + result);
                } else {
                    // Handle the scenario where the index is out of bounds
                    throw new IndexOutOfBoundsException("Index out of bounds");
                }
            }

            // Update the equationList with the result of the operation
            equationList.set(opIndex, String.valueOf(result));
            // Remove the operands used in the operation from the list
            equationList.remove(opIndex + 1);
            equationList.remove(opIndex - 1); 

            // Debugging: Print updated equationList after each iteration
            System.out.println("Updated Equation List: " + equationList);
        }
    }
}
