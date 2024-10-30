# Opleverdocument B_compiler

student:	Mees van Haeren (2108419)  
klas:		ITN-ASD-A-s  
datum:	30 oktober 2024  
vak:		ASD-APP   
docent: Dennis Breuker  
versie:	1.0

## Behaalde eisen

### Algemeen

| ID   | Omschrijving                                                                                                                                                                                                                                                                                               | Prio | Punten | Competentie VT | Behaald |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | -------------- |---------|
| AL01 | De code behoudt de packagestructuur van de aangeleverde startcode. Toegevoegde code bevindt zich in de relevante packages.                                                                                                                                                                                 | Must | 0      | APP-6          | ✅        |
| AL02 | Alle code compileert en is te bouwen met Maven 3.6 of hoger, onder OpenJDK 13. Tip: controleer dit door **eerst** `mvn clean` uit te voeren alvorens te compileren en in te leveren, hierop een onvoldoende halen is echt zonde. **Gebruik van Oracle versies van Java is uitdrukkelijk niet toegestaan**. | Must | 0      | n.v.t.         | Openjdk-21 |
| AL03 | De code is goed geformatteerd, zo nodig voorzien van commentaar, correcte variabelenamen gebruikt, bevat geen onnodig ingewikkelde constructies en is zo onderhoudbaar mogelijk opgesteld. (naar oordeel van docent)                                                                                       | Must | 0      | n.v.t.         | ✅        |
| AL04 | De docent heeft vastgesteld (tijdens les, assessment of op een andere manier) dat de compiler eigen werk is en dat je voldoet aan de beoordelingscriteria van APP-6, te weten: - Kent de standaardarchitectuur van compilers; - Kent de basisbegrippen over programmeertalen (zoals syntaxis, semantiek).  | Must | 0      | APP-6          | ✅        |

In de code en source tree is soms commentaar geschreven die mijn denkproces beschrijft. Dit is achtergelaten om mijn denkproces te laten zien, ik weet niet of dit professioneel is of toegestaan is, maar voor transparantie is het achtergelaten. 

### Parser

| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                                                                                                                                          | Prio | Punten | Competentie VT | Behaald |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | -------------- |---------|
| PA00 | De parser dient zinvol gebruik te maken van **jouw** eigen implementatie van een stack generic voor `ASTNode` (VT: zie huiswerk `IHANStack<ASTNode>`)                                                                                                                                                                                                                                                                                                 | Must | 0      | APP-1, APP-9   | ✅       |
| PA01 | Implementeer een grammatica plus listener die AST’s kan maken voor ICSS documenten die “eenvoudige opmaak” kan parseren, zoals beschreven in de taalbeschrijving. In `level0.icss` vind je een voorbeeld van ICSS code die je moet kunnen parseren. `testParseLevel0()` slaagt.                                                                                                                                                                       | Must | 10     | APP-6, APP-7   | ✅       |
| PA02 | Breid je grammatica en listener uit zodat nu ook assignments van variabelen en het gebruik ervan geparseerd kunnen worden. In `level1.icss` vind je voorbeeldcode die je nu zou moeten kunnen parseren. `testParseLevel1()` slaagt.                                                                                                                                                                                                                   | Must | 10     | APP-6, APP-7   | ✅       |
| PA03 | Breid je grammatica en listener uit zodat je nu ook optellen en aftrekken en vermenigvuldigen kunt parseren. In `level2.icss` vind je voorbeeld- code die je nu ook zou moeten kunnen parseren. Houd hierbij rekening met de rekenregels (vermenigvuldigen gaat voor optellen en aftrekken, optellen en aftrekken gaan van links naar rechts; zie ook [deze site](https://www.beterrekenen.nl/website/index.php?pag=217).”`testParseLevel2()` slaagt. | Must | 10     | APP-6, APP-7   | ✅       |
| PA04 | Breid je grammatica en listener uit zodat je if/else-statements aankunt. In `level3.icss` vind je voorbeeldcode die je nu ook zou moeten kunnen parseren. `testParseLevel3()` slaagt.                                                                                                                                                                                                                                                                 | Must | 10     | APP-6, APP-7   | ✅       |
| PA05 | PA01 t/m PA04 leveren minimaal 30 punten op                                                                                                                                                                                                                                                                                                                                                                                                           | Must | 0      | nvt            | ✅?      |

### Checker

| ID   | Omschrijving                                                                                                                                                                                                                                                                         | Prio   | Punten | Competentie VT       | Behaald |
|------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------|--------|----------------------|---------|
| CH00 | Minimaal vier van onderstaande checks **moeten** zijn geïmplementeerd                                                                                                                                                                                                                | Must   | 0      | APP-2, APP-6, APP-7, | ✅       |
| CH01 | Controleer of er geen variabelen worden gebruikt die niet gedefinieerd zijn.                                                                                                                                                                                                         | Should | 5      |                      | ✅       |
| CH02 | Controleer of de operanden van de operaties plus en min van gelijk type zijn. Je mag geen pixels bij percentages optellen bijvoorbeeld. Controleer dat bij vermenigvuldigen minimaal een operand een scalaire waarde is. Zo mag `20% * 3` en `4 * 5` wel, maar mag `2px * 3px` niet. | Should | 5      |                      | ✅       |
| CH03 | Controleer of er geen kleuren worden gebruikt in operaties (plus, min en keer).                                                                                                                                                                                                      | Should | 5      |                      | ✅       |
| CH04 | Controleer of bij declaraties het type van de value klopt met de property. Declaraties zoals `width: #ff0000` of `color: 12px` zijn natuurlijk onzin.                                                                                                                                | Should | 5      |                      | ✅       |
| CH05 | Controleer of de conditie bij een if-statement van het type boolean is (zowel bij een variabele-referentie als een boolean literal)                                                                                                                                                  | Should | 5      |                      | ✅       |
| CH06 | Controleer of variabelen enkel binnen hun scope gebruikt worden                                                                                                                                                                                                                      | Must   | 5      |                      | ✅       |

### Transformer

| ID   | Omschrijving                                                                                                                                                                                                                                                                                                                                                                                                      | Prio | Punten | Competentie VT      | Behaald |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---- | ------ | ------------------- |---------|
| TR01 | Evalueer expressies. Schrijf een transformatie in ```Evaluator``` die alle `Expression` knopen in de AST door een `Literal` knoop met de berekende waarde vervangt.                                                                                                                                                                                                                                                 | Must | 10     | APP-2, APP-6, APP-7 |   ✅      |
| TR02 | Evalueer if/else expressies. Schrijf een transformatie in ```Evaluator``` die alle `IfClause`s uit de AST verwijdert. Wanneer de conditie van de `IfClause` `TRUE` is wordt deze vervangen door de body van het if-statement. Als de conditie `FALSE` is dan vervang je de `IfClause` door de body van de `ElseClause`. Als er geen `ElseClause` is bij een negatieve conditie dan verwijder je de `IfClause` volledig uit de AST. | Must | 10     | APP-2, APP-6, APP-7 |    ✅     |

Bij de transformer worden ook overbodige declarations verwijderd. De volgende code:

```css
    p {
    background-color: #ffffff;
    background-color: #000000;
}
```

Zal tot het volgende veranderd worden:

```css
    p {
    background-color: #000000;
}
```

Ook worden alle variable assignments weggelaten in de getransformeerde AST, omdat ze toch niet meer aangeroepen worden. 

### Generator

| ID   | Omschrijving                                                                                                        | Prio | Punten | Competentie VT      | Behaald |
| ---- | ------------------------------------------------------------------------------------------------------------------- | ---- | ------ | ------------------- |---------|
| GE01 | Implementeer de generator in `nl.han.ica.icss.generator.Generator` die de AST naar een CSS2-compliant string omzet. | Must | 5      | APP-2, APP-6, APP-7 |       ✅  |
| GE02 | Zorg dat de CSS met twee spaties inspringing per scopeniveau gegenereerd wordt.                                     | Must | 5      | APP-2, APP-6, APP-7 |        ✅ |

### Eigen uitbreiding

Hieronder is de eigen uitbreiding uitgewerkt als bij de andere eisen.  

De competenties zijn hier weggelaten, omdat deze eisen niet gemaakt zijn met het oog op competenties. Ook is er een gok gemaakt over de puntendistributie op basis van het besloten puntentotaal van 10 punten, en de moeite die nodig was om het te realiseren. 

| ID   | Omschrijving                                                                                                            | Prio   | Punten | Behaald |
|------|-------------------------------------------------------------------------------------------------------------------------|--------|--------|---------|
| EU01 | De boolean operaties <, >, <=, >=, \==, != en ! moeten geïmplementeerd worden in de grammatica en parser.               | Should | 2      | ✅       |
| EU02 | Bij de vergelijkingen <, >, <= en >= mogen alleen nummerwaardes vegeleken worden.                                       | Should | 1      | ✅       |
| EU03 | Bij de vergelijkingen <, >, <= , >=, != en == mogen alleen waardes van een gelijk type vergeleken worden.               | Should | 1      | ✅       |
| EU03 | De not-operatie (!) mag alleen uitgevoerd worden op boolean waardes.                                                    | Should | 1      | ✅       |
| EU03 | Boolean operaties moeten opgeslagen kunnen worden in variabelen, en moeten kunnen werken als conditie in een if-clause. | Should | 1      | ✅       |
| EU03 | De boolean operaties moeten in de transformer geëvalueerd worden tot een boolean-literal.                               | Should | 4      | ✅       |
