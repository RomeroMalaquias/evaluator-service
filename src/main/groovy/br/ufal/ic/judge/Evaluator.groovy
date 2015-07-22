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
        boolean err = false
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        StringWriter writer = new StringWriter(); //ouput will be stored here
        def submission;
        try {
            def jsonSlurper = new JsonSlurper()
            submission = jsonSlurper.parseText(message)
            println submission
        } catch(Exception e) {
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Invalid JSON format"
            return  JsonOutput.toJson(submission)
        }

        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer); //configures output redirection
        if(!submission.code) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'code' cannot be empty"
        } else if (submission.code.size() > 510)    {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'to' cannot be greather than 510 caracters"
        }
        if (!submission.language) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'language' cannot be empty"
        } else if (submission.language.size() > 255) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'language' cannot be greather than 255 caracters"
        } else if (submission.language != 'groovy') {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'language' valid values are ['groovy']"
        }
        if (submission.input && submission.input.size() > 255) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'input' cannot be greather than 255 caracters"
        }
        if (!submission.output) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'output' cannot be empty"
        } else if (submission.output.size() > 255) {
            err = true
            submission['__errMSG'] = submission['__errMSG']? submission['__errMSG'] : []
            submission['__status'] = "FAIL"
            submission['__errMSG'] << "Field 'output' cannot be greather then 255 caracters"
        }
        try {
            if(!err) {
                engine.eval(submission.code, context);
                String result = writer.toString()
                if (result.equals(submission.output)) {
                    submission['__result'] = "CORRECT"
                    submission['__status'] = "OK"
                } else {
                    submission['__result'] = "WRONG_ANSWER"
                    submission['__status'] = "OK"
                }
            }

        } catch (Exception e) {
            e.printStackTrace()
            submission['__result'] = 'COMPILATION_ERROR'
            submission['__status'] = "OK"
        }
        return  JsonOutput.toJson(submission)
    }

    public static void main(String[] argv) {
        Evaluator evaluatorServer = new Evaluator("EXCHANGE", "judge");


    }


}






