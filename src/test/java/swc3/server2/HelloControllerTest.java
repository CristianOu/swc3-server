package swc3.server2;

import org.junit.jupiter.api.Test;
import swc3.server2.controller.tutorials.HelloController;

public class HelloControllerTest {
    @Test
    public void returnsHelloMessage(){
        String message = new HelloController().hello("Tomas");
        assert(message).equals("Hello Tomas!");
    }
}

