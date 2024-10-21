package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        variableValues.addFirst(new HashMap<>());
        ArrayList<ASTNode> styleObjects = ast.root.getChildren();
        for (ASTNode styleObject : styleObjects) {
            if (styleObject instanceof VariableAssignment){
                storeVariableReferenceExpression((VariableAssignment) styleObject);
            }
            else if (styleObject instanceof Stylerule) {
                evaluateStyleRule((Stylerule) styleObject);
            }
        }
    }

    //Semi-walker methods
    private void evaluateStyleRule(Stylerule stylerule){
        introduceScope();
        stylerule.body.forEach(this::evaluateBody);
        removeScope();
    }

    private void evaluateBody(ASTNode bodyNode){
        if(bodyNode instanceof Declaration){
            ((Declaration) bodyNode).expression = evaluateExpression(((Declaration) bodyNode).expression);
        } else if (bodyNode instanceof IfClause) {
            evaluateIfClause((IfClause) bodyNode);
        } else if (bodyNode instanceof VariableAssignment) {
            storeVariableReferenceExpression((VariableAssignment) bodyNode);
        }
    }

    private void evaluateIfClause(IfClause ifClause) {
        ifClause.conditionalExpression = evaluateExpression(ifClause.conditionalExpression);
        introduceScope();
        ifClause.body.forEach(this::evaluateBody);
        removeScope();
        if (ifClause.elseClause != null){
            evaluateElseClause(ifClause.elseClause);
        }
    }

    private void evaluateElseClause(ElseClause elseClause) {
        introduceScope();
        elseClause.body.forEach(this::evaluateBody);
        removeScope();
    }

    //Real evalhead methods
    private Literal evaluateExpression(Expression expression){
        if (expression instanceof Literal) {
            return (Literal) expression;
        } else if (expression instanceof VariableReference){
            return evaluateExpression(getVariableReferenceExpression((VariableReference) expression));
        }else if (expression instanceof Operation){
            return evaluateOperation((Operation) expression);
        }  else {
            //This is here because I felt gross casting the else to Literal
            return null;
        }
    }

    private Expression getVariableReferenceExpression(VariableReference variableReference){
        //remember that the highest scope in the values list is the one that needs to take priority
        //TODO: ask if I may change this datastructure
        //TODO: Currently this algorithm is really really inefficient, like, n2 inefficient, but scopes shouldnt go very high anyway
        Expression variableValue = null;
        for (int i = variableValues.getSize()-1; i >= 0; i--) {
            variableValue = variableValues.get(i).get(variableReference.name);
        }
        return variableValue;
    }

    private void storeVariableReferenceExpression(VariableAssignment variableAssignment) {
        variableValues.get(variableValues.getSize()-1).put(variableAssignment.name.name,evaluateExpression(variableAssignment.expression));
    }

    private Literal evaluateOperation(Operation operation){
        Literal left = evaluateExpression(operation.lhs);
        Literal right = evaluateExpression(operation.rhs);
        operation.lhs = left;
        operation.rhs = right;
        //This isn't polymorphic because this really shouldnt be called from an unchecked context, so delegating this to
        //The operations like in the calculator assignment doesn't seem responsible. There is probably a
        // software pattern that solves this but I also have 6 deadlines next friday so I will not look into it.
        int result = 0;
        if (operation instanceof AddOperation){
            result = left.getValue() + right.getValue();
        } else if (operation instanceof MultiplyOperation) {
            result = left.getValue() * right.getValue();
        } else if (operation instanceof SubtractOperation){
            result = left.getValue() - right.getValue();
        }
        //TODO turn into factory pattern
        Literal returnLiteral = null;
        switch (getSignificantOperationType(left,right)){
            case SCALAR:
                returnLiteral = new ScalarLiteral(result);
                break;
            case PERCENTAGE:
                returnLiteral = new PercentageLiteral(result);
            case PIXEL:
                returnLiteral = new PixelLiteral(result);
        }
        return returnLiteral;
    }

    //Scope methods
    private void introduceScope(){
        variableValues.insert(variableValues.getSize(), new HashMap<>());
    }

    private void removeScope(){
        variableValues.delete(variableValues.getSize()-1);
    }

    private ExpressionType getSignificantOperationType(Literal left, Literal right){
        //Method to find which of the two input variables is "significant" (not scalar)
        // (a pixel, percentage or whatever else)
        Literal significant = left;
        if (left instanceof ScalarLiteral){
            significant = right;
        }
        return significant.getType();
    }
}
