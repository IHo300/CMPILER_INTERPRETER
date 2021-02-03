package GeneratedAntlrClasses;// Generated from Thanos.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ThanosParser}.
 */
public interface ThanosListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ThanosParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(ThanosParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(ThanosParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#genericSelection}.
	 * @param ctx the parse tree
	 */
	void enterGenericSelection(ThanosParser.GenericSelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#genericSelection}.
	 * @param ctx the parse tree
	 */
	void exitGenericSelection(ThanosParser.GenericSelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#genericAssocList}.
	 * @param ctx the parse tree
	 */
	void enterGenericAssocList(ThanosParser.GenericAssocListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#genericAssocList}.
	 * @param ctx the parse tree
	 */
	void exitGenericAssocList(ThanosParser.GenericAssocListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#genericAssociation}.
	 * @param ctx the parse tree
	 */
	void enterGenericAssociation(ThanosParser.GenericAssociationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#genericAssociation}.
	 * @param ctx the parse tree
	 */
	void exitGenericAssociation(ThanosParser.GenericAssociationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(ThanosParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(ThanosParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentExpressionList(ThanosParser.ArgumentExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#argumentExpressionList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentExpressionList(ThanosParser.ArgumentExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(ThanosParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(ThanosParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(ThanosParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(ThanosParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(ThanosParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#castExpression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(ThanosParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(ThanosParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(ThanosParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(ThanosParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(ThanosParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(ThanosParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(ThanosParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(ThanosParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(ThanosParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(ThanosParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(ThanosParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(ThanosParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(ThanosParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(ThanosParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(ThanosParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(ThanosParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(ThanosParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(ThanosParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(ThanosParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(ThanosParser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(ThanosParser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(ThanosParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(ThanosParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(ThanosParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(ThanosParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(ThanosParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(ThanosParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(ThanosParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(ThanosParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(ThanosParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(ThanosParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ThanosParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ThanosParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantExpression(ThanosParser.ConstantExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#constantExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantExpression(ThanosParser.ConstantExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ThanosParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ThanosParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifiers(ThanosParser.DeclarationSpecifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declarationSpecifiers}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifiers(ThanosParser.DeclarationSpecifiersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declarationSpecifiers2}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifiers2(ThanosParser.DeclarationSpecifiers2Context ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declarationSpecifiers2}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifiers2(ThanosParser.DeclarationSpecifiers2Context ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationSpecifier(ThanosParser.DeclarationSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declarationSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationSpecifier(ThanosParser.DeclarationSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclaratorList(ThanosParser.InitDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclaratorList(ThanosParser.InitDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclarator(ThanosParser.InitDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclarator(ThanosParser.InitDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterStorageClassSpecifier(ThanosParser.StorageClassSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitStorageClassSpecifier(ThanosParser.StorageClassSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifier(ThanosParser.TypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifier(ThanosParser.TypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structOrUnionSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterStructOrUnionSpecifier(ThanosParser.StructOrUnionSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structOrUnionSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitStructOrUnionSpecifier(ThanosParser.StructOrUnionSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structOrUnion}.
	 * @param ctx the parse tree
	 */
	void enterStructOrUnion(ThanosParser.StructOrUnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structOrUnion}.
	 * @param ctx the parse tree
	 */
	void exitStructOrUnion(ThanosParser.StructOrUnionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclarationList(ThanosParser.StructDeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclarationList(ThanosParser.StructDeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(ThanosParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(ThanosParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#specifierQualifierList}.
	 * @param ctx the parse tree
	 */
	void enterSpecifierQualifierList(ThanosParser.SpecifierQualifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#specifierQualifierList}.
	 * @param ctx the parse tree
	 */
	void exitSpecifierQualifierList(ThanosParser.SpecifierQualifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaratorList(ThanosParser.StructDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaratorList(ThanosParser.StructDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#structDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclarator(ThanosParser.StructDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#structDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclarator(ThanosParser.StructDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterEnumSpecifier(ThanosParser.EnumSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitEnumSpecifier(ThanosParser.EnumSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorList(ThanosParser.EnumeratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorList(ThanosParser.EnumeratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void enterEnumerator(ThanosParser.EnumeratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void exitEnumerator(ThanosParser.EnumeratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#enumerationConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumerationConstant(ThanosParser.EnumerationConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#enumerationConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumerationConstant(ThanosParser.EnumerationConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#atomicTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAtomicTypeSpecifier(ThanosParser.AtomicTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#atomicTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAtomicTypeSpecifier(ThanosParser.AtomicTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifier(ThanosParser.TypeQualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#typeQualifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifier(ThanosParser.TypeQualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSpecifier(ThanosParser.FunctionSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSpecifier(ThanosParser.FunctionSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#alignmentSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAlignmentSpecifier(ThanosParser.AlignmentSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#alignmentSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAlignmentSpecifier(ThanosParser.AlignmentSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(ThanosParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(ThanosParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterDirectDeclarator(ThanosParser.DirectDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#directDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitDirectDeclarator(ThanosParser.DirectDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#gccDeclaratorExtension}.
	 * @param ctx the parse tree
	 */
	void enterGccDeclaratorExtension(ThanosParser.GccDeclaratorExtensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#gccDeclaratorExtension}.
	 * @param ctx the parse tree
	 */
	void exitGccDeclaratorExtension(ThanosParser.GccDeclaratorExtensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#gccAttributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterGccAttributeSpecifier(ThanosParser.GccAttributeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#gccAttributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitGccAttributeSpecifier(ThanosParser.GccAttributeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#gccAttributeList}.
	 * @param ctx the parse tree
	 */
	void enterGccAttributeList(ThanosParser.GccAttributeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#gccAttributeList}.
	 * @param ctx the parse tree
	 */
	void exitGccAttributeList(ThanosParser.GccAttributeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#gccAttribute}.
	 * @param ctx the parse tree
	 */
	void enterGccAttribute(ThanosParser.GccAttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#gccAttribute}.
	 * @param ctx the parse tree
	 */
	void exitGccAttribute(ThanosParser.GccAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#nestedParenthesesBlock}.
	 * @param ctx the parse tree
	 */
	void enterNestedParenthesesBlock(ThanosParser.NestedParenthesesBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#nestedParenthesesBlock}.
	 * @param ctx the parse tree
	 */
	void exitNestedParenthesesBlock(ThanosParser.NestedParenthesesBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#pointer}.
	 * @param ctx the parse tree
	 */
	void enterPointer(ThanosParser.PointerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#pointer}.
	 * @param ctx the parse tree
	 */
	void exitPointer(ThanosParser.PointerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void enterTypeQualifierList(ThanosParser.TypeQualifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#typeQualifierList}.
	 * @param ctx the parse tree
	 */
	void exitTypeQualifierList(ThanosParser.TypeQualifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void enterParameterTypeList(ThanosParser.ParameterTypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#parameterTypeList}.
	 * @param ctx the parse tree
	 */
	void exitParameterTypeList(ThanosParser.ParameterTypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(ThanosParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(ThanosParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(ThanosParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(ThanosParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierList(ThanosParser.IdentifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#identifierList}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierList(ThanosParser.IdentifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(ThanosParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(ThanosParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#abstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterAbstractDeclarator(ThanosParser.AbstractDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#abstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitAbstractDeclarator(ThanosParser.AbstractDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#directAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterDirectAbstractDeclarator(ThanosParser.DirectAbstractDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#directAbstractDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitDirectAbstractDeclarator(ThanosParser.DirectAbstractDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void enterTypedefName(ThanosParser.TypedefNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void exitTypedefName(ThanosParser.TypedefNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(ThanosParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(ThanosParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void enterInitializerList(ThanosParser.InitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void exitInitializerList(ThanosParser.InitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#designation}.
	 * @param ctx the parse tree
	 */
	void enterDesignation(ThanosParser.DesignationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#designation}.
	 * @param ctx the parse tree
	 */
	void exitDesignation(ThanosParser.DesignationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#designatorList}.
	 * @param ctx the parse tree
	 */
	void enterDesignatorList(ThanosParser.DesignatorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#designatorList}.
	 * @param ctx the parse tree
	 */
	void exitDesignatorList(ThanosParser.DesignatorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#designator}.
	 * @param ctx the parse tree
	 */
	void enterDesignator(ThanosParser.DesignatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#designator}.
	 * @param ctx the parse tree
	 */
	void exitDesignator(ThanosParser.DesignatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#staticAssertDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStaticAssertDeclaration(ThanosParser.StaticAssertDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#staticAssertDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStaticAssertDeclaration(ThanosParser.StaticAssertDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ThanosParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ThanosParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void enterLabeledStatement(ThanosParser.LabeledStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void exitLabeledStatement(ThanosParser.LabeledStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(ThanosParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(ThanosParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void enterBlockItemList(ThanosParser.BlockItemListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#blockItemList}.
	 * @param ctx the parse tree
	 */
	void exitBlockItemList(ThanosParser.BlockItemListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void enterBlockItem(ThanosParser.BlockItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#blockItem}.
	 * @param ctx the parse tree
	 */
	void exitBlockItem(ThanosParser.BlockItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(ThanosParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(ThanosParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(ThanosParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(ThanosParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#comparisonStatement}.
	 * @param ctx the parse tree
	 */
	void enterComparisonStatement(ThanosParser.ComparisonStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#comparisonStatement}.
	 * @param ctx the parse tree
	 */
	void exitComparisonStatement(ThanosParser.ComparisonStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void enterComparisonOperator(ThanosParser.ComparisonOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#comparisonOperator}.
	 * @param ctx the parse tree
	 */
	void exitComparisonOperator(ThanosParser.ComparisonOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(ThanosParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(ThanosParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void enterForCondition(ThanosParser.ForConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#forCondition}.
	 * @param ctx the parse tree
	 */
	void exitForCondition(ThanosParser.ForConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void enterForExpression(ThanosParser.ForExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#forExpression}.
	 * @param ctx the parse tree
	 */
	void exitForExpression(ThanosParser.ForExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(ThanosParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(ThanosParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(ThanosParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(ThanosParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(ThanosParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(ThanosParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExternalDeclaration(ThanosParser.ExternalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExternalDeclaration(ThanosParser.ExternalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(ThanosParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(ThanosParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThanosParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(ThanosParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThanosParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(ThanosParser.DeclarationListContext ctx);
}