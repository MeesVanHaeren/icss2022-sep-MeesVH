package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;

public abstract class Operation extends Expression {

    public Expression lhs;
    public Expression rhs;

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(lhs != null)
            children.add(lhs);
        if(rhs != null)
            children.add(rhs);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(lhs == null) {
            lhs = (Expression) child;
        } else if(rhs == null) {
            rhs = (Expression) child;
        }
        return this;
    }

    @Override
    public ExpressionType getType() {
        //Returns scalar if both are scalar, and the other type if it has another type
        //Since this is not checker code, it will return valid expresison types even if it really shouldnt be possible
        //The expression will be checked in the checker.
        //Color + Color .getType will return Color, this is how the checker will know something is up.
        if (lhs.getType() == ExpressionType.SCALAR && rhs.getType() == ExpressionType.SCALAR){
            return ExpressionType.SCALAR;
        } else if (lhs.getType() != ExpressionType.SCALAR) {
            return lhs.getType();
        } else {
            return rhs.getType();
        }
    }
}
