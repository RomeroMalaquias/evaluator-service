package br.ufal.ic

import br.ufal.ic.commons.ServerRPC
import org.codehaus.jackson.map.ObjectMapper

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
        Submission submission;
        try {
            submission =  new ObjectMapper().readValue(message, Submission.class);
            if (!submission.isValid()) {
                return "INVALID_FORMAT"
            }
        } catch(Exception e) {
            return "INVALID_FORMAT"
        }

        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer); //configures output redirection

        try {
            engine.eval(submission.getCode(), context);
            String result = writer.toString()
            if (result.equals(submission.getOutput())) {
                return "CORRECT"
            } else {
                return "WRONG_ANSWER"
            }
        } catch (Exception e) {
            e.printStackTrace()
            return "COMPILATION_ERROR"
        }

    }

    public static void main(String[] argv) {
        Evaluator evaluatorServer = new Evaluator("EXCHANGE", "evaluator");


    }


}






