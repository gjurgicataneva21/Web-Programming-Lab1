package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dishId;
    private String name;
    private String cusine;
    private int preparationTime;

    @ManyToOne
    Chef chef;

    public Dish(String dishId, String name, String cusine, int preparationTime, Chef chef) {
        this.dishId = dishId;
        this.name = name;
        this.cusine = cusine;
        this.preparationTime = preparationTime;
        this.chef = chef;
    }


}
