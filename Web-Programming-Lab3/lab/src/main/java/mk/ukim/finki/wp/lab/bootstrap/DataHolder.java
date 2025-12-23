package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
//@AllArgsConstructor
@Component
public class DataHolder {

    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public DataHolder(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }


    public static List<Chef> chefs=null;
    public static List<Dish> dishes=null;

    @PostConstruct
    public void init() {

        if (chefRepository != null) {
            chefs = new ArrayList<>();
            chefs.add(new Chef("Marko", "Markovski", "Chef for 2 years"));
            chefs.add(new Chef("Lidija", "Lilova", "Private chef on a yacht"));
            chefs.add(new Chef("Mona", "Malinova", "Professional chef for 3 years"));
            chefs.add(new Chef("Bobi", "Bobovski", "Chef still in school"));
            chefs.add(new Chef("Maja", "Mirkova", "Restaurant owner in Italy"));
            chefs = chefRepository.saveAll(chefs);
        }

        if (dishRepository != null) {
            dishes = new ArrayList<>();
            Chef marko = chefs.get(0);
            dishes.add(new Dish("1", "Pasta", "Italian", 20, marko));
            dishes.add(new Dish("2", "Orange Chicken", "Chinese", 30, marko));
            dishes.add(new Dish("3", "Tacos", "Mexican", 25, marko));
            dishes.add(new Dish("4", "Tteokbokki", "Korean", 15, marko));
            dishes.add(new Dish("5", "Biryani", "Indian", 40, marko));
            dishRepository.saveAll(dishes);
        }
    }

}
