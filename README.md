# The Shopping Web App
The Shopping Web App is a Java-based web application built using the Spring Framework. It allows users to perform various actions related to shopping, including ordering products, applying coupons, making payments, and viewing order statuses. The app is designed to provide a seamless shopping experience for users, ensuring that they can easily browse products, apply discounts, and complete transactions.


## API Documentation
For detailed documentation on each API endpoint, including request and response formats, status codes, and error handling, refer to the  API Documentation file.

[API Documentation](https://documenter.getpostman.com/view/31944249/2sA35G3gm3)



# Features
Product Ordering: Users can enter the quantity of products they wish to order.

Availability Check: The app checks the availability of the desired quantity of products before processing the order.

Coupon Application: Users can view and apply available coupons to their orders.

Payment Processing: The app facilitates mock payment processing for completed orders.

Order Status: Users can view the status of all their orders, including details such as order ID, quantity, amount, and status.


# Base URL
The base URL for accessing the API endpoints is http://localhost:8080 if running locally.

# ER Diagram
![shopping ER](https://github.com/arshadiqbal786/Shopping_App_HyperSrot_Technologies_Tsk/assets/94107920/7ca46302-b6dc-4e64-8e38-010e25d0cda5)
# Class Diagram
![chatuml-diagram](https://github.com/arshadiqbal786/Shopping_App_HyperSrot_Technologies_Tsk/assets/94107920/e85142f9-2160-452f-9c28-a8be4baa24cb)


## Installation

To run the Shopping Web App project, follow these steps:

1. **Clone the Repository:**
   Clone the project repository from the GitHub repository to your local machine using the following command:
   ```
   git clone <repository-url>
   ```

2. **Navigate to Project Directory:**

   Navigate to the directory of the cloned project using the `cd` command:
   ```
   cd shopping-web-app
   ```

3. **Install Dependencies:**
   Use Maven to install project dependencies by running the following command:
   ```
   mvn clean install
   ```

4. **Start the Application:**
   Start the Spring Boot application by running the generated JAR file with the following command:
   ```
   java -jar target/<application-name>.jar
   ```

5. **Access the Endpoints:**
   Once the application is running, you can access the defined endpoints to perform various actions such as ordering products, applying coupons, making payments, and viewing order statuses.

6. **Test the Functionality:**
   Use tools like Postman or curl to send HTTP requests to the endpoints and test the functionality of the application.


By following these steps, you should be able to run the Shopping Web App project locally on your machine and interact with its functionalities.
```
    


