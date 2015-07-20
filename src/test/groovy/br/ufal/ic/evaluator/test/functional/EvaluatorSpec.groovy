package br.ufal.ic.evaluator.test.functional

import br.ufal.ic.Evaluator
import br.ufal.ic.SubmissionTest
import spock.lang.Specification

class EvaluatorSpec extends Specification {



    def "when you submit a correct program"() {
        when:
          def a = 1
            SubmissionTest submission = new SubmissionTest("EXCHANGE", "evaluator");
            submission.start();
            submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "2", "output": "Ola mundo"}');
            while(submission.getLoop()){}
        then:
            submission.getResponse() == 'CORRECT'

    }


}
