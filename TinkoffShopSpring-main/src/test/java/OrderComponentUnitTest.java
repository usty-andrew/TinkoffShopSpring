import org.example.components.OrderComponent;
import org.example.components.ProductComponent;
import org.example.components.UserComponent;
import org.example.entity.Product;
import org.example.entity.User;
import org.example.repositories.OrderRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class OrderComponentUnitTest extends AbstractTest{
    @Mock
    UserComponent userComponent;

    @Mock
    ProductComponent productComponent;

    @InjectMocks
    OrderComponent orderComponent;

    @Mock
    OrderRepository orderRepository;


    @Mock
    UserRepository userRepository;

    @Test
    void orderComponentTest(){
        var userName = "Oleg";
        var userPhone = "+79991112233";
        var productName = "Milk";
        var productPrice = 100;

        var user = new User();
        user.setPhone(userPhone);
        user.setName(userName);
        user.setId(1L);

        var product = new Product();
        product.setName(productName);
        product.setPrice(productPrice);
        product.setId(1L);
        product.setRemainder(1);

        Mockito.when(userComponent.getOrCreateUser(userName,userPhone)).thenReturn(user);
        Mockito.when(productComponent.getProductByName(productName)).thenReturn(product);

        assertThrows(NullPointerException.class, ()-> orderComponent.createOrder(userName, userPhone, productName));

        Mockito.verify(orderRepository, Mockito.times(0)).save(any());
    }
}
