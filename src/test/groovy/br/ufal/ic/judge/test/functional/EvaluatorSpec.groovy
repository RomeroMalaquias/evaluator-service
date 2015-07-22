package br.ufal.ic.judge.test.functional

import groovy.json.JsonSlurper
import spock.lang.Specification

class EvaluatorSpec extends Specification {



    def "Quando se submete um programa válido"() {
        when:
            SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
            submission.start();
            submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "2", "output": "Ola mundo"}');
            while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'OK'

    }
    def "Quando se submete um programa com input vazio"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'OK'

    }

    def "Quando se submete um programa com input acima de 255"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "romero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.com", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa com output vazio"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "2", "output": ""}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa com output acima de 255"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "groovy", "input": "2", "output": "romero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.com"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa com code vazio"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"", "language": "groovy", "input": "2", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa com code acima do limite"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"a2romero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.com", "language": "groovy", "input": "2", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa numa linguagem inválida"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "java", "input": "2", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa numa linguagem vazia"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "", "input": "2", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }

    def "Quando se submete um programa numa linguagem com nome acima do limite de 255"() {
        when:
        SubmissionTest submission = new SubmissionTest("EXCHANGE", "judge");
        submission.start();
        submission.call('{"code":"print \'Ola mundo\';", "language": "romero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.comromero.malaquias@gmail.com", "input": "2", "output": "Ola mundo"}');
        while(submission.getLoop()){}
        then:
        def jsonSlurper = new JsonSlurper()
        def response = jsonSlurper.parseText(submission.getResponse())
        response['__status'] == 'FAIL'
        response['__errMSG'] != null
    }


}
