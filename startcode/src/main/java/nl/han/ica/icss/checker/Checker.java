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
//            if (!(declaration.expression instanceof Literal)){
//                continue;
//            }
            HashMap<ExpressionType, Boolean> tolerableValues = propertyInputTypes.get(declaration.property.name.toLowerCase());
            ExpressionType declarationsExpressionType = getExpressionType(declaration.expression);
            if (!tolerableValues.containsKey(declarationsExpressionType)){
                astNode.setError("Property value mismatch");
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

    private ExpressionType getExpressionType(Expression expression){
        if(expression instanceof BoolLiteral){
            return ExpressionType.BOOL;
        }
        if(expression instanceof ColorLiteral){
            return ExpressionType.COLOR;
        }
        if(expression instanceof PercentageLiteral){
            return ExpressionType.PERCENTAGE;
        }
        if(expression instanceof PixelLiteral){
            return ExpressionType.PIXEL;
        }
        if(expression instanceof ScalarLiteral){
            return ExpressionType.SCALAR;
        }
        return null;
    }
}
