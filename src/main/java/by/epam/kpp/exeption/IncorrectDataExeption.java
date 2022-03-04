package by.epam.kpp.exeption;

public class IncorrectDataExeption extends Exception{

    public IncorrectDataExeption() {
        super();
    }

    public IncorrectDataExeption(String message) {
        super(message);
    }

    public IncorrectDataExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDataExeption(Throwable cause) {
        super(cause);
    }

    protected IncorrectDataExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
