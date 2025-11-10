public class Calculator {
    public static double calculate(String op, double a, double b) throws IllegalArgumentException {
        switch (op.toUpperCase()) {
            case "ADD": return a + b;
            case "SUB": return a - b;
            case "MUL": return a * b;
            case "DIV":
                if (b == 0) throw new IllegalArgumentException(Protocol.ERR_DIV_BY_ZERO); //div by zero error
                return a / b;
            default:
                throw new IllegalArgumentException(Protocol.ERR_UNKNOWN_OPERATION); //unknown operation error
        }
    }
}