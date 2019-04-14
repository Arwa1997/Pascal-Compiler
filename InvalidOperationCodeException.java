package compiler;

class InvalidOperationCodeException extends Exception {
    public InvalidOperationCodeException(Statement statement) {
        super("Invalid operation code found: " + statement.operation());
    }
}