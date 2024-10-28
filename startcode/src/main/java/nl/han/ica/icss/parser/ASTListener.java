package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.InversionOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.operations.comparisons.*;
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

	//A special variable that points to the last ifClause exited, so that an elseClause can be added to it.
	private IfClause lastIfClause = new IfClause();
	//It is done for ifClauses because if and else clauses uniquely relate to eachother spacially. An if-clause
	//on the tree has no clue if it has an else clause when exited, and once the if clause has been exited it becomes
	//relatively inaccessible. This was previously done with methods, but I personally find this implementation a little more clean,
	//even if it treats if-clauses in some unique way that it doesn't treat other objects.

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

	@Override
	public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
		super.exitTagSelector(ctx);
		genericExit();
	}

	@Override
	public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
		super.exitClassSelector(ctx);
		genericExit();
	}

	@Override
	public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
		super.exitIdSelector(ctx);
		genericExit();
	}

	//Adds a stylerule when appropriate and removes and adds it when it's done
	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		super.enterStyleRule(ctx);
		currentContainer.push(new Stylerule());
	}

	//Different from generic because it needs the root always
	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		super.exitStyleRule(ctx);
		genericExit();
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
		genericExit();
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

	//Different from generic exit because it needs the root sometimes
	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		super.exitVariableAssignment(ctx);
		genericExit();
	}

	@Override
	public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		super.enterVariableReference(ctx);
		currentContainer.push(new VariableReference(ctx.getText()));
	}

	@Override
	public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
		super.exitVariableReference(ctx);
		genericExit();
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
		genericExit();
	}

	@Override
	public void enterSubtraction(ICSSParser.SubtractionContext ctx) {
		super.enterSubtraction(ctx);
		currentContainer.push(new SubtractOperation());
	}

	@Override
	public void exitSubtraction(ICSSParser.SubtractionContext ctx) {
		super.exitSubtraction(ctx);
		genericExit();
	}

	@Override
	public void enterMultiplication(ICSSParser.MultiplicationContext ctx) {
		super.enterMultiplication(ctx);
		currentContainer.push(new MultiplyOperation());
	}

	@Override
	public void exitMultiplication(ICSSParser.MultiplicationContext ctx) {
		super.exitMultiplication(ctx);
		genericExit();
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
		lastIfClause = (IfClause) genericExit();
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
		lastIfClause.addChild(elseClause);
	}

	@Override
	public void exitBranch(ICSSParser.BranchContext ctx) {
		super.exitBranch(ctx);
		lastIfClause = new IfClause();
	}

	//Boolean operation
	@Override
	public void enterLesserComparison(ICSSParser.LesserComparisonContext ctx) {
		super.enterLesserComparison(ctx);
		currentContainer.push(new LesserComparison());
	}
	@Override
	public void exitLesserComparison(ICSSParser.LesserComparisonContext ctx) {
		super.exitLesserComparison(ctx);
		genericExit();
	}

	@Override
	public void enterGreaterComparison(ICSSParser.GreaterComparisonContext ctx) {
		super.enterGreaterComparison(ctx);
		currentContainer.push(new GreaterComparison());
	}

	@Override
	public void exitGreaterComparison(ICSSParser.GreaterComparisonContext ctx) {
		super.exitGreaterComparison(ctx);
		genericExit();
	}

	@Override
	public void enterEqLesserComparison(ICSSParser.EqLesserComparisonContext ctx) {
		super.enterEqLesserComparison(ctx);
		currentContainer.push(new EqLesserComparsion());
	}

	@Override
	public void exitEqLesserComparison(ICSSParser.EqLesserComparisonContext ctx) {
		super.exitEqLesserComparison(ctx);
		genericExit();
	}

	@Override
	public void enterEqGreaterComparison(ICSSParser.EqGreaterComparisonContext ctx) {
		super.enterEqGreaterComparison(ctx);
		currentContainer.push(new EqGreaterComparison());
	}

	@Override
	public void exitEqGreaterComparison(ICSSParser.EqGreaterComparisonContext ctx) {
		super.exitEqGreaterComparison(ctx);
		genericExit();
	}

	@Override
	public void enterEqualComparison(ICSSParser.EqualComparisonContext ctx) {
		super.enterEqualComparison(ctx);
		currentContainer.push(new EqualComparsion());
	}

	@Override
	public void exitEqualComparison(ICSSParser.EqualComparisonContext ctx) {
		super.exitEqualComparison(ctx);
		genericExit();
	}

	@Override
	public void enterNotComparion(ICSSParser.NotComparionContext ctx) {
		super.enterNotComparion(ctx);
		currentContainer.push(new NotComparsion());
	}

	@Override
	public void exitNotComparion(ICSSParser.NotComparionContext ctx) {
		super.exitNotComparion(ctx);
		genericExit();
	}

	@Override
	public void enterInversion(ICSSParser.InversionContext ctx) {
		super.enterInversion(ctx);
		currentContainer.push(new InversionOperation());
	}

	@Override
	public void exitInversion(ICSSParser.InversionContext ctx) {
		super.exitInversion(ctx);
		genericExit();
	}

	private ASTNode genericExit(){
		//Pops node from currentContainer and either adds it to the next top of stack or adds it to the root.
		ASTNode astNode = currentContainer.pop();
		if (currentContainer.peek() == null){
			ast.root.addChild(astNode);
		} else {
			currentContainer.peek().addChild(astNode);
		}
		return astNode;
	}
}