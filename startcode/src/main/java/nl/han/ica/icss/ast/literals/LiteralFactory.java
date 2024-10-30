package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.Objects;

public class LiteralFactory {

    private static LiteralFactory instance;

    LiteralFactory(){

    }

    public static LiteralFactory getInstance() {
        if (instance == null){
            instance = new LiteralFactory();
        }
        return instance;
    }

    public Literal makeLiteral(ExpressionType expressionType, int value){
        switch (expressionType) {
            case SCALAR:
                return new ScalarLiteral(value);
            case PERCENTAGE:
                return new PercentageLiteral(value);
            case PIXEL:
                return new PixelLiteral(value);
            default:
                return null;
        }
    }

    public Literal makeLiteral(ExpressionType expressionType, String value){
        switch (expressionType) {
            case SCALAR:
                return new ScalarLiteral(value);
            case PERCENTAGE:
                return new PercentageLiteral(value);
            case PIXEL:
                return new PixelLiteral(value);
            case COLOR:
                return new ColorLiteral(value);
            case BOOL:
                return new BoolLiteral(value);
            default:
                return null;
        }
    }

    public Literal makeLiteral(ExpressionType expressionType, boolean value){
        if (expressionType == ExpressionType.BOOL) {
            return new BoolLiteral(value);
        } else {
            return null;
        }
    }
}
