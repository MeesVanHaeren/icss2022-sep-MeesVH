package nl.han.ica.datastructures;

public class HanLinkedListNode<T> {

    private T value;

    private HanLinkedListNode<T> child;

    HanLinkedListNode (T value){
        this.value = value;
    }

    public HanLinkedListNode<T> getChild() {
       return child;
    }

    public void setChild(HanLinkedListNode<T> newChild) {
        child = newChild;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public HanLinkedListNode<T> findNode(int pos) {
       if (child == null || pos <= 0){
           return this;
       }
       return child.findNode(pos - 1);
    }

    public void implodeNext() {
        if (child != null){
            if (child.getChild() != null){
                this.child = child.getChild();
            } else {
                this.child = null;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public void insertNext(HanLinkedListNode<T> value) {
        if(child == null){
            child = value;
        } else {
            HanLinkedListNode<T> oldChild = this.child;
            child = value;
            child.setChild(oldChild);
        }
    }
}
