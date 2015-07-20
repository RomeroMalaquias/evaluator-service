package br.ufal.ic

/**
 * Created by huxley on 18/07/15.
 */
class Submission {
    private String code
    private String language
    private String input
    private String output
    public String getCode() {
        return code;
    }

    public void setCode(code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(language) {
        this.language = language;
    }

    public String getInput(input) {
        return input;
    }

    public void setInput(input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(output) {
        this.output = output;
    }
    public String toString() {
        return language;
    }
    public boolean isValid() {
        println code
        return code && language && input && output
    }
}
