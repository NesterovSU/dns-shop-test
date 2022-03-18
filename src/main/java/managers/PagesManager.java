package managers;

import pages.*;

/**
 * @author Sergey Nesterov
 */
public class PagesManager {
    private  HomePage homePage;
    private  SearchResults searchResults;
    private  BasketPage basketPage;
    private  ProductCardPage productCardPage;

    private static PagesManager pagesManager;

    public static PagesManager getInstance(){
        if (pagesManager==null) pagesManager = new PagesManager();
        return pagesManager;
    }
    public static void deleteInstance(){
        pagesManager=null;
    }

    private PagesManager(){
    }

    public BasketPage getBasketPage() {
        if (basketPage == null) basketPage = new BasketPage();
        return basketPage;
    }

    public SearchResults getSearchResults() {
        if (searchResults == null) searchResults = new SearchResults();
        return searchResults;
    }

    public ProductCardPage getProductCardPage() {
        if (productCardPage == null) productCardPage = new ProductCardPage();
        return productCardPage;
    }

    public HomePage getHomePage() {
        if (homePage == null) homePage = new HomePage();
        return homePage;
    }
}