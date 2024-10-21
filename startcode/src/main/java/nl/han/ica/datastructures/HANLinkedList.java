package nl.han.ica.datastructures;

public class HANLinkedList<T> implements IHANLinkedList<T>{

    HANLinkedListHeaderNode<T> header;

    public HANLinkedList() {
        header = new HANLinkedListHeaderNode<T>();
    }

    @Override
    public void addFirst(T value) {
        header.add(value);
    }

    @Override
    public void clear() {
        header.clear();
    }

    @Override
    public void insert(int index, T value) {
        header.insert(index, new HANLinkedListNode<T>(value));
    }

    @Override
    public void delete(int pos) {
        header.delete(pos);
    }

    @Override
    public T get(int pos) {
        return header.findNode(pos).getValue();
    }

    @Override
    public void removeFirst() {
        header.removeFirst();
    }

    @Override
    public T getFirst() {
        HANLinkedListNode<T> headerChild = header.getChild();
        if (headerChild != null){
            return headerChild.getValue();
        } else {
            return null;
        }
    }

    @Override
    public int getSize() {
        return header.getSize();
    }
}
