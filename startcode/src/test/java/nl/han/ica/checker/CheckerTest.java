package nl.han.ica.checker;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.checker.Checker;
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
}
