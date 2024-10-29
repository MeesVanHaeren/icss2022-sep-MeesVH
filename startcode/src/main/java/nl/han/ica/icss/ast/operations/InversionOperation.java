package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class InversionOperation extends Operation {

    public InversionOperation(){
        acceptedTypes.add(ExpressionType.BOOL);
    }

    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        this.rhs = (Expression) child;
        return this;
    }

    @Override
    public ExpressionType getType() {
        return rhs.getType();
    }

    @Override
    public boolean hasCorrectTypes() {
        return acceptsType(rhs.getType());
    }
}
