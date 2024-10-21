package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T>{

    private IHANLinkedList<T> list = new HANLinkedList<>();

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    public T pop() {
        T value = list.getFirst();
        list.removeFirst();
        return value;
    }

    @Override
    public T peek() {
        return list.getFirst();
    }
}
