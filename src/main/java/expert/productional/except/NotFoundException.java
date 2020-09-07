package expert.productional.except;

public class NotFoundException extends ProductionalException{
    public NotFoundException(String message) {
        super(message);
    }
}
