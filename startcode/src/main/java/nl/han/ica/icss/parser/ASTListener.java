package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

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
		currentContainer = new HANStack<>();
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
		currentContainer.peek().addChild(declaration);
	}
	
	@Override
	public void enterProperty(ICSSParser.PropertyContext ctx) {
		super.enterProperty(ctx);
		Declaration currentDeclaration = (Declaration) currentContainer.peek();
		currentDeclaration.property = new PropertyName(ctx.getText());
	}

	//Values
	private void attachLiteral(Literal literal){
		ASTNode context = currentContainer.peek();
		context.addChild(literal);
	}

	@Override
	public void enterColor(ICSSParser.ColorContext ctx) {
		super.enterColor(ctx);
		attachLiteral(new ColorLiteral(ctx.getText()));
	}

	@Override
	public void enterBooleann(ICSSParser.BooleannContext ctx) {
		super.enterBooleann(ctx);
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


	//variableHandler
	@Override
	public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		super.enterVariableAssignment(ctx);
		currentContainer.push(new VariableAssignment());
	}

	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		super.exitVariableAssignment(ctx);
		VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();
		//Scope check. If anything is in currentcontainer it means it's an inline variable assignment
		//Which belongs to a stylerule, otherwise it goes into the AST's root
		if (currentContainer.peek() == null){
			ast.root.addChild(variableAssignment);
		} else {
			currentContainer.peek().addChild(variableAssignment);
		}
	}

	@Override
	public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		super.enterVariableReference(ctx);
		currentContainer.push(new VariableReference(ctx.getText()));
	}

	@Override
	public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
		super.exitVariableReference(ctx);
		VariableReference variableReference = (VariableReference) currentContainer.pop();
		currentContainer.peek().addChild(variableReference);
	}

	@Override
	public void enterVariableValue(ICSSParser.VariableValueContext ctx) {
		super.enterVariableValue(ctx);
	}

	//Expression
	@Override
	public void enterAddition(ICSSParser.AdditionContext ctx) {
		super.enterAddition(ctx);
		currentContainer.push(new AddOperation());
	}

	@Override
	public void exitAddition(ICSSParser.AdditionContext ctx) {
		super.exitAddition(ctx);
		AddOperation addOperation = (AddOperation) currentContainer.pop();
		currentContainer.peek().addChild(addOperation);
	}

	@Override
	public void enterSubtraction(ICSSParser.SubtractionContext ctx) {
		super.enterSubtraction(ctx);
		currentContainer.push(new SubtractOperation());
	}

	@Override
	public void exitSubtraction(ICSSParser.SubtractionContext ctx) {
		super.exitSubtraction(ctx);
		SubtractOperation subtractOperation = (SubtractOperation) currentContainer.pop();
		currentContainer.peek().addChild(subtractOperation);
	}

	@Override
	public void enterMultiplication(ICSSParser.MultiplicationContext ctx) {
		super.enterMultiplication(ctx);
		currentContainer.push(new MultiplyOperation());
	}

	@Override
	public void exitMultiplication(ICSSParser.MultiplicationContext ctx) {
		super.exitMultiplication(ctx);
		MultiplyOperation multiplyOperation = (MultiplyOperation) currentContainer.pop();
		currentContainer.peek().addChild(multiplyOperation);
	}

	//If-Else
	@Override
	public void enterIfBranch(ICSSParser.IfBranchContext ctx) {
		super.enterIfBranch(ctx);
		currentContainer.push(new IfClause());
	}

	@Override
	public void exitIfBranch(ICSSParser.IfBranchContext ctx) {
		super.exitIfBranch(ctx);
		IfClause ifClause = (IfClause) currentContainer.pop();
		currentContainer.peek().addChild(ifClause);
	}

	@Override
	public void enterElseBranch(ICSSParser.ElseBranchContext ctx) {
		super.enterElseBranch(ctx);
		currentContainer.push(new ElseClause());
	}

	@Override
	public void exitElseBranch(ICSSParser.ElseBranchContext ctx) {
		super.exitElseBranch(ctx);
		ElseClause elseClause = (ElseClause) currentContainer.pop();
		IfClause ifClause = (IfClause) currentContainer.peek();
		ifClause.appendElse(elseClause);
	}
}