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

        //Цена 1го товара
        int priceFirstProduct = PagesManager.getInstance().getHomePage()
                .typeInSearchFormAndRequest(firstSearch)
                .clickOnProductContainedName(firstName)
                .getPrice();

        //Добавляем 1й товар в корзину   //Цена 2го товара
        int priceSecondProduct = PagesManager.getInstance().getProductCardPage()
                .clickBuy()
                .typeInSearchFormAndRequest(secondSearch)
                .clickOnProductContainedName(secondName)
                .getPrice();

        //Добавляем 2й товар в корзину
        PagesManager.getInstance().getProductCardPage()
                .clickBuy();

        //Проверяем цену иконки корзины
        Assertions.assertEquals(priceFirstProduct + priceSecondProduct,
                PagesManager.getInstance().getProductCardPage()
                        .getBasketIconPrice(),
                "Цена иконки корзины неверна");

        //Проверяем цену 1го товара в корзине
        Assertions.assertEquals(priceFirstProduct,
                PagesManager.getInstance().getProductCardPage()
                        .clickOnBasketIcon()
                        .getProductPrice(firstName),
                "Цена первого товара в козине неверна");

        //Проверяем цену 2го товара в корзине
        Assertions.assertEquals(priceSecondProduct,
                PagesManager.getInstance().getBasketPage()
                        .getProductPrice(secondName),
                "Цена второго товара в козине неверна");

        //Проверяем итоговую цену в корзине
        Assertions.assertEquals(priceFirstProduct + priceSecondProduct,
                PagesManager.getInstance().getBasketPage()
//                        .getBasketPrice(),
                        .getBasketIconPrice(),
                "Цена корзины неверна");

        //Проверяем удаление второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals(-1,
                        PagesManager.getInstance().getBasketPage()
                                .clickDelete(secondName)
                                .getProductPrice(secondName),
                        "Товар не удалился из корзины"),
                () -> Assertions.assertEquals(priceFirstProduct,
                        PagesManager.getInstance().getBasketPage()
                                .getBasketIconPrice(),
//                                .getBasketPrice(),
                        "Цена корзины не правильно изменилась после удаления товара"));

        //Проверяем добавление первого товара
        Assertions.assertEquals(priceFirstProduct * 3,
                PagesManager.getInstance().getBasketPage()
                        .clickPlus(firstName)
                        .clickPlus(firstName)
//                        .getBasketPrice(),
                        .getBasketIconPrice(),
                "Цена корзины не равна стоимости 3х товаров");

        //Проверяем возвращение второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals((priceFirstProduct * 3) + priceSecondProduct,
                        PagesManager.getInstance().getBasketPage()
                                .restoreProduct()
//                                .clickCheckBox(secondName)
//                                .getBasketPrice(),
                                .getBasketIconPrice(),
                        "Цена корзины не увеличилась на цену возвращенного товара"),
                () -> Assertions.assertEquals(priceSecondProduct,
                        PagesManager.getInstance().getBasketPage()
                                .getProductPrice("Detroit"),
                        "Товар не появился в списке корзины"));
    }
}
