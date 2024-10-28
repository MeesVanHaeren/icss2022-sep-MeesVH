package nl.han.ica.icss.ast.operations.comparisons;

import nl.han.ica.icss.ast.operations.ComparisonOperation;

public class EqLesserComparsion extends ComparisonOperation {
    @Override
    public String getCssRepresentation() {
        return "";
    }

    @Override
    public String getNodeLabel() {
        return "Lesser or equal";
    }
}
