package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Nesterov
 */

@Getter
@AllArgsConstructor
@ToString
public class Product {
    private String
            title,
            description,
            article,
            rating;
    private int price;

    private static List<Product> productList;

    static {
        productList = new ArrayList<>();
    }

    public static Product add(Product product) {
        productList.add(product);
        return product;
    }

    public static List<Product> getList() {
        return productList;
    }

    public static void clearList() {
        productList.clear();
    }
}
