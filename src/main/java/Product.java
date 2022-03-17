import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Nesterov
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Product {
    private String
            title,
            description,
            article,
            country;
    private int price;

    private static List<Product> productList;

    static {
        productList = new ArrayList<>();
    }

    public static void add(Product product){
        productList.add(product);
        System.out.println(productList);
    }

    public static List<Product>  getList(){
        return productList;
    }
}
