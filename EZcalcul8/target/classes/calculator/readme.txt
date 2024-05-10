# Calculator Package

This package provides utility classes for performing arithmetic operations and calculating roots of numbers.  
It was developed after an introductory programming class to help practice programming concepts. 
This is why I did not you any methods or classes that involve containers or algorithms.
I would love input into what parts of my fundamental knowledge base need the most work.

## Classes:
- **Operation:** Provides static methods for addition, subtraction, multiplication, division, and exponentiation.
- **Root:** Provides static methods for calculating the nth root and square root of numbers.
- **Fraction:** Provides static methods for converting decimal numbers to fractions.

### Operation Class:
- **Methods:**
  - `add(double a, double b)`: Adds two numbers.
  - `sub(double a, double b)`: Subtracts one number from another.
  - `mul(double a, double b)`: Multiplies two numbers.
  - `div(double a, double b)`: Divides one number by another.
  - `pow(double a, int b)`: Raises a number to an integer exponent.
  - `pow(double a, double b)`: Raises a number to a decimal exponent.
  
### Root Class:
- **Methods:**
  - `nthRoot(double base, int root)`: Calculates the nth root of a number with default precision.
  - `nthRoot(double base, int root, double precision)`: Calculates the nth root of a number with a specified precision.
  - `sqRoot(double a)`: Calculates the square root of a number using the Newton-Raphson method.

### Fraction Class:
- **Methods:**
  - `decToFrac(double a)`: Converts a decimal number to a fraction.

## Usage:
- Import the desired class from the calculator package into your Java project.
- Use the static methods provided by the imported class to perform arithmetic operations or calculate roots as needed.

## Example (Java):
```java
// To calculate the square root of a number
- double sqrt = Root.sqRoot(25);

## Notes:
- Ensure that all input parameters are within the valid range to avoid unexpected behavior.
- Refer to the Javadoc comments in the source code for detailed information about each method.

## Publisher:
- Zachary Esquibel-Crostic
- LinkedIn: [Zachary Esquibel-Crostic](https://www.linkedin.com/in/zachary-esquibel-crostic-3279a6280)

## GitHub Repository:
- [https://github.com/91besquibel/](https://github.com/91besquibel/)
