package nl.han.ica.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HanStackTest {

    IHANStack<String> stringIHANStack;

    @BeforeEach
    void setup(){
        stringIHANStack = new HanStack<>();
    }

    @Test
    void pop() {
        //Arrange
        String b = "Born in a world of strife";
        String a = "Against the odds";
        String w = "We choose to fight";
        stringIHANStack.push(b);
        stringIHANStack.push(a);
        stringIHANStack.push(w);
        //Act
        String one = stringIHANStack.pop();
        String two = stringIHANStack.pop();
        String three = stringIHANStack.pop();
        //Assert
        Assertions.assertEquals(w,one);
        Assertions.assertEquals(a,two);
        Assertions.assertEquals(b,three);
    }

    @Test
    void peek() {
        //Arrange
        String b = "Born in a world of strife";
        String a = "Against the odds";
        String w = "We choose to fight";
        stringIHANStack.push(b);
        stringIHANStack.push(a);
        stringIHANStack.push(w);
        //Act
        String one = stringIHANStack.peek();
        String two = stringIHANStack.peek();
        String three = stringIHANStack.peek();
        //Assert
        Assertions.assertEquals(w,one);
        Assertions.assertEquals(w,two);
        Assertions.assertEquals(w,three);
    }
}