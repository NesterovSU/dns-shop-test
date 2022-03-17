import managers.DriverManager;
import managers.PagesManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Sergey Nesterov
 */
public class AddAndDeleteProductBasketTest {

    @BeforeAll
    static void beforeAll() {
        DriverManager.getWebDriver().get("https://www.dns-shop.ru");
    }

    @AfterAll
    public static void afterAll() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DriverManager.quit();
    }

    @Test
    void test() {
        //Цена 1го товара
        int priceFirstProduct = PagesManager.getHomePage()
                .typeInSearchFormAndRequest("телевизор")
                .clickOnProductContainedName("LED DEXP H32F7100C")
                .getPrice();

        System.out.println(priceFirstProduct);

        //Добавляем 1й товар в корзину   //Цена 2го товара
        int priceSecondProduct = PagesManager.getProductCardPage()
                .clickBuy()
                .typeInSearchFormAndRequest("Detroit")
                .clickOnProductContainedName("Игра Detroit")
                .getPrice();

        System.out.println(priceSecondProduct);

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
                        .getProductPrice("H32F7100C"),
                "Цена первого товара в козине неверна");

        //Проверяем цену 2го товара в корзине
        Assertions.assertEquals(priceSecondProduct,
                PagesManager.getBasketPage()
                        .getProductPrice("Игра Detroit"),
                "Цена второго товара в козине неверна");

        //Проверяем итоговую цену в корзине
        Assertions.assertEquals(priceFirstProduct + priceSecondProduct,
                PagesManager.getBasketPage()
                        .getBasketPrice(),
                "Цена корзины неверна");

        //Проверяем удаление товара
        Assertions.assertAll(
                () -> Assertions.assertEquals(-1,
                        PagesManager.getBasketPage()
                                .clickDelete("Detroit")
                                .getProductPrice("Detroit"),
                        "Товар не удалился из корзины"),
                () -> Assertions.assertEquals(priceFirstProduct,
                        PagesManager.getBasketPage()
                                .getBasketPrice(),
                        "Цена корзины не правильно изменилась после удаления товара"));

        //Проверяем добавление первого товара
        Assertions.assertEquals(priceFirstProduct * 3,
                PagesManager.getBasketPage()
                        .clickPlus("H32F7100C")
                        .clickPlus("H32F7100C")
                        .getBasketPrice(),
                "Цена корзины не равна стоимости 3х товаров");

        //Проверяем возвращение второго товара
        Assertions.assertAll(
                () -> Assertions.assertEquals((priceFirstProduct * 3) + priceSecondProduct,
                        PagesManager.getBasketPage()
                                .restoreProduct()
                                .clickCheckBox("Detroit")
                                .getBasketPrice(),
                        "Цена корзины не увеличилась на цену возвращенного товара"),
                () -> Assertions.assertEquals(priceSecondProduct,
                        PagesManager.getBasketPage()
                                .getProductPrice("Detroit"),
                        "Товар не появился в списке корзины"));
    }
}
