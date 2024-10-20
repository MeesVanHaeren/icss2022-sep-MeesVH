package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IfClause extends ASTNode {


    public Expression conditionalExpression;
    public ArrayList<ASTNode> body = new ArrayList<>();
    public ElseClause elseClause;

    public IfClause() { }

    public IfClause(Expression conditionalExpression, ArrayList<ASTNode> body) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
    }
    public IfClause(Expression conditionalExpression, ArrayList<ASTNode> body, ElseClause elseClause) {

        this.conditionalExpression = conditionalExpression;
        this.body = body;
        this.elseClause = elseClause;
    }

    @Override
    public String getNodeLabel() {
        return "If_Clause";
    }
    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(conditionalExpression);
        children.addAll(body);
        if (elseClause!=null)
            children.add(elseClause);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(child instanceof Expression)
            //I am not using this line but may not modify it :P
            conditionalExpression  = (Expression) child;
        else if (child instanceof ElseClause)
            elseClause = (ElseClause) child;
        else
            body.add(child);

        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IfClause ifClause = (IfClause) o;
        if (this.elseClause == null)
            return Objects.equals(conditionalExpression, ifClause.getConditionalExpression()) &&
                Objects.equals(body, ifClause.body);
        else
            return Objects.equals(conditionalExpression, ifClause.getConditionalExpression()) &&
                    Objects.equals(body, ifClause.body) &&
                    Objects.equals(elseClause, ifClause.elseClause);

    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionalExpression, body, elseClause);
    }

    public Expression getConditionalExpression() {
        return conditionalExpression;
    }
    public ElseClause getElseClause() { return elseClause; }

    //Meesmade method. This method adds an else to the lowest if it can find under this clause, which does include this clause as well
    public void appendElse(ElseClause elseClause) {
        //Gets all ifClauses in the body
        List<ASTNode> ifClauses = body.stream().filter(astNode -> astNode instanceof IfClause).collect(Collectors.toList());
        if (ifClauses.isEmpty()){
            //Basecase! If there are no ifClauses in the body else is put in elseClause
            this.elseClause = elseClause;
        } else {
            //If there IS more than none, get the last, and call this method on it.
            IfClause lastIfClause = (IfClause) ifClauses.get(ifClauses.size()-1);
            lastIfClause.appendElse(elseClause);
        }
    }
}
