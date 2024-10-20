package nl.han.ica.datastructures;

public class HanLinkedList<T> implements IHANLinkedList<T>{

    HanLinkedListHeaderNode<T> header;

    public HanLinkedList() {
        header = new HanLinkedListHeaderNode<T>();
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
        header.insert(index, new HanLinkedListNode<T>(value));
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
        HanLinkedListNode<T> headerChild = header.getChild();
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
