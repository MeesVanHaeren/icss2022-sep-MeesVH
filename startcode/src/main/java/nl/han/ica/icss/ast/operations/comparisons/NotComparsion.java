package nl.han.ica.icss.ast.operations.comparisons;

import nl.han.ica.icss.ast.operations.ComparisonOperation;

public class NotComparsion extends ComparisonOperation {
    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public String getNodeLabel() {
        return "Not";
    }
}
