package nl.han.ica.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HanQueueTest {

    private IHANQueue<String> queue;

    @BeforeEach
    void setup(){
        queue = new HanQueue<String>();
    }

    @Test
    void clear() {
        //Arrange
        addBlossomDance(queue);
        //Act
        queue.clear();
        //Assert
        Assertions.assertEquals(0,queue.getSize());
    }

    @Test
    void isEmpty() {
        //Arrange
        addBlossomDance(queue);
        //Act
        //Assert
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    void dequeue() {
        //Arrange
        addBlossomDance(queue);
        //Act
        //Assert
        Assertions.assertEquals("Born in a world of strife",queue.dequeue());
        Assertions.assertEquals("Against the odds",queue.dequeue());
        Assertions.assertEquals("We choose to fight",queue.dequeue());
        Assertions.assertEquals("Blossom dance",queue.dequeue());
        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    void peek() {
        //Arrange
        addBlossomDance(queue);
        //Act
        //Assert
        Assertions.assertEquals("Born in a world of strife",queue.peek());
        Assertions.assertEquals("Born in a world of strife",queue.peek());
    }

    @Test
    void getSize() {
        //Arrange
        addBlossomDance(queue);
        addBlossomDance(queue);
        addBlossomDance(queue);
        addBlossomDance(queue);
        //Act
        //Assert
        Assertions.assertEquals(16,queue.getSize());
    }

    private void addBlossomDance(IHANQueue<String> queue){
        //Adds a sequence of strings this may or may not be illegal in the AAA structure I'm not sure I made these tests
        //Without thinking of the AAA structure and they're pretty readable still if you'd ask me
        queue.enqueue("Born in a world of strife");
        queue.enqueue("Against the odds");
        queue.enqueue("We choose to fight");
        queue.enqueue("Blossom dance");
    }
}