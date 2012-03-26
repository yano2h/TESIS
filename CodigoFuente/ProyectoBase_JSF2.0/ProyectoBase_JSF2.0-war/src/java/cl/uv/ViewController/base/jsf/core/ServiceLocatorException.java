package cl.uv.ViewController.base.jsf.core;

public class ServiceLocatorException extends Exception {

    protected Throwable causa;

    public ServiceLocatorException() {
        super();
    }

    public ServiceLocatorException(Throwable causa) {
        super(causa);
    }

    public ServiceLocatorException(String message) {
        super(message);
    }

    public ServiceLocatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
