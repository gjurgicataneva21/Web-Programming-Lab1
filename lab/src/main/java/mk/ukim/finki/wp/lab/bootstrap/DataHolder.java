package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class DataHolder {

    public static List<Chef> chefs =new ArrayList<>();
    public static List<Dish> dishes=new ArrayList<>();

    @PostConstruct
    public void init() {
       dishes = new ArrayList<>();
       dishes.add(new Dish("1","Pasta","Italian",20));
       dishes.add(new Dish("2","Orange Chicken","Chinese",30));
       dishes.add(new Dish("3","Tacos","Mexican",25));
       dishes.add(new Dish("4","Tteokbokki","Korean",15));
       dishes.add(new Dish("5","Biryani","Indian",40));

       /*
       List<Dish> markoDishes = new ArrayList<>();
       markoDishes.add(dishes.get(0));
       markoDishes.add(dishes.get(3));

       List<Dish> lidijaDishes = new ArrayList<>();
       lidijaDishes.add(dishes.get(0));
       lidijaDishes.add(dishes.get(1));
       lidijaDishes.add(dishes.get(4));

       List<Dish> monaDishes = new ArrayList<>();
       monaDishes.add(dishes.get(2));
       monaDishes.add(dishes.get(4));

       List<Dish> bobiDishes = new ArrayList<>();
       bobiDishes.add(dishes.get(1));

       List<Dish> majaDishes = new ArrayList<>();
       majaDishes.add(dishes.get(2));
       majaDishes.add(dishes.get(3));
       majaDishes.add(dishes.get(4));
       majaDishes.add(dishes.get(1));

        */

       chefs=new ArrayList<>();
       chefs.add(new Chef(1L,"Marko","Markovski","Chef for 2 years",new ArrayList<>()));
       chefs.add(new Chef(2L,"Lidija","Lilova","Private chef on a yacht",new ArrayList<>()));
       chefs.add(new Chef(3L,"Mona","Malinova","Professional chef for 3 years",new ArrayList<>()));
       chefs.add(new Chef(4L,"Bobi","Bobovski","Chef still in school",new ArrayList<>()));
       chefs.add(new Chef(5L,"Maja","Mirkova","Resturant owner in Italy",new ArrayList<>()));

    }

}
