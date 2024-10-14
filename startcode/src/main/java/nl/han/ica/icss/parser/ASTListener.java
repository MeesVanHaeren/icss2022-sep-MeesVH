package nl.han.ica.icss.parser;

import java.util.Stack;


import nl.han.ica.datastructures.HanStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HanStack<>();
	}
    public AST getAST() {
        return ast;
    }

	// Selector methods
	@Override
	public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
		super.enterTagSelector(ctx);
		currentContainer.push(new TagSelector(ctx.getText()));
	}

	@Override
	public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
		super.enterClassSelector(ctx);
		currentContainer.push(new ClassSelector(ctx.getText()));
	}

	@Override
	public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
		super.enterIdSelector(ctx);
		currentContainer.push(new IdSelector(ctx.getText()));
	}

	//Takes selector off the stack and applies it to the stylerule behind it in the stack
	private void attachSelector(){
		Selector selector = (Selector) currentContainer.pop();
		Stylerule stylerule = (Stylerule) currentContainer.peek();
		stylerule.selectors.add(selector);
	}

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		super.exitTagSelector(ctx);
		attachSelector();
	}

	@Override
	public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
		super.exitClassSelector(ctx);
		attachSelector();
	}

	@Override
	public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
		super.exitIdSelector(ctx);
		attachSelector();
	}

	//Adds a stylerule when appropriate and removes and adds it when it's done
	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		super.enterStyleRule(ctx);
		currentContainer.push(new Stylerule());
	}

	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		super.exitStyleRule(ctx);
		Stylerule stylerule = (Stylerule) currentContainer.pop();
		ast.root.addChild(stylerule);
	}

	//Declaration
	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		super.enterDeclaration(ctx);
		currentContainer.push(new Declaration());
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		super.exitDeclaration(ctx);
		Declaration declaration = (Declaration) currentContainer.pop();
		Stylerule currentStylerule = (Stylerule) currentContainer.peek();
		currentStylerule.body.add(declaration);
	}
	
	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		super.enterProperty(ctx);
		Declaration currentDeclaration = (Declaration) currentContainer.peek();
		currentDeclaration.property = new PropertyName(ctx.getText());
	}

	//Values
	private void attachLiteral(Literal literal){
		Declaration currentDeclaration = (Declaration) currentContainer.peek();
		currentDeclaration.expression = literal;
	}

	@Override
	public void enterColor(ICSSParser.ColorContext ctx) {
		super.enterColor(ctx);
		attachLiteral(new ColorLiteral(ctx.getText()));
	}

	@Override
	public void enterBoolean(ICSSParser.BooleanContext ctx) {
		super.enterBoolean(ctx);
		attachLiteral(new BoolLiteral(ctx.getText()));
	}

	@Override
	public void enterPercentage(ICSSParser.PercentageContext ctx) {
		super.enterPercentage(ctx);
		attachLiteral(new PercentageLiteral(ctx.getText()));
	}

	@Override
	public void enterPixelsize(ICSSParser.PixelsizeContext ctx) {
		super.enterPixelsize(ctx);
		attachLiteral(new PixelLiteral(ctx.getText()));
	}

	@Override
	public void enterScalar(ICSSParser.ScalarContext ctx) {
		super.enterScalar(ctx);
		attachLiteral(new ScalarLiteral(ctx.getText()));
	}

}