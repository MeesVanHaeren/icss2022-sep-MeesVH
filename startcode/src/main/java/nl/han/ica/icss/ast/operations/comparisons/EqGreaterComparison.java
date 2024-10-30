package nl.han.ica.icss.ast.operations.comparisons;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.literals.LiteralFactory;
import nl.han.ica.icss.ast.operations.ComparisonOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class EqGreaterComparison extends ComparisonOperation {

    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public String getNodeLabel() {
        return "Greater or equal";
    }

    @Override
    public Literal getResult() {
        literalGuard();
        return LiteralFactory.getInstance().makeLiteral(getType(),(Integer) ((Literal)lhs).getValue() >= (Integer)((Literal)rhs).getValue());
    }
}
