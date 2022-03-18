import managers.PagesManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER;

/**
 * @author Sergey Nesterov
 */
public class AddAndDeleteProductBasketTest extends BeforeTests {

    //
    static Stream<Arguments> userData() {
        return Stream.of(
                Arguments.of("телевизор", "H32F7100C", "Detroit", "Игра Detroit")
        );
    }

    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("userData")
    void test(String firstSearch, String firstName, String secondSearch, String secondName) {
//        String firstSearch = properties.get("first.search"),
//                firstName = properties.get("first.name"),
//                secondSearch = properties.get("second.search"),
//                secondName = properties.get("second.name");

        //Цена 1го товара
        int priceFirstProduct = PagesManager.getHomePage()
                .typeInSearchFormAndRequest(firstSearch)
                .clickOnProductContainedName(firstName)
                .getPrice();

        //Добавляем 1й товар в корзину   //Цена 2го товара
        int priceSecondProduct = PagesManager.getProductCardPage()
                .clickBuy()
                .typeInSearchFormAndRequest(secondSearch)
                .clickOnProductContainedName(secondName)
                .getPrice();

        //Добавляем 2й товар в корзину
        PagesManager.getProductCardPage()
                .clickBuy();

        //Проверяем цену иконки корзины
        Assertions.assertEquals(priceFirstProduct + priceSecondProduct,
                PagesManager.getProductCardPage()
                        .getBasketIconPrice(),
                "Цена иконки корзины неверна");

        //Проверяем цену 1го товара в корзине
        Assertions.assertEquals(priceFirstProduct,
                PagesManager.getProductCardPage()
                        .clickOnBasketIcon()
                        .getProductPrice(firstName),
                "Цена первого товара в козине неверна");

        //Проверяем цену 2го товара в корзине
        Assertions.assertEquals(priceSecondProduct,
                PagesManager.getBasketPage()
                        .getProductPrice(secondName),
                "Цена второго товара в козине неверна");

        //Проверяем итоговую цену в корзине
        Assertions.assertEquals(priceFirstProduct + priceSecondProduct,
                PagesManager.getBasketPage()
                        .getBasketPrice(),
                "Цена корзины неверна");

        //Проверяем удаление второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals(-1,
                        PagesManager.getBasketPage()
                                .clickDelete(secondName)
                                .getProductPrice(secondName),
                        "Товар не удалился из корзины"),
                () -> Assertions.assertEquals(priceFirstProduct,
                        PagesManager.getBasketPage()
                                .getBasketPrice(),
                        "Цена корзины не правильно изменилась после удаления товара"));

        //Проверяем добавление первого товара
        Assertions.assertEquals(priceFirstProduct * 3,
                PagesManager.getBasketPage()
                        .clickPlus(firstName)
                        .clickPlus(firstName)
                        .getBasketPrice(),
                "Цена корзины не равна стоимости 3х товаров");

        //Проверяем возвращение второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals((priceFirstProduct * 3) + priceSecondProduct,
                        PagesManager.getBasketPage()
                                .restoreProduct()
                                .clickCheckBox(secondName)
                                .getBasketPrice(),
                        "Цена корзины не увеличилась на цену возвращенного товара"),
                () -> Assertions.assertEquals(priceSecondProduct,
                        PagesManager.getBasketPage()
                                .getProductPrice("Detroit"),
                        "Товар не появился в списке корзины"));
    }
}
