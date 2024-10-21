package nl.han.ica.datastructures;

public class HANLinkedListNode<T> {

    private T value;

    private HANLinkedListNode<T> child;

    HANLinkedListNode(T value){
        this.value = value;
    }

    public HANLinkedListNode<T> getChild() {
       return child;
    }

    public void setChild(HANLinkedListNode<T> newChild) {
        child = newChild;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public HANLinkedListNode<T> findNode(int pos) {
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

    public void insertNext(HANLinkedListNode<T> value) {
        if(child == null){
            child = value;
        } else {
            HANLinkedListNode<T> oldChild = this.child;
            child = value;
            child.setChild(oldChild);
        }
    }
}
