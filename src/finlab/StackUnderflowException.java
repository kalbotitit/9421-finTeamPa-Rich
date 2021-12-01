package finlab;

public class StackUnderflowException extends StackOverflowError{
    StackUnderflowException(String message){
        super(message);
    }
}
