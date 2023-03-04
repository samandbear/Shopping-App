# Shopping-App

User (Buyer)
The users are able to shop for different products.
1. Registration
a. Before being able to purchase products, a user has to first register and login.
i. Your application should prevent registration using the same username
and email.
ii. Only username, email and password are required to register an account.
2. Login
a. If the user has entered the correct credentials, they may proceed to the
corresponding page based on their authorities.
b. If the user has entered incorrect credentials, a custom named exception
‘InvalidCredentialsException’ should be thrown and handled by the Exception
handler. The message the user will get is: “Incorrect credentials, please try
again.”
3. Home Page
a. The user is able to view all of the products. An out of stock product should NOT
be shown to the user.
b. When a user clicks on one product, the user should be redirected to the detail
page of that product, including the description and price (retail_price) of the
product. (The user should NOT be able to see the actual quantity of any items).
c. After purchasing the product, the user should be able to view order details
including, order placement time and order status which is Processing,
Completed or Canceled.
4. Purchasing
a. The user should be able to purchase listing items with a specified quantity by
creating a “Processing” order. After a user places an order, the item’s stock
should be deducted accordingly.
b. If the quantity of an item that the user is purchasing is greater than the item’s
stock, throw a custom exception named ‘NotEnoughInventoryException’ using
Exception Handler and the order should not be placed.
c. The user should be able to cancel an order by updating the status from
“Processing” to “Canceled”. If so, the item’s stock should be incremented
accordingly to offset the auto-deduction that took place when the order is first
placed. However, a “Completed” order cannot be changed to “Canceled”.
5. Product Watchlist
a. The user can add/remove products to/from their watchlist.
b. The user can view all in stock products within their watchlist.
i. When viewing the watchlist, products which are out of stock will not be
shown to the user.

Admin (Seller)
The seller is able to list different products to sell.
1. Home Page
a. The seller should be able to view a dashboard, consisting of the following:
i. Order information, with details of order placed time, users who placed
the order and the order status (Processing, Processing, Canceled).
ii. Listing information, the current products that are listed to sell. When the
seller clicks on one product, the seller should be redirected to the detail
page of that product, including the description, wholesale_price,
retail_price and stock’s quantity of the product; the seller should be
able to modify the wholesale_price, retail_price, description and
quantity of a product.
2. Listing
a. The seller should be able to add products. A product has fields including
description, wholesale_price, retail_price and stock’s quantity.
i. The wholesale price is the price which the seller paid for the product.
ii. The retail price is the price which customers pay for the product.
3. Selling
a. When one product is sold, the quantity of that product should be deducted
accordingly. And such quantity should be reflected on the dashboard.
4. Order
a. The seller should be able to complete a “Processing” order by updating its status
to “Completed”.
b. The seller should also be able to cancel an order for some reasons, such as that
the product is sold out locally, by updating the order status to “Canceled”. If so,
the item’s stock should be incremented accordingly to offset the auto-deduction
that took place when the order is first placed. However, a “Canceled” order
cannot be completed, nor can a “Completed” order be canceled.

