package finiteStateMachine.state;

import finiteStateMachine.AbstractFiniteStateMachine;

import java.util.ArrayList;

public class State<I,O> {
    private String name;
    private AbstractFiniteStateMachine<I,O> machine;
    private boolean accepted;
    private ArrayList<Transition<O>> transitions;
    private ArrayList<I> inputs;
    public State(AbstractFiniteStateMachine<I, O> machine, String name){
        this.name = name;
        this.machine = machine;
        transitions = new ArrayList<Transition<O>>();
        inputs = new ArrayList<I>();
    }

    public void setAccepted(boolean accepted){
        //kennzeichnen einen Zustand als akzeptiert
        this.accepted = accepted;
    }
    public boolean isAccepted(){
        //kennzeichnen einen Zustand als akzeptiert
      return this.accepted;
    }

    public void addTransition(I input, String targetState, O output){
        //Fügt eine Transition zum Zustand hinzu, die bei der Eingabe input ausgeführt werden soll.
        int index = inputs.indexOf(input);
        if(index >= 0)
        {
            transitions.set(index, new Transition<>(targetState,output));
        }
        else {
            transitions.add(new Transition<>(targetState, output));
            inputs.add(input);
        }
    }
    public Transition<O> getTransition(I input){
        //Liefert die Transition, die bei der Eingabe input ausgeführt wird.
        int index = inputs.indexOf(input);
        if(index >= 0)
        {
            return transitions.get(index);
        }
        else
            return null;
    }
    public O transit(I input){
        //Führt den Zustandsübergang bei Eingabe von input aus.
        int index = inputs.indexOf(input);
        Transition<O> transition;
        if(index >= 0)
        {
            transition = transitions.get(index);
            machine.setCurrentState(transition.getNextState());
            return transition.getOutput();

        }
        else{
            throw new IllegalArgumentException("kein ubergang fuer "+ input.toString() + " definiert");
        }
    }
    public AbstractFiniteStateMachine<I, O> getMachine(){
        return machine;
    }
    public String getName(){
        return this.name;
    }
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State<?, ?> state = (State<?, ?>) o;

        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
