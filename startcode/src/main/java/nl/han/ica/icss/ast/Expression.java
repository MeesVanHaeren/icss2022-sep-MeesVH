package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

public abstract class Expression extends ASTNode implements CssRepresentable {
    public abstract ExpressionType getType();

}
