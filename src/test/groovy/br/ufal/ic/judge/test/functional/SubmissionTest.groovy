package br.ufal.ic.judge.test.functional

import br.ufal.ic.judge.commons.ClientRPC

/**
 * Created by huxley on 18/07/15.
 */
class SubmissionTest extends ClientRPC {
    private String response
    SubmissionTest(String exchangeName, String key) {
        super(exchangeName, key);
    }

    void doWork (String message){
        response = message
        println message
        this.close()
    }

    public String getResponse() {
        return response
    }

    public boolean getLoop() {
        return this.listen
    }

    public static void main(String[] argv) {
        SubmissionTest fibonacciRpc = new SubmissionTest("EXCHANGE", "judge");
        fibonacciRpc.start();
        fibonacciRpc.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "2", "output": "oi"}');
        fibonacciRpc.call('{"language": "groovy", "input": "2", "output": "oi"}');
        fibonacciRpc.call('{"code":"print(\'oi\');", "language": "groovy", "input": "2", "output": "3"}');
        fibonacciRpc.call('{"code":"print(\'Ola mundo\');", "language": "groovy", "input": "2", "output": "Ola mundo"}');
        fibonacciRpc.call('{"code":" \'Ola mundo\';", "language": "groovy", "input": "2", "output": "Ola mundo"}');

    }

}





