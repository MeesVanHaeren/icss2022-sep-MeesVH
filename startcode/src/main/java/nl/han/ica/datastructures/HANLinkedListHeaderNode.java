package nl.han.ica.datastructures;

public class HANLinkedListHeaderNode<T> extends HANLinkedListNode<T> {
    private int size;

    HANLinkedListHeaderNode() {
        super(null);
        size = 0;
    }

    public void clear() {
        setChild(null);
        size = 0;
    }

    public void add(T value) {
        HANLinkedListNode<T> oldFirst = getChild();
        setChild(new HANLinkedListNode<T>(value));
        getChild().setChild(oldFirst);
        size += 1;
    }

    public int getSize() {
        return size;
    }

    public void removeFirst() {
        if (getChild() != null){
            HANLinkedListNode<T> newChild = getChild().getChild();
            setChild(newChild);
            size -= 1;
        }
    }

    @Override
    public HANLinkedListNode<T> findNode(int pos){
        return getChild().findNode(pos);
    }

    public void insert(int index, HANLinkedListNode<T> node) {
        if(index > 0){
            findNode(index - 1).insertNext(node);
        } else {
            insertNext(node);
        }
        size += 1;
    }

    public void delete(int pos) {
        findNode(pos-1).implodeNext();
        size -= 1;
    }
}
