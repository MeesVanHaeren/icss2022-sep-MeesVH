package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.LiteralFactory;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;

public class AddOperation extends Operation {

    public AddOperation(){
        acceptedTypes.add(ExpressionType.PERCENTAGE);
        acceptedTypes.add(ExpressionType.PIXEL);
        acceptedTypes.add(ExpressionType.SCALAR);
    }

    @Override
    public String getNodeLabel() {
        return "Add";
    }

    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public Literal getResult() {
        literalGuard();
        return LiteralFactory.getInstance().makeLiteral(getType(),(Integer)((Literal)lhs).getValue() + (Integer)((Literal)rhs).getValue());
    }
}
