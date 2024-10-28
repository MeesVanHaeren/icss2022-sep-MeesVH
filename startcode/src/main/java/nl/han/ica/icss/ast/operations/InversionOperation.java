package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;

public class InversionOperation extends Operation {
    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        this.rhs = (Expression) child;
        return this;
    }
}
