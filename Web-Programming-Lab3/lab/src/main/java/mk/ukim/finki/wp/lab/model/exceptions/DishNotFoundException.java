package mk.ukim.finki.wp.lab.model.exceptions;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Long id) {
        super(String.format("Dish with id %d not found", id));
    }
}