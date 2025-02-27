package com.example.java.playground.features.script;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.function.Predicate;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.HostAccess;
import org.junit.jupiter.api.Test;

@Slf4j
class ScriptTest {

  ScriptEngineManager manager = new ScriptEngineManager();

  @Value
  static class SomeBean {

    String name;

    public String name2() {
      return name;
    }
  }

  @Test
  void availableScriptEngines() {
    var engineFactories = manager.getEngineFactories();
    var names = engineFactories.stream()
        .map(ScriptEngineFactory::getNames)
        .flatMap(Collection::stream)
        .toList();
    log.info("engines: {}", names);
  }

  @Test
  void groovy() throws ScriptException {
    var engine = manager.getEngineByName("groovy");
    log.info("engine: {}", engine);
    var context = new SimpleScriptContext();
    context.setAttribute(
        "javaVar", "Hello Java", ScriptContext.ENGINE_SCOPE);
    context.setAttribute(
        "someBean", new SomeBean("java_somebean_name"), ScriptContext.ENGINE_SCOPE);

    var eval = engine.eval(
        """
            def name = 'Groovy'
            def greeting = "Hello ${name}"
            def newline = "\\n"
            greeting + newline + javaVar + newline + 
            '''line one
            line two
            line three'''""",
        context);
    log.info("eval: {}", eval);
  }

  // NOTE: currently (Java 23) not working, due to access to jdk.internal.misc
  // cannot access class jdk.internal.misc.Unsafe (in module java.base) because module java.base does not export jdk.internal.misc to module jdk.graal.compiler
  // Wait for fix or try to "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED"
  @Test
  void javascript() throws ScriptException {
    var engine = manager.getEngineByName("graal.js");
    assertThat(engine.getFactory())
        .isEqualTo(manager.getEngineByName("js").getFactory());
    log.info("engine: {}", engine);

    var graalVmContext = org.graalvm.polyglot.Context.newBuilder("js")
        .allowHostAccess(HostAccess.ALL)
        .allowHostClassLookup(className -> true)
        .build();

    var someBean = new SomeBean("some_name");
    var javaVar = "Hello Java";

    var context = new SimpleScriptContext();
    context.setAttribute("javaVar", javaVar, ScriptContext.ENGINE_SCOPE);
    context.setAttribute("someBean", someBean, ScriptContext.ENGINE_SCOPE);

    // trying to activate access to classes and properties
    Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
    bindings.put("polyglot.js.allowHostAccess", true);
    bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
    bindings.put("polyglot.js.allowNativeAccess", true);
    bindings.put("polyglot.js.allowAllAccess", true);
    // Note: using vars in binding is mutual exclusive with using context in engine.eval()
    // But it seems to be equivalent
    bindings.put("someBean", someBean);
    bindings.put("javaVar", javaVar);

    // Note: seems to need options to be able to access bean properties (maybe compiler options)
    var eval = engine.eval(
        """
            print('Hello World!'); 
            print('someBean: ' + someBean); 
            print('someBean.name: ' + someBean.name); 
            print('someBean.name: ' + someBean['name']); 
            print('someBean keys: ' + Object.keys(someBean)); 
            'Received: ' + javaVar
            """
//        , context
    );
    log.info("eval: {}", eval);
  }

}
