package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;
    private HashMap<String,Integer> previousDeclarations;

    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        variableValues.addFirst(new HashMap<>());
        ArrayList<ASTNode> styleObjects = ast.root.getChildren();
        //While loop iterates over all the bodynodes and evaluates everything.
        //If what it encounters is no longer needed in the final product (variable assignments and branches)
        //It takes the relevant information, applies it, and removes it from the AST
        int i = 0;
        while (i < styleObjects.size()) {
            ASTNode styleObject = styleObjects.get(i);
            if (styleObject instanceof VariableAssignment){
                storeVariableReferenceExpression((VariableAssignment) styleObject);

                //The assignment never said variable assignments were supposed to be deleted
                //But I just think it looks way neater like this, because I'm already
                //Replacing all the references with literals, so the variable assignments
                //Are basically dead weight.
                styleObjects.remove(i);
            }
            else if (styleObject instanceof Stylerule) {
                evaluateStyleRule((Stylerule) styleObject);
                i++;
            } else {
                i++;
            }
        }
    }

    //Semi-walker methods
    private void evaluateStyleRule(Stylerule stylerule){
        introduceScope();
        //While loop because the length of the loop is dynamic.
        int i = 0;
        while (i < stylerule.body.size()){
            ASTNode bodyNode = stylerule.body.get(i);
            if(bodyNode instanceof Declaration){
                ((Declaration) bodyNode).expression = evaluateExpression(((Declaration) bodyNode).expression);
                //Removes any previous declarations of the same property, not described in the assignment
                //But it felt like an obvious thing that the evaluator should do
                if (previousDeclarations.containsKey(((Declaration) bodyNode).property.name)){
                    int previousIndex = previousDeclarations.get(((Declaration) bodyNode).property.name);
                    stylerule.body.remove(previousIndex);
                    previousDeclarations.replace((((Declaration) bodyNode).property.name),i);
                } else {
                    previousDeclarations.put(((Declaration) bodyNode).property.name,i);
                }
                i++;
            } else if (bodyNode instanceof VariableAssignment) {
                storeVariableReferenceExpression((VariableAssignment) bodyNode);
                stylerule.body.remove(i);
            } else if (bodyNode instanceof IfClause) {
                ArrayList<ASTNode> branchBodyNodes = extractBranch((IfClause) bodyNode);
                stylerule.body.remove(i);
                stylerule.body.addAll(i,branchBodyNodes);
            }
        }
        removeScope();
    }

    //Real evalhead methods
    private ArrayList<ASTNode> extractBranch(IfClause ifClause) {
        BoolLiteral condition = (BoolLiteral) evaluateExpression(ifClause.conditionalExpression);
        ifClause.conditionalExpression = condition;
        if (condition.value) {
            ifClause.elseClause = null;
            return ifClause.body;
        } else {
            if (ifClause.elseClause != null) {
                return ifClause.elseClause.body;
            } else {
                return new ArrayList<>();
            }
        }
    }

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
        Expression variableValue = null;
        for (int i = 0; i < variableValues.getSize(); i++) {
            if (variableValues.get(i).containsKey(variableReference.name)) {
                variableValue = variableValues.get(i).get(variableReference.name);
            }
        }
        return variableValue;
    }

    private void storeVariableReferenceExpression(VariableAssignment variableAssignment) {
        variableValues.get(variableValues.getSize()-1).put(variableAssignment.name.name,evaluateExpression(variableAssignment.expression));
    }

    private Literal evaluateOperation(Operation operation){
        operation.lhs = evaluateExpression(operation.lhs);
        operation.rhs = evaluateExpression(operation.rhs);

        return operation.getResult();
    }

    //Scope methods
    private void introduceScope(){
        variableValues.insert(variableValues.getSize(), new HashMap<>());
        previousDeclarations = new HashMap<>();
    }

    private void removeScope(){
        variableValues.delete(variableValues.getSize()-1);
    }

    private ExpressionType getSignificantOperationType(Literal left, Literal right){
        //Method to find which of the two input variables is "significant" (not scalar)
        // (a pixel, percentage or whatever else, the checker needs to get rid of those anyway)
        Literal significant = left;
        if (left instanceof ScalarLiteral){
            significant = right;
        }
        return significant.getType();
    }
}
