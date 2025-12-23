package mk.ukim.finki.wp.lab.service;


import jakarta.persistence.criteria.Join;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import org.springframework.data.jpa.domain.Specification;

public class DishSpecification {

    public static Specification<Dish> filterContainsText(String field, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(field)),
                "%" + value.toLowerCase() + "%"
        );
    }

    public static Specification<Dish> filterEquals(String field, Object value) {
        if (value == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            if (field.contains(".")) {
                String[] parts = field.split("\\.");
                Join<Dish, ?> join = root.join(parts[0]);
                return criteriaBuilder.equal(join.get(parts[1]), value);
            }
            return criteriaBuilder.equal(root.get(field), value);
        };
    }

    public static Specification<Dish> filterLessThanEqual(String field, Integer value) {
        if (value == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(field), value);
    }

    public static Specification<Dish> filterChefName(String chefName) {
        if (chefName == null || chefName.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<Dish, Chef> chefJoin = root.join("chef");
            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(chefJoin.get("firstName")),
                            "%" + chefName.toLowerCase() + "%"
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(chefJoin.get("lastName")),
                            "%" + chefName.toLowerCase() + "%"
                    )
            );
        };
    }
}
