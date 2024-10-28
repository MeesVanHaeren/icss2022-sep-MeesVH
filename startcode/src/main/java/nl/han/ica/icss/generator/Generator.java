package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.CssRepresentable;
import nl.han.ica.icss.ast.Stylerule;

import java.util.ArrayList;

public class Generator {

	//Problem analysis time!!
	//I need to turn every object in the AST into an ordered text representation of itself
	//If a scope is introduced 2 spaces ("  ") need to be prepended to the statement
	//If-statements will not occur, so the only scoped things in this language are stylerule declarations
	//This can be done statically therefore, but this would feel really really bad, and isn't expandable.
	//An approach like in the evaluator or checker would be more expandable and relatively painless, so I will go for that.
	//The way we will print the objects will be via an interface "CSS-representable"

	//An AST that has been evaluated will contain only stylerules with declarations
	public String generate(AST ast) {
		StringBuilder stringBuilder = new StringBuilder();
		for (ASTNode astNode : ast.root.body){
			if (astNode instanceof Stylerule){
				stringBuilder.append(((Stylerule) astNode).getCssRepresentation());
				//Two newlines because I prefer it this way and this is my implementation
				stringBuilder.append("\n\n");
			}
		}
		return stringBuilder.toString();
	}

}
