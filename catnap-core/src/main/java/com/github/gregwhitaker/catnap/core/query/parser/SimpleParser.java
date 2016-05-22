package com.github.gregwhitaker.catnap.core.query.parser;

import com.github.gregwhitaker.catnap.core.exception.QuerySyntaxException;
import com.github.gregwhitaker.catnap.core.query.QuerySyntax;
import com.github.gregwhitaker.catnap.core.query.model.SimpleExpression;
import com.github.gregwhitaker.catnap.core.query.model.SimpleQuery;

/**
 *
 */
public class SimpleParser implements Parser<SimpleQuery> {
    private static final char FIELD_DELIMITER = ',';
    private static final String SUBQUERY_OPEN = "(";
    private static final String SUBQUERY_CLOSE = ")";
    private static final char EXPRESSION_OPEN = '[';

    private final ExpressionParser<SimpleExpression> expressionParser = new SimpleExpressionParser();

    @Override
    public SimpleQuery parse(String expression) throws QuerySyntaxException {
        if(expression != null) {
            SimpleQuery query = new SimpleQuery();
            StringBuilder buffer = new StringBuilder(expression);

            while(buffer.length() > 0) {
                buildQuery(buffer, query);
            }

            return query;
        }

        return null;
    }

    private void buildQuery(StringBuilder buffer, SimpleQuery query) {
        StringBuilder field = new StringBuilder();
        SimpleQuery subQuery = null;
        SimpleExpression expression = null;
        int index = 0;

        while(true) {
            if(buffer.charAt(index) == FIELD_DELIMITER) {
                //Found end of expression
                index++;
                break;
            } else if(buffer.substring(index).startsWith(SUBQUERY_OPEN)) {
                //Found subquery
                int closeIndex = BracketMatcher.getClosingParenthesisIndex(buffer, index);

                //Recursively parse nested subquery
                subQuery = parse(buffer.substring(index + 1, closeIndex));

                //Check if the expression is missing a closing character
                if(buffer.length() > closeIndex + 1 && (buffer.charAt(closeIndex + 1) != FIELD_DELIMITER &&
                        buffer.charAt(closeIndex + 1) != EXPRESSION_OPEN)) {
                    throw new QuerySyntaxException(buffer.toString(),
                            String.format("Multiple subqueries must be separated by the '%s' character",
                                    String.valueOf(FIELD_DELIMITER)));
                }

                index = closeIndex + 1;
            } else if(buffer.charAt(index) == EXPRESSION_OPEN) {
                //Found expression
                int closeIndex = BracketMatcher.getClosingSquareBracketIndex(buffer, index);

                expression = expressionParser.parse(buffer.substring(index + 1, closeIndex));

                index = closeIndex + 1;
            } else {
                field.append(buffer.charAt(index));
                index++;
            }

            if(index >= buffer.length()) {
                break;
            }
        }

        buffer.delete(0, index);

        if(subQuery != null && expression != null) {
            //Subquery and Expression
            query.addParameter(field.toString(), subQuery, expression);
        } else if(subQuery != null) {
            //Subquery Only
            query.addParameter(field.toString(), subQuery);
        } else if(expression != null) {
            //Expression Only
            query.addParameter(field.toString(), expression);
        } else {
            //Default Field Selection
            query.addParameter(field.toString());
        }
    }

    @Override
    public QuerySyntax getSyntax() {
        return QuerySyntax.SIMPLE;
    }
}
