package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HanLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;


public class Checker {
    //Da rules::
    //Pixel * Pixel is NOT allowed, because ddurr it's not
    //Percentage * percentage is also NOT allowed, I mean you can totally do it but not in here


    //This thing is to store the types of variables, variable names go into String and the variable's type goes into expressionType
    //It is a linked list to account for scopes. Every scope entered means another item and every scope exited means an item removed
    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    private HashMap<String, HashMap<ExpressionType, Boolean>> propertyInputTypes = new HashMap<>();

    public void check(AST ast) {
         variableTypes = new HanLinkedList<>();
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
        //TODO
    }

    private void checkStylerule(Stylerule stylerule){
        stylerule.body.forEach(this::checkBody);
//        stylerule.selectors.forEach(this::checkSelector);
    }

    private void checkBody(ASTNode bodyNode){
        if(bodyNode instanceof Declaration){
            checkDeclaration((Declaration) bodyNode);
        }
    }

    private void checkDeclaration(Declaration declaration) {
            HashMap<ExpressionType, Boolean> tolerableValues = propertyInputTypes.get(declaration.property.name.toLowerCase());
            if (!tolerableValues.getOrDefault(declaration.expression.getType(),Boolean.FALSE)) {
                declaration.setError(
                        "Value of property \"" + declaration.property.name +
                        "\" doesn't accept expressions of type \"" + declaration.expression.getType() +
                        "\".");
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
