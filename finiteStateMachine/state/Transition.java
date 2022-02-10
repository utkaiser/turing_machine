package finiteStateMachine.state;

public class Transition<O> {

    private String targetState;
    private O output;

    public Transition(String targetState, O output){
        this.targetState =targetState;
        this.output = output;
    }

    public String getNextState(){
        return targetState;
    }
    public O getOutput(){
        return output;
    }

    public String toString(){
        String result = targetState;
        if(output != null)
            result +=","+output.toString();
        else
            result +=",null";
        return result;
    }


}
