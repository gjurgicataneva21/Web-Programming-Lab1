package mk.ukim.finki.wp.lab.model.exceptions;

public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException(Long id) {
        super(String.format("Chef with id %d not found", id));
    }
}