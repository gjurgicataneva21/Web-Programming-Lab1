package mk.ukim.finki.wp.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {
    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public ChefServiceImpl(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Chef> listChefs() {
        return chefRepository.findAll();
    }

    @Override
    public Chef findById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Chef addDishToChef(Long chefId, String dishId) {
        Chef chef = chefRepository.findById(chefId).orElse(null);
        Dish dish = dishRepository.findByDishId(dishId);

        if (dish == null || chef == null)
            throw new RuntimeException("Invalid request");

        Dish checkDish = chef.getDishes().stream()
                .filter(d -> d.getDishId().equals(dishId))
                .findFirst()
                .orElse(null);

        if (checkDish == null) {
            dish.setChef(chef);
            dishRepository.save(dish);

            chef.getDishes().add(dish);
        }

        return chef;
    }

    @Override
    public List<Dish> sortedDishes(Long id) {
        Chef chef = chefRepository.findById(id).orElseThrow(RuntimeException::new);
        return chef.getDishes().stream().sorted(Comparator.comparing(Dish::getName)).toList();
    }
}