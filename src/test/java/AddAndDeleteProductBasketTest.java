import entities.Product;
import managers.PagesManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * @author Sergey Nesterov
 */
public class AddAndDeleteProductBasketTest extends BeforeTests {

    @ParameterizedTest
    @CsvFileSource(resources = "/test-parameters.csv")
    void test(String firstSearch, String firstName, String secondSearch, String secondName) {


        PagesManager.getInstance().getHomePage()
                .typeInSearchFormAndRequest(firstSearch)
                .clickOnProductContainedName(firstName)
                .getProduct()           //1й товар в лист
                .clickBuy()             //Добавляем 1й товар в корзину
                .typeInSearchFormAndRequest(secondSearch)
                .clickOnProductContainedName(secondName)
                .getProduct()          //2й товар в лист
                .clickBuy();            //Добавляем 2й товар в корзину


        int priceProduct1 = Product.getList().get(0).getPrice();
        int priceProduct2 = Product.getList().get(1).getPrice();
        //Проверяем цену иконки корзины
        Assertions.assertEquals(priceProduct1 + priceProduct2,
                PagesManager.getInstance().getHomePage().getBasketIconPrice(),
                "Цена иконки корзины неверна");

        String titleProduct1 = Product.getList().get(0).getTitle().substring(0, 10);
        String titleProduct2 = Product.getList().get(1).getTitle().substring(0, 10);

        Assertions.assertAll(
                //Проверяем цену 1го товара в корзине
                () -> Assertions.assertEquals(priceProduct1,
                        PagesManager.getInstance().getProductCardPage()
                                .clickOnBasketIcon()
                                .getProductPrice(titleProduct1),
                        "Цена первого товара в козине неверна"),

                //Проверяем цену 2го товара в корзине
                () -> Assertions.assertEquals(priceProduct2,
                        PagesManager.getInstance().getBasketPage()
                                .getProductPrice(titleProduct2),
                        "Цена второго товара в козине неверна"),

                //Проверяем итоговую цену в иконки корзине
                () -> Assertions.assertEquals(priceProduct1 + priceProduct2,
                        PagesManager.getInstance().getBasketPage()
                                .getBasketIconPrice(),
                        "Цена иконки корзины неверна")
        );

        //Проверяем удаление второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals(-1,
                        PagesManager.getInstance().getBasketPage()
                                .clickDelete(secondName)
                                .getProductPrice(secondName),
                        "Товар не удалился из корзины"),
                () -> Assertions.assertEquals(priceProduct1,
                        PagesManager.getInstance().getBasketPage()
                                .getBasketIconPrice(),
                        "Цена корзины не правильно изменилась после удаления товара"));

        //Проверяем добавление первого товара
        Assertions.assertEquals(priceProduct1 * 3,
                PagesManager.getInstance().getBasketPage()
                        .clickPlus(titleProduct1)
                        .clickPlus(titleProduct1)
                        .getBasketIconPrice(),
                "Цена корзины не равна стоимости 3х товаров");

        //Проверяем возвращение второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals((priceProduct1 * 3) + priceProduct2,
                        PagesManager.getInstance().getBasketPage()
                                .restoreProduct()
                                .getBasketIconPrice(),
                        "Цена корзины не увеличилась на цену возвращенного товара"),
                () -> Assertions.assertEquals(priceProduct2,
                        PagesManager.getInstance().getBasketPage()
                                .getProductPrice(titleProduct2),
                        "Товар не появился в списке корзины"));
    }
}
