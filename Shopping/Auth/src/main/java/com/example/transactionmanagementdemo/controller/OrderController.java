package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.OrderProduct;
import com.example.transactionmanagementdemo.domain.entity.Orders;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.request.BuyRequest;
import com.example.transactionmanagementdemo.domain.request.Buyitem;
import com.example.transactionmanagementdemo.domain.response.AllOrderResponse;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
import com.example.transactionmanagementdemo.domain.response.OrderDetailsResponse;
import com.example.transactionmanagementdemo.exception.NotEnoughInventoryException;
import com.example.transactionmanagementdemo.service.UserService;
import com.example.transactionmanagementdemo.service.OrderProductService;
import com.example.transactionmanagementdemo.service.OrderService;
import com.example.transactionmanagementdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api")

public class OrderController {
    private OrderService orderService;
    private UserService userService;

    private OrderProductService orderProductService;

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderProductService(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setLoginService(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/buy")
    // The user should be able to purchase listing items with a specified quantity by
    // creating a “Processing” order. After a user places an order, the item’s stock
    // should be deducted accordingly.
    // b. If the quantity of an item that the user is purchasing is greater than the item’s
    // stock, throw a custom exception named ‘NotEnoughInventoryException’ using
    // Exception Handler and the order should not be placed.
    public String createNewOrder(@RequestParam Integer user_id, @RequestBody BuyRequest request) throws Exception {
        for (Buyitem item : request.getBuyitems()) {
            Product p = productService.getProductById(item.getProduct_id());
            int stock = p.getStock_quantity();
            if (item.getQuantity() > stock) {
                throw new NotEnoughInventoryException("no enough stock");
            }
        }

        Orders orders = orderService.createNewOrder(user_id);
        orderProductService.createNewOrderProduct(orders, request);

        return "succeed";
    }

    @GetMapping("/allOrders")
    // Order information, with details of order placed time, users who placed
    //the order and the order status (Processing, Processing, Canceled)
    public AllOrderResponse getAllOrder() throws Exception {
        List<Orders> orders = orderService.getAllOrder();
        return AllOrderResponse.builder()
                .Orders(orders)
                .build();
    }

    @GetMapping("/allUserOrders")
    // After purchasing the product, the user should be able to view order details
    //including, order placement time and order status which is Processing,
    //Completed or Canceled.
    public AllOrderResponse AllUserOrders(@RequestParam int user_id) throws Exception {
        List<Orders> orders = orderService.getOrderByUser(user_id);
        return AllOrderResponse.builder()
                .Orders(orders)
                .build();
    }

    @GetMapping("/OrderProductDetail")
    // The seller can click and see information regarding any single order, completed with the items involved in the order.
    public List<OrderDetailsResponse> getOrderProductByOrder(@RequestParam int order_id) throws Exception {
        List<OrderDetailsResponse> orderProducts = orderProductService.getOrderProductByOrder(order_id);
        return orderProducts;
    }

    @PutMapping("/cancel")
    //  The user should be able to cancel an order by updating the status from
    //“Processing” to “Canceled”. If so, the item’s stock should be incremented
    //accordingly to offset the auto-deduction that took place when the order is first
    //placed. However, a “Completed” order cannot be changed to “Canceled”.
    public String cancelOrder(@RequestParam int order_id, @RequestParam int user_id) throws Exception {
        orderService.cancelOrder(order_id,user_id);
        return "cancel succeed";
    }

    @PutMapping("/complete")
    // 4. Order a. The seller should be able to complete a “Processing” order by updating its status to “Completed”.
    public String completedOrder(@RequestParam int order_id, @RequestParam int user_id) throws Exception {
        orderService.completedOrder(order_id,user_id);
        return "complete succeed";
    }

    @GetMapping("/mostSold3")
    // d. The seller can see which 3 products are the most popular/sold (excluding canceled and ongoing order).
    public List<String> getTop3MostSold() {
        List<String> ans = orderService.getTop3MostSold();
        return ans;
    }

    @GetMapping("/recent3")
    // d. The user can also view their top 3 most recently purchased items. (excluding canceled order, use item id as tie breaker)
    public List<String> getTop3MostRecent(@RequestParam Integer user_id) {
        List<String> ans = orderService.getTop3MostRecent(user_id);
        return ans;
    }

    @GetMapping("/freq3")
    // c. The user should be able to view their top 3 most frequently purchased items. (excluding canceled order, use item ID as tie breaker)
    public List<String> getTop3MostFreq(@RequestParam Integer user_id) {
        List<String> ans = orderService.getTop3MostFreq(user_id);
        return ans;
    }

    @GetMapping("/totalItemSold")
    // e. The seller can also see the amount of total items sold successfully (excluding canceled and ongoing order).
    public Long getTotalItemSold() {
        return orderService.getTotalItemSold();
    }

    @GetMapping("/users3")
    // f. Show the top 3 buyers who spent the most (excluding canceled and ongoing order, use first name as a tie breaker).
    // Error
    public List<String> getTop3User() {
        List<String> ans = orderService.getTop3User();
        return ans;
    }


    @GetMapping("/mostProfit")
    // c. The seller can see which product brings the most profit.
    public String getMostProfit() {
        return orderService.getMostProfit();
    }
}
