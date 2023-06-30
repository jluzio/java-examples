package com.example.java.playground.lib.validator.jsr380;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class ValidationSpElHelper {

  private final ExpressionParser expressionParser;
  private final ParserContext parserContext;
  private final EvaluationContext evaluationContext;
  private final BeanExpressionContext expressionContext;

  public ValidationSpElHelper(ConfigurableBeanFactory beanFactory) {
    this.expressionParser = getExpressionParser();
    this.parserContext = new TemplateParserContext();
    this.evaluationContext = getStandardEvaluationContext(beanFactory);
    this.expressionContext = getExpressionContext(beanFactory);
  }

  public <T> T getValue(String expressionString, Class<T> desiredResultType) {
    Expression expression = expressionParser.parseExpression(expressionString, parserContext);
    return expression.getValue(evaluationContext, expressionContext, desiredResultType);
  }

  private ExpressionParser getExpressionParser() {
    SpelParserConfiguration config = new SpelParserConfiguration(
        // enable configuration of expressions
        SpelCompilerMode.IMMEDIATE,
        this.getClass().getClassLoader()
    );
    return new SpelExpressionParser(config);
  }

  @NotNull
  private StandardEvaluationContext getStandardEvaluationContext(
      ConfigurableBeanFactory beanFactory) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    context.setBeanResolver(new BeanFactoryResolver(beanFactory));
    context.addPropertyAccessor(new BeanExpressionContextAccessor());
    return context;
  }

  @NotNull
  private BeanExpressionContext getExpressionContext(ConfigurableBeanFactory beanFactory) {
    return new BeanExpressionContext(beanFactory, null);
  }

}
