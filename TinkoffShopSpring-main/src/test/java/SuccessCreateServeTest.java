import org.example.components.ProductComponent;
import org.example.controllers.ProductController;
import org.example.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SuccessCreateServeTest {
    //добавление товара
    //добавление заказа
    //проверка, что заказ добавлен
    // проверка , что пользватель и продукт в заказе корректный
    //удаление
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductComponent productComponent;

    @Autowired
    ProductController productController;

    @Test
    public void createOrderTest() {
        //PRECONDITION
        var serveName = "testServe";
        var servePrice = 100;

        var pathGetServe = "/byProductName?=" + serveName;
        var responseGetServe = TestUtils.callGet(pathGetServe);
        var status = responseGetServe.extract().statusCode();

        if (status == 200) {
            var id = Long.valueOf((Integer) responseGetServe.extract().body().path("id"));
            TestUtils.callDelete("/deleteProductById?id=" + id);
        }

        var userName = "Ivan";
        var userPhone = "+71234567678";

        var pathCreateServe = "/createServe?name=" + serveName + "&price=" + servePrice;
        var responseCreateServe = TestUtils.callPut(pathCreateServe).assertThat().statusCode(200);
        var idServe = Long.valueOf((Integer) responseCreateServe.extract().body().path("id"));

        var pathCreateOrder = "/createOrder?userName=" + userName + "&userPhone=" + userPhone + "&productName=" + serveName;


        //TEST

        var responseCreateOrder = TestUtils.callPost(pathCreateOrder).assertThat().statusCode(200);

        var idOrder = Long.valueOf((Integer) responseCreateOrder.extract().body().path("id"));

        //asserts
        var pathGetUser = "/byPhone?phone=" + userPhone;

        var responseCreateUser = TestUtils.callGet(pathGetUser);

        var idServeInOrder = Long.valueOf((Integer) responseCreateOrder.extract().body().path("productId"));
        var idUserInOrder = Long.valueOf((Integer) responseCreateOrder.extract().body().path("authorId"));
        var idUser = Long.valueOf((Integer) responseCreateUser.extract().body().path("id"));

        assertThat(responseCreateUser.extract().statusCode()).as("statusCode").isEqualTo(200);
        assertThat(idServeInOrder).as("productId").isEqualTo(idServe);
        assertThat(idUserInOrder).as("authorId").isEqualTo(idUser);

        //postcondition
        var pathDeleteOrder = "/deleteOrderById?id=" + idOrder;
        TestUtils.callDelete(pathDeleteOrder).statusCode(200);

        var pathDeleteServe = "/deleteProductById?id=" + idServe;
        TestUtils.callDelete(pathDeleteServe).statusCode(200);

        var pathDeleteUser = "/deleteUserById?id=" + idUser;
        TestUtils.callDelete(pathDeleteUser).statusCode(200);

    }

}
