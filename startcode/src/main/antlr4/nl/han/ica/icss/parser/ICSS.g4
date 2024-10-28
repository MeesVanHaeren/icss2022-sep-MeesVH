grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';





//--- PARSER: ---
//Level 0:
stylesheet: styleObject+ EOF;

styleObject: variableAssignment | styleRule;

styleRule: selector OPEN_BRACE styleRuleObject+ CLOSE_BRACE;

selector: tagSelector | classSelector | idSelector;
tagSelector: name;
classSelector: CLASS_IDENT;
idSelector: ID_IDENT;

declaration: property COLON expression SEMICOLON;

property: name;

expression:     literal #literalValue |
                variableReference #variableValue|
                NOT_OPERATOR expression #inversion|
                expression MUL expression #multiplication|
                expression PLUS expression #addition |
                expression MIN expression #subtraction |
                expression LESSER_OPERATOR expression #lesserComparison |
                expression GREATER_OPERATOR expression #greaterComparison |
                expression EQ_GREATER_OPERATOR expression #eqLesserComparison |
                expression EQ_LESSER_OPERATOR expression #eqGreaterComparison |
                expression EQUAL_OPERATOR expression #equalComparison |
                expression NOT_EQUAL_OPERATOR expression #notComparion
                ;

literal:  booleann #bool | PIXELSIZE #pixelsize | PERCENTAGE #percentage | COLOR #color | SCALAR #scalar;

booleann: TRUE | FALSE;

//Level 1:
variableAssignment: variableReference ASSIGNMENT_OPERATOR expression SEMICOLON;

variableReference: name;

name : LOWER_IDENT | CAPITAL_IDENT;

//level 3:
styleRuleObject: declaration | branch | variableAssignment;

branch: ifBranch elseBranch?;

ifBranch: IF BOX_BRACKET_OPEN variableReference BOX_BRACKET_CLOSE OPEN_BRACE styleRuleObject+ CLOSE_BRACE;

elseBranch: ELSE OPEN_BRACE styleRuleObject+ CLOSE_BRACE;

//Boolean operators:

LESSER_OPERATOR: '<';
GREATER_OPERATOR: '>';
EQUAL_OPERATOR: '==';
NOT_EQUAL_OPERATOR: '!=';
EQ_LESSER_OPERATOR: '<=';
EQ_GREATER_OPERATOR: '>=';
NOT_OPERATOR: '!';


