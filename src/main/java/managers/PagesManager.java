package managers;

import pages.*;

/**
 * @author Sergey Nesterov
 */
public class PagesManager {
    private static HomePage homePage;
    private static SearchResults searchResults;
    private static BasketPage basketPage;
    private static ProductCardPage productCardPage;

    public static BasketPage getBasketPage() {
        if (basketPage ==null) basketPage = new BasketPage();
        return basketPage;
    }

    public static SearchResults getSearchResults() {
        if (searchResults == null) searchResults = new SearchResults();
        return searchResults;
    }

    public static ProductCardPage getProductCardPage() {
        if (productCardPage == null) productCardPage = new ProductCardPage();
        return productCardPage;
    }

    public static HomePage getHomePage() {
        if (homePage == null) homePage = new HomePage();
        return homePage;
    }

}