import org.example.repositories.OrderRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.UserRepository;
import org.example.components.ProductComponent;
import org.example.components.OrderComponent;
import org.example.components.UserComponent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderComponentTest extends AbstractTest{
    @Autowired
    UserComponent userComponent;

    @Autowired
    ProductComponent productComponent;

    @Autowired
    OrderComponent orderComponent;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void Setup(){
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createOrderTests(){
        var userName = "Oleg";
        var userPhone = "+79991112233";
        var productName = "Milk";
        var productPrice = 100;

        var product = productComponent.addNewGoods(productName,productPrice);

        //TEST
        var createOrder = orderComponent.createOrder(userName, userPhone, productName);

        //ASSERTS
        var order = orderRepository.findById(createOrder.getId()).get();

        var authorId = order.getAuthorId();

        var user = userRepository.findById(authorId);
        assertThat(user).isNotEmpty();
        assertThat(user.get().getPhone()).isEqualTo(userPhone);
        assertThat(user.get().getName()).isEqualTo(userName);

        var productId = order.getProductId();
        assertThat(product.getId()).isEqualTo(productId);

    }

//    @Test
//    void errorWhenTryToOrderInvalidProductTest() {
//        var productName = "Beer";
//        var error = assertThrows(
//                NoSuchElementException.class,
//                () -> orderComponent.createOrder(userName: "Maxim", userPhone: "12345", productName);
//        );
//        assertThat(error.getMessage()).isEqualTo( expectedStringTemplate: "Продукта с именем %s нет!", productName);
//    }
}


