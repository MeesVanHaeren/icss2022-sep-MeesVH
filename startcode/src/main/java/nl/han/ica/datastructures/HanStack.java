package nl.han.ica.datastructures;

public class HanStack<T> implements IHANStack<T>{

    private IHANLinkedList<T> list = new HanLinkedList<>();

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
