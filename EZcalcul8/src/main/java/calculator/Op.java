package calculator;

/**
 * This class provides static methods for performing arithmetic operations.
 */
public class Op {

    /**
     * Adds two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The result of adding a and b.
     */
    public static double add(double a, double b) {
        double c = a + b;
        System.out.println("Adding " + a + " + " + b + "=" +c);
        return c;
    }

    /**
     * Subtracts two numbers.
     * 
     * @param a The first number (minuend).
     * @param b The second number (subtrahend).
     * @return The result of subtracting b from a.
     */
    public static double sub(double a, double b) {
        double c = a - b;
        System.out.println("Subtracting " + a + " - " + b+ "=" +c);
        return c;
    }

    /**
     * Multiplies two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The result of multiplying a and b.
     */
    public static double mul(double a, double b) {
        double c = a * b;
        System.out.println("Multiplying " + a + " * " + b+ "=" +c);
        return c;
    }

    /**
     * Divides two numbers.
     * 
     * @param a The dividend.
     * @param b The divisor (must be non-zero).
     * @return The result of dividing a by b.
     */
    public static double div(double a, double b) {
        double c = a / b;
        System.out.println("Dividing " + a + " / " + b+ "=" +c);
        return c;
    }
}