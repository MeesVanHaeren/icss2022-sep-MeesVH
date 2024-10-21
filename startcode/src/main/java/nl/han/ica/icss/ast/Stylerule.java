package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Stylerule extends ASTNode {
	
	public ArrayList<Selector> selectors = new ArrayList<>();
	public ArrayList<ASTNode> body = new ArrayList<>();

    public Stylerule() { }

    public Stylerule(Selector selector, ArrayList<ASTNode> body) {

    	this.selectors = new ArrayList<>();
    	this.selectors.add(selector);
    	this.body = body;
    }

    @Override
	public String getNodeLabel() {
		return "Stylerule";
	}
	@Override
	public ArrayList<ASTNode> getChildren() {
		ArrayList<ASTNode> children = new ArrayList<>();
		children.addAll(selectors);
		children.addAll(body);

		return children;
	}

    @Override
    public ASTNode addChild(ASTNode child) {
		if(child instanceof Selector)
			selectors.add((Selector) child);
		else
        	body.add(child);

		return this;
    }
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Stylerule stylerule = (Stylerule) o;
		return Objects.equals(selectors, stylerule.selectors) &&
				Objects.equals(body, stylerule.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(selectors, body);
	}

	//Meesmade method. duplicate from ifclause's appendElse except it only accounts for it's children
	public void appendElse(ElseClause elseClause) {
		//Gets all ifClauses in the body
		List<ASTNode> ifClauses = body.stream().filter(astNode -> astNode instanceof IfClause).collect(Collectors.toList());
		if (!ifClauses.isEmpty()){
			//If there IS more than none, get the last, and call this method on it.
			IfClause lastIfClause = (IfClause) ifClauses.get(ifClauses.size() - 1);
			lastIfClause.appendElse(elseClause);
		}
	}
}
