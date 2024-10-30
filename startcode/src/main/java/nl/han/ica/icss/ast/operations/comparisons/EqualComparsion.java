package nl.han.ica.icss.ast.operations.comparisons;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.LiteralFactory;
import nl.han.ica.icss.ast.operations.ComparisonOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class EqualComparsion extends ComparisonOperation {

    public EqualComparsion(){
        super();
        acceptedTypes.add(ExpressionType.BOOL);
        acceptedTypes.add(ExpressionType.COLOR);
    }

    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public String getNodeLabel() {
        return "Equal";
    }

    @Override
    public Literal getResult() {
        literalGuard();
        return LiteralFactory.getInstance().makeLiteral(ExpressionType.BOOL, ((Literal)lhs).getValue().equals(((Literal)rhs).getValue()));
    }
}
