package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;

public abstract class ComparisonOperation extends Operation {
    public ComparisonOperation(){
        acceptedTypes.add(ExpressionType.PERCENTAGE);
        acceptedTypes.add(ExpressionType.PIXEL);
        acceptedTypes.add(ExpressionType.SCALAR);
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.BOOL;
    }
}
