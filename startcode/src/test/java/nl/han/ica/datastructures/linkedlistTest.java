//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package nl.han.ica.datastructures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HanLinkedListImplTest {
    private HanLinkedList<String> hanLinkedList;

    HanLinkedListImplTest() {
    }

    @BeforeEach
    void setup() {
        this.hanLinkedList = new HanLinkedList<String>();
    }

    @Test
    void addFirst() {
        String test = "Test";
        this.hanLinkedList.addFirst(test);
        Assertions.assertEquals(test, this.hanLinkedList.getFirst());
    }

    @Test
    void clear() {
        this.hanLinkedList.addFirst("unwanted");
        this.hanLinkedList.addFirst("Dont want this");
        this.hanLinkedList.clear();
        Assertions.assertEquals(0, this.hanLinkedList.getSize());
    }

    @Test
    void insert() {
        this.hanLinkedList.addFirst("this goes in 3");
        this.hanLinkedList.addFirst("this goes in 2");
        this.hanLinkedList.addFirst("this goes in 1");
        this.hanLinkedList.addFirst("this goes in 0");
        String test = "this is in 1";
        this.hanLinkedList.insert(1, test);
        Assertions.assertEquals(test, this.hanLinkedList.get(1));
    }

    @Test
    void delete() {
        String test = "Blossom dance";
        this.hanLinkedList.addFirst(test);
        this.hanLinkedList.addFirst("We choose to fight");
        this.hanLinkedList.addFirst("Against the odds");
        this.hanLinkedList.addFirst("Born in a world of strife");
        this.hanLinkedList.delete(2);
        Assertions.assertEquals(test, this.hanLinkedList.get(2));
    }

    @Test
    void get() {
        String test = "Blossom dance";
        this.hanLinkedList.addFirst(test);
        this.hanLinkedList.addFirst("We choose to fight");
        this.hanLinkedList.addFirst("Against the odds");
        this.hanLinkedList.addFirst("Born in a world of strife");
        Assertions.assertEquals(test, this.hanLinkedList.get(3));
    }

    @Test
    void removeFirst() {
        String test = "Blossom dance";
        this.hanLinkedList.addFirst(test);
        this.hanLinkedList.addFirst("We choose to fight");
        this.hanLinkedList.addFirst("Against the odds");
        this.hanLinkedList.addFirst("Born in a world of strife");
        this.hanLinkedList.removeFirst();
        this.hanLinkedList.removeFirst();
        this.hanLinkedList.removeFirst();
        Assertions.assertEquals(test, this.hanLinkedList.getFirst());
    }

    @Test
    void getFirst() {
        String test = "Born in a world of strife";
        this.hanLinkedList.addFirst("Blossom dance");
        this.hanLinkedList.addFirst("We choose to fight");
        this.hanLinkedList.addFirst("Against the odds");
        this.hanLinkedList.addFirst(test);
        Assertions.assertEquals(test, this.hanLinkedList.getFirst());
    }

    @Test
    void getSize() {
        this.hanLinkedList.addFirst("Blossom dance");
        this.hanLinkedList.addFirst("We choose to fight");
        this.hanLinkedList.addFirst("Against the odds");
        this.hanLinkedList.addFirst("Born in a world of strife");
        Assertions.assertEquals(4, this.hanLinkedList.getSize());
    }
}
