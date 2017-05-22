package PrologIntegrations;

import Models.Environment;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2017-03-29.
 */
public class PrologEngineResolver {

    private Prolog engine;

    public Prolog getEngine() {
        return this.engine;
    }

    public PrologEngineResolver(String theoryFileName) throws Exception {
        engine = new Prolog();
        this.setEngineTheory(theoryFileName);
    }

    private void setEngineTheory(String filename) throws Exception {
        Theory basicTheory = new Theory(new FileInputStream(filename));
        engine.setTheory(basicTheory);
    }

    public List<String> resolvePrologQuery(String query) throws Exception {

        List<String> solutions = new ArrayList<>();

        String goal;
        do {
            goal = query;
        } while (goal.equals(""));
        try {
            SolveInfo info = engine.solve(goal);

            if (engine.isHalted());
            else if (!info.isSuccess()) {
                System.out.println("no.");
            }
            else if (!engine.hasOpenAlternatives()) {
                System.out.println(info);
                System.out.println(info.getSolution().toJSON());
                System.out.println(info.getSolution().getTerm());
                solutions.add(info.getSolution().getTerm().toString());
                //return true;
            } else { // main case
                System.out.println(info + " ?");
                String answer = query;
                while (answer.equals(";") && engine.hasOpenAlternatives()) {
                    info = engine.solveNext();
                    System.out.println(info.getSolution().toJSON());
                    System.out.println(info.getSolution().getTerm());
                    if (!info.isSuccess()) {
                        System.out.println("no.");
                        //return false;
                        break;
                    } else {
                        System.out.println(info + " ?");
                        answer = query;
                    } // endif
                }// endwhile
                if (answer.equals(";") && !engine.hasOpenAlternatives()) {
                    System.out.println(info.getSolution().toJSON());
                    System.out.println(info.getSolution().getTerm());
                    solutions.add(info.getSolution().getTerm().toString());
                    System.out.println("no.");
                    //return false;
                }

            } // end main case
        } catch (MalformedGoalException ex) {
            System.err.println("syntax error.");
            //return false;
        } // end try
        return solutions;
        //return false;
    }

}