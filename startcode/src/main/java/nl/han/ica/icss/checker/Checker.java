package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Checker {

    //This thing is to store the types of variables, variable names go into String and the variable's type goes into expressionType
    //It is a linked list to account for scopes. Every scope entered means another item and every scope exited means an item removed
    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    private HashMap<String, HashMap<ExpressionType, Boolean>> propertyInputTypes = new HashMap<>();

    public void check(AST ast) {
         variableTypes = new HanLinkedList<>();
         establishTypes(propertyInputTypes);
         //Problem analysis time!! Welcome to another episode of problem analysis where Mees will think on her feet
         //ast is an unchecked AST
        //Checking is finding errors anywhere in this AST and adding them to any Node in question
        //These nodes will then be collected, added to a list, and displayed on the frontend
        //In this method we will check this AST recursively
        //The first node in AST will always be stylesheet, and everything below that can be a tree
        //the basecase in level 0 will be a declaration in the body of a stylerule
        ArrayList<ASTNode> styleRules = ast.root.getChildren();
        for (ASTNode astNode : styleRules){
            checkValueCorrectness(astNode);
        }
    }

    private void checkValueCorrectness(ASTNode stylerule) {
        ArrayList<ASTNode> children =  stylerule.getChildren();
        for (ASTNode astNode : children){
            if (!(astNode instanceof Declaration)){
                continue;
            }
            Declaration declaration = (Declaration) astNode;
            HashMap<ExpressionType, Boolean> tolerableValues = propertyInputTypes.get(declaration.property.name.toLowerCase());
            //TODO make this polymorphic too
                if (!tolerableValues.getOrDefault(declaration.expression.getType(),Boolean.FALSE)) {
                    astNode.setError("Value of property \"" + declaration.property.name + "\" doesn't accept expressions of type \"" + declaration.expression.getType() + "\".");
                }
        }
    }

    private void establishTypes(HashMap<String, HashMap<ExpressionType, Boolean>> properties){
        HashMap<ExpressionType, Boolean> width = new HashMap();
        width.put(ExpressionType.PIXEL,Boolean.TRUE);
        width.put(ExpressionType.PERCENTAGE,Boolean.TRUE);
        properties.put("width",width);

        HashMap<ExpressionType, Boolean> color = new HashMap();
        color.put(ExpressionType.COLOR,Boolean.TRUE);
        properties.put("color",color);

        HashMap<ExpressionType, Boolean> backgroundColor = new HashMap();
        backgroundColor.put(ExpressionType.COLOR,Boolean.TRUE);
        properties.put("background-color",backgroundColor);
    }
}
