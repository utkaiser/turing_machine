package finiteStateMachine.mealy;

import finiteStateMachine.AbstractFiniteStateMachine;

public class MealyMachine<I, O> extends AbstractFiniteStateMachine<I, O> {

    public static void main(String[] args) {
        ////Zeigt die Funktionstüchtigkeit dieser Klasse an einem Beispiel.
        AbstractFiniteStateMachine<Character,Character> test = new MealyMachine<Character, Character>();
        test.addState("s1");
        test.addState("s2");
        test.addState("s3");
        String startState1 = test.getState("s1").toString();
        String targetState1 = test.getState("s2").toString();
        String targetState2 = test.getState("s3").toString();
        System.out.println("State1 : "+ startState1);
        System.out.println("State2 : "+targetState1);
        System.out.println("State3 : "+targetState2);
        test.setCurrentState("s1");
        System.out.println("CurrentState: "+test.getCurrentState().toString());
        test.getState("s3").setAccepted(true);
        test.addTransition(startState1,'1',targetState1,'1');
        test.addTransition(targetState1,'1',targetState2,'1');
        System.out.print("Transit with input: ");
        test.transit('1');
        System.out.println();
        System.out.print("Transit with input: ");
        test.transit('1');
        System.out.println();
        System.out.println("CurrentState: " + test.getCurrentState().toString());
        System.out.print("AcceptedState is: ");
        System.out.println(test.isInAcceptedState());


    }

    @Override
    protected void processOutput(O output) {
        //Ausgaben ohne Zwischenzeichen wie Leerzeichen oder Zeilenumbruch auf die Standardausgabe schreibt.
        String result = output.toString();
        result = result.replaceAll(" ","");
        result = result.replaceAll("\\r\\n|\\r|\\n","");
        System.out.print(result);
    }


}
