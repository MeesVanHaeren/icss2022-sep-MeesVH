package nl.han.ica.icss.ast.operations.comparisons;

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
}
