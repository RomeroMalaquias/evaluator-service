package br.ufal.ic.judge

import br.ufal.ic.judge.commons.ServerRPC
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import javax.script.ScriptContext
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleScriptContext


class Evaluator extends ServerRPC {
    Evaluator(String exchangeName, String key) {
        super(exchangeName, key)
    }

    String doWork (String message){
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        StringWriter writer = new StringWriter(); //ouput will be stored here
        def submission;
        try {
            def jsonSlurper = new JsonSlurper()
            submission = jsonSlurper.parseText(message)
            println submission
        } catch(Exception e) {
            submission['__result'] = "INVALID_FORMAT"
        }

        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer); //configures output redirection

        try {
            if(submission.code && submission.output) {
                engine.eval(submission.code, context);
                String result = writer.toString()
                if (result.equals(submission.output)) {
                    submission['__result'] = "CORRECT"
                } else {
                    submission['__result'] = "WRONG_ANSWER"
                }
            } else {
                submission['__result'] = "INVALID_FORMAT"
            }

        } catch (Exception e) {
            e.printStackTrace()
            submission['__result'] = 'COMPILATION_ERROR'
        }
        return  JsonOutput.toJson(submission)
    }

    public static void main(String[] argv) {
        Evaluator evaluatorServer = new Evaluator("EXCHANGE", "evaluator");


    }


}






