package nl.han.ica.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.InversionOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.operations.comparisons.*;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.SemanticError;
import nl.han.ica.icss.parser.Fixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class CheckerTest {

    private AST testAst;
    private Checker sut;

    @BeforeEach
    public void setup(){
        testAst = new AST();
        sut = new Checker();
    }

    @Test
    public void propertyValueCorrect(){
        //Arrange
        Declaration declaration = new Declaration("background-color");
        declaration.addChild(new ColorLiteral("#ffffff"));

        Stylesheet stylesheet = new Stylesheet();
		/*
		p {
			background-color: #ffffff;
			width: 500px;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild(declaration)
                .addChild((new Declaration("width"))
                        .addChild(new PixelLiteral("500px")))
        );
        testAst.root = stylesheet;
        //Act
        sut.check(testAst);
        //Assert
        Assertions.assertNull(declaration.getError());
    }

    @Test
    public void propertyValueMismatch(){
        //Arrange
        Declaration declaration = new Declaration("background-color");
        declaration.addChild(new PixelLiteral("100px"));

        Stylesheet stylesheet = new Stylesheet();
		/*
		p {
			background-color: #ffffff;
			width: 500px;
		}
		*/
        stylesheet.addChild((new Stylerule())
                .addChild(new TagSelector("p"))
                .addChild(declaration)
                .addChild((new Declaration("width"))
                        .addChild(new PixelLiteral("500px")))
        );
        testAst.root = stylesheet;
        //Act
        sut.check(testAst);
        //Assert
        Assertions.assertEquals("ERROR: Value of property \"background-color\" doesn't accept expressions of type \"PIXEL\".",declaration.getError().toString());
    }

    @Test
    public void expressionCheckLiteralCorrect(){
        //Arrange
        AddOperation        operation1 = new AddOperation();
        MultiplyOperation   operation2 = new MultiplyOperation();
        SubtractOperation   operation3 = new SubtractOperation();
        InversionOperation  operation4 = new InversionOperation();
        LesserComparison    operation5 = new LesserComparison();
        GreaterComparison   operation6 = new GreaterComparison();
        EqGreaterComparison operation7 = new EqGreaterComparison();
        EqLesserComparsion  operation8 = new EqLesserComparsion();
        EqualComparsion     operation9 = new EqualComparsion();
        NotComparsion       operation10= new NotComparsion();

        operation1.addChild(new PixelLiteral(5)).addChild(new PixelLiteral(5));
        operation2.addChild(new ScalarLiteral(5)).addChild(new ScalarLiteral(5));
        operation3.addChild(new PercentageLiteral(5)).addChild(new PercentageLiteral(5));
        operation4.addChild(new BoolLiteral(Boolean.TRUE));
        operation5.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));
        operation6.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));
        operation7.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));
        operation8.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));
        operation9.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));
        operation10.addChild(new ScalarLiteral(4)).addChild(new ScalarLiteral(10));



        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable1"))
                .addChild(operation1)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable2"))
                .addChild(operation2)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable3"))
                .addChild(operation3)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable4"))
                .addChild(operation4)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable5"))
                .addChild(operation5)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable6"))
                .addChild(operation6)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable7"))
                .addChild(operation7)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable8"))
                .addChild(operation8)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable9"))
                .addChild(operation9)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable10"))
                .addChild(operation10)
        );
        testAst.root = stylesheet;
        //Act
        sut.check(testAst);
        //Assert
        Assertions.assertNull(operation1.getError());
        Assertions.assertNull(operation2.getError());
        Assertions.assertNull(operation3.getError());
        Assertions.assertNull(operation4.getError());
        Assertions.assertNull(operation5.getError());
        Assertions.assertNull(operation6.getError());
        Assertions.assertNull(operation7.getError());
        Assertions.assertNull(operation8.getError());
        Assertions.assertNull(operation9.getError());
        Assertions.assertNull(operation10.getError());
    }

    @Test
    public void expressionsColorIncorrect() {
        //Arrange
        AddOperation        operation1 = new AddOperation();
        MultiplyOperation   operation2 = new MultiplyOperation();
        SubtractOperation   operation3 = new SubtractOperation();
        InversionOperation  operation4 = new InversionOperation();
        LesserComparison    operation5 = new LesserComparison();
        GreaterComparison   operation6 = new GreaterComparison();
        EqGreaterComparison operation7 = new EqGreaterComparison();
        EqLesserComparsion  operation8 = new EqLesserComparsion();

        operation1.addChild(new PixelLiteral(5)).addChild(new ColorLiteral("#000000"));
        operation2.addChild(new ScalarLiteral(5)).addChild(new ColorLiteral("#000000"));
        operation3.addChild(new PercentageLiteral(5)).addChild(new ColorLiteral("#000000"));
        operation4.addChild(new ColorLiteral("#000000"));
        operation5.addChild(new ColorLiteral("#000000")).addChild(new ColorLiteral("#000000"));
        operation6.addChild(new ColorLiteral("#000000")).addChild(new ColorLiteral("#000000"));
        operation7.addChild(new ColorLiteral("#000000")).addChild(new ColorLiteral("#000000"));
        operation8.addChild(new ColorLiteral("#000000")).addChild(new ColorLiteral("#000000"));



        Stylesheet stylesheet = new Stylesheet();
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable1"))
                .addChild(operation1)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable2"))
                .addChild(operation2)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable3"))
                .addChild(operation3)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable4"))
                .addChild(operation4)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable5"))
                .addChild(operation5)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable6"))
                .addChild(operation6)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable7"))
                .addChild(operation7)
        );
        stylesheet.addChild((new VariableAssignment())
                .addChild(new VariableReference("Variable8"))
                .addChild(operation8)
        );
        testAst.root = stylesheet;
        String semanticErrorExpected = "ERROR: Operation applied to expression of incorrect type";
        //Act
        sut.check(testAst);
        //Assert
        Assertions.assertEquals(semanticErrorExpected,operation1.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation2.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation3.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation4.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation5.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation6.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation7.getError().toString());
        Assertions.assertEquals(semanticErrorExpected,operation8.getError().toString());
    }
}
