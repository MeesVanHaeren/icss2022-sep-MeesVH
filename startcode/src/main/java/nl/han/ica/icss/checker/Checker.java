package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;


public class Checker {
    //semi arbitrary rules as explained in a demo:
    //Pixel * Pixel is NOT allowed
    //Percentage * percentage is also NOT allowed


    //This thing is to store the types of variables, variable names go into String and the variable's type goes into expressionType
    //It is a linked list to account for scopes. Every scope entered means another item and every scope exited means an item removed
    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    private HashMap<String, HashMap<ExpressionType, Boolean>> propertyInputTypes = new HashMap<>();

    public void check(AST ast) {
         variableTypes = new HANLinkedList<>();
         variableTypes.addFirst(new HashMap<>());
         establishTypes(propertyInputTypes);
         ArrayList<ASTNode> styleObjects = ast.root.getChildren();
        for (int i = 0; i < styleObjects.size(); i++) {
            if (styleObjects.get(i) instanceof VariableAssignment){
                checkVariableAssignment((VariableAssignment) styleObjects.get(i));
            }
            else if (styleObjects.get(i) instanceof Stylerule) {
                checkStylerule((Stylerule) styleObjects.get(i));
            }
        }
    }

    private void checkVariableAssignment(VariableAssignment variableAssignment){
        appendTypeAssignment(variableAssignment);
    }

    private void appendTypeAssignment(VariableAssignment variableAssignment) {
        variableAssignment.name.setType(variableAssignment.expression.getType());
        variableTypes.get(variableTypes.getSize()-1).put(variableAssignment.name.name, variableAssignment.expression.getType());
    }

    private void checkStylerule(Stylerule stylerule){
        introduceScope();
        stylerule.body.forEach(this::checkBody);
//        stylerule.selectors.forEach(this::checkSelector);
        removeScope();
    }

    private void checkBody(ASTNode bodyNode){
        if(bodyNode instanceof Declaration){
            checkDeclaration((Declaration) bodyNode);
        } else if (bodyNode instanceof IfClause) {
            checkIfClause((IfClause) bodyNode);
        } else if (bodyNode instanceof VariableAssignment) {
            checkVariableAssignment((VariableAssignment) bodyNode);
        }
    }

    private void checkIfClause(IfClause ifClause) {
        introduceScope();
        //TODO extract into method for duplicate use in declaration
//        if (ifClause.conditionalExpression instanceof VariableReference){
//            ((VariableReference) ifClause.conditionalExpression).setType(getScopedVariableType(((VariableReference) ifClause.conditionalExpression).name));
//        }
        checkExpression(ifClause.conditionalExpression);
        if (ifClause.conditionalExpression.getType() != ExpressionType.BOOL && !ifClause.conditionalExpression.hasError()){
            ifClause.setError("given if clause with conditional expression isn't provided with an expression of a boolean type");
        }
        ifClause.body.forEach(this::checkBody);
        removeScope();
        if (ifClause.elseClause != null){
            checkElseClause(ifClause.elseClause);
        }
    }

    private void checkElseClause(ElseClause elseClause) {
        introduceScope();
        elseClause.body.forEach(this::checkBody);
        removeScope();
    }

    private void checkExpression(Expression expression) {
        if (expression instanceof VariableReference){
            checkVariableReference((VariableReference) expression);
        } else if (expression instanceof  Operation){
            checkOperation((Operation) expression);
        }
    }

    private void checkOperation(Operation oparation) {
        oparation.getChildren().forEach(expression -> checkExpression((Expression) expression));
        //TODO check validity of operation
    }

    private void checkVariableReference(VariableReference variableReference) {
        boolean containsKey = false;
        for (int i = 0; i < variableTypes.getSize(); i++) {
            if (variableTypes.get(i).containsKey(variableReference.name)){
                containsKey = true;
                break;
            }
        }
        if (!containsKey){
            variableReference.setError(
                    "Variable reference \"" + variableReference.name +
                            "\" has not been declared in this scope.");
        } else{
            variableReference.setType(getScopedVariableType(variableReference.name));
        }
    }

    private void checkDeclaration(Declaration declaration) {
            checkExpression(declaration.expression);
//            if (declaration.expression instanceof VariableReference){
//                ((VariableReference) declaration.expression).setType(getScopedVariableType(((VariableReference) declaration.expression).name));
//            }
            HashMap<ExpressionType, Boolean> tolerableValues = propertyInputTypes.get(declaration.property.name.toLowerCase());
            if (!tolerableValues.getOrDefault(declaration.expression.getType(),Boolean.FALSE)) {
                if (!declaration.expression.hasError()) {
                    declaration.setError(
                            "Value of property \"" + declaration.property.name +
                                    "\" doesn't accept expressions of type \"" + declaration.expression.getType() +
                                    "\".");
                }
            }
        }

    private ExpressionType getScopedVariableType(String name) {
        ExpressionType expressionType = null;
        for (int i = 0; i < variableTypes.getSize(); i++) {
            if (variableTypes.get(i).containsKey(name)){
                expressionType = variableTypes.get(i).get(name);
            }
        }
        return expressionType;
    }

    private void establishTypes(HashMap<String, HashMap<ExpressionType, Boolean>> properties){
        HashMap<ExpressionType, Boolean> width = new HashMap();
        width.put(ExpressionType.PIXEL,Boolean.TRUE);
        width.put(ExpressionType.PERCENTAGE,Boolean.TRUE);
        properties.put("width",width);
        properties.put("height",width);

        HashMap<ExpressionType, Boolean> color = new HashMap();
        color.put(ExpressionType.COLOR,Boolean.TRUE);
        properties.put("color",color);

        HashMap<ExpressionType, Boolean> backgroundColor = new HashMap();
        backgroundColor.put(ExpressionType.COLOR,Boolean.TRUE);
        properties.put("background-color",backgroundColor);
    }

    private void eliminateScope(){
        HashMap<String, ExpressionType> root = variableTypes.getFirst();
        variableTypes.clear();
        variableTypes.addFirst(root);
    }

    private void introduceScope(){
        variableTypes.insert(variableTypes.getSize(), new HashMap<>());
    }

    private void removeScope(){
        variableTypes.delete(variableTypes.getSize()-1);
    }
}
