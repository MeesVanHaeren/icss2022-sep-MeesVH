package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class SubtractOperation extends Operation {

    public SubtractOperation(){
        acceptedTypes.add(ExpressionType.PERCENTAGE);
        acceptedTypes.add(ExpressionType.PIXEL);
        acceptedTypes.add(ExpressionType.SCALAR);
    }

    @Override
    public String getNodeLabel() {
        return "Subtract";
    }

    @Override
    public String getCssRepresentation() {
        return "";
    }
}
