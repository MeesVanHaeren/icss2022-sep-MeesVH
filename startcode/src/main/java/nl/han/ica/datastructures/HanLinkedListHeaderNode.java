package nl.han.ica.datastructures;

public class HanLinkedListHeaderNode<T> extends HanLinkedListNode<T>{
    private int size;

    HanLinkedListHeaderNode() {
        super(null);
        size = 0;
    }

    public void clear() {
        setChild(null);
        size = 0;
    }

    public void add(T value) {
        HanLinkedListNode<T> oldFirst = getChild();
        setChild(new HanLinkedListNode<T>(value));
        getChild().setChild(oldFirst);
        size += 1;
    }

    public int getSize() {
        return size;
    }

    public void removeFirst() {
        if (getChild() != null){
            HanLinkedListNode<T> newChild = getChild().getChild();
            setChild(newChild);
            size -= 1;
        }
    }

    @Override
    public HanLinkedListNode<T> findNode(int pos){
        return getChild().findNode(pos);
    }

    public void insert(int index, HanLinkedListNode<T> node) {
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
