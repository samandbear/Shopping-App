package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.request.NewProductRequest;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
import com.example.transactionmanagementdemo.domain.response.Watchlist;
import com.example.transactionmanagementdemo.service.ProductService;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ProductController {
    private ProductService productService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/seller/addNewProduct")
    // a. The seller should be able to add products. A product has fields including
    //description, wholesale_price, retail_price and stock’s quantity.
    //i. The wholesale price is the price which the seller paid for the product.
    //ii. The retail price is the price which customers pay for the product.
    public String createNewProduct(@RequestBody NewProductRequest request) throws Exception {
        productService.createNewProduct(request);
        return "new product";
    }

    @GetMapping("/user/home")
    // a. The user is able to view all of the products. An out of stock product should NOT
    // be shown to the user.
    public AllProductResponse getHome() {
        List<Product> products = productService.getAllProductUser();
        return AllProductResponse.builder()
                .products(products)
                .build();
    }

    @GetMapping("/user/home/detail")
    // When a user clicks on one product, the user should be redirected to the detail
    // page of that product, including the description and price (retail_price) of the
    // product
    public AllProductResponse getProductUserDetail(@RequestParam int product_id) {
        List<Product> products = productService.getProductUserDetail(product_id);
        return AllProductResponse.builder()
                .products(products)
                .build();
    }


    @GetMapping("/seller/home/detail")
    // Listing information, the current products that are listed to sell. When the
    // seller clicks on one product, the seller should be redirected to the detail
    // page of that product, including the description, wholesale_price,
    // retail_price and stock’s quantity of the product;
    public AllProductResponse getProductUserDetail_seller(@RequestParam int product_id) {
        List<Product> products = productService.getAllProduct(product_id);
        return AllProductResponse.builder()
                .products(products)
                .build();
    }

    @PutMapping("/seller/updateWholesale_price")
    // the seller should be
    //able to modify the wholesale_price, retail_price, description and
    //quantity of a product.
    public String updateWholesale_price(@RequestParam int product_id, @RequestParam float wholesale_price) {
        productService.updateWholesale_price(product_id, wholesale_price);
        return "updated Wholesale_price";
    }

    @PutMapping("/seller/updateRetail_price")
    // the seller should be
    //able to modify the wholesale_price, retail_price, description and
    //quantity of a product.
    public String updateRetail_price(@RequestParam int product_id, @RequestParam float retail_price) {
        productService.updateRetail_price(product_id, retail_price);
        return "updated Retail_price";
    }

    @PutMapping("/seller/updateDescription")
    // the seller should be
    //able to modify the wholesale_price, retail_price, description and
    //quantity of a product.
    public String updateDescription(@RequestParam int product_id, @RequestParam String description) {
        productService.updateDescription(product_id, description);
        return "updated Description";
    }


    @PostMapping("/user/addWatchlist")
    public String addToWatchList(@RequestParam Integer user_id, @RequestParam Integer product_id) throws Exception {
        userService.addToWatchList(user_id, product_id);
        return "add watchlist";
    }

    @PostMapping("/user/removeWatchlist")
    public String removeFromWatchList(@RequestParam Integer user_id, @RequestParam Integer product_id) throws Exception {
        userService.removeFromWatchList(user_id, product_id);
        return "remove watchlist";
    }

    @PostMapping("/user/viewWatchlist")
    public Watchlist viewWatchlist(@RequestParam Integer user_id) throws Exception {
        Set<Product> products = userService.viewWatchlist(user_id);
        return Watchlist.builder()
                .products(products)
                .build();
    }
}
