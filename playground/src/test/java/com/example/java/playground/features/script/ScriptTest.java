package com.example.java.playground.features.script;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ScriptTest {

  ScriptEngineManager manager = new ScriptEngineManager();

  @Test
  void availableScriptEngines() {
    var engineFactories = manager.getEngineFactories();
    var names = engineFactories.stream()
        .map(ScriptEngineFactory::getNames)
        .flatMap(Collection::stream)
        .toList();
    log.debug("engines: {}", names);
  }

  @Test
  void groovy() throws ScriptException {
    var engine = manager.getEngineByName("groovy");
    log.debug("engine: {}", engine);
    var scriptContext = new SimpleScriptContext();
    scriptContext.setAttribute("javaVar", "Hello Java", ScriptContext.ENGINE_SCOPE);
    var eval = engine.eval(
        """
            def name = 'Groovy'
            def greeting = "Hello ${name}"
            def newline = "\\n"
            greeting + newline + javaVar + newline + 
            '''line one
            line two
            line three'''""",
        scriptContext);
    log.debug("eval: {}", eval);
  }

  @Test
  void javascript() throws ScriptException {
    var engine = manager.getEngineByName("graal.js");
    assertThat(engine.getFactory())
        .isEqualTo(manager.getEngineByName("js").getFactory());
    log.debug("engine: {}", engine);
    var scriptContext = new SimpleScriptContext();
    scriptContext.setAttribute("javaVar", "Hello Java", ScriptContext.ENGINE_SCOPE);
    var eval = engine.eval(
        "print('Hello World!'); 'Received: ' + javaVar",
        scriptContext
    );
    log.debug("eval: {}", eval);
  }

}
