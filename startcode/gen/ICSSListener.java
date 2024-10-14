// Generated from /home/nellylorey/Documents/Han/APP/startcodeBCompiler/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void enterRuleSet(ICSSParser.RuleSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ruleSet}.
	 * @param ctx the parse tree
	 */
	void exitRuleSet(ICSSParser.RuleSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#tagSelector}.
	 * @param ctx the parse tree
	 */
	void enterTagSelector(ICSSParser.TagSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#tagSelector}.
	 * @param ctx the parse tree
	 */
	void exitTagSelector(ICSSParser.TagSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#classSelector}.
	 * @param ctx the parse tree
	 */
	void enterClassSelector(ICSSParser.ClassSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#classSelector}.
	 * @param ctx the parse tree
	 */
	void exitClassSelector(ICSSParser.ClassSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#idSelector}.
	 * @param ctx the parse tree
	 */
	void enterIdSelector(ICSSParser.IdSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#idSelector}.
	 * @param ctx the parse tree
	 */
	void exitIdSelector(ICSSParser.IdSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#styleRule}.
	 * @param ctx the parse tree
	 */
	void enterStyleRule(ICSSParser.StyleRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#styleRule}.
	 * @param ctx the parse tree
	 */
	void exitStyleRule(ICSSParser.StyleRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void enterPropertyName(ICSSParser.PropertyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void exitPropertyName(ICSSParser.PropertyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
}