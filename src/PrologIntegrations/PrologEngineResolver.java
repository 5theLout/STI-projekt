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

    public List<String> solvePrologQuery(String query) throws Exception {

        List<String> solutions = new ArrayList<>();

        String goal = query;
        try {
            SolveInfo info = engine.solve(goal);

            if (!info.isSuccess()) {
                System.out.println("no.");
            }
            else if (!engine.hasOpenAlternatives()) {
                //System.out.println(info);
                System.out.println(info.getSolution().toJSON());
                //System.out.println(info.getSolution().getTerm());
                solutions.add(info.getSolution().getTerm().toString());
            } else { // main case
                String answer = query;
                solutions.add(info.getSolution().getTerm().toString());
                while(engine.hasOpenAlternatives()) {
                    info = engine.solveNext();
                    System.out.println(info.getSolution().toJSON());
                    solutions.add(info.getSolution().getTerm().toString());
                    if (!info.isSuccess()) {
                        System.out.println("no.");
                        break;
                    } else {
                        answer = query;
                    }
                }
                if (answer.equals(";") && !engine.hasOpenAlternatives()) {
                    System.out.println(info.getSolution().toJSON());
                    solutions.add(info.getSolution().getTerm().toString());
                    System.out.println("no.");
                }

            } // end main case
        } catch (MalformedGoalException ex) {
            System.err.println("syntax error.");
            //return false;
        } // end try
        return solutions;
        //return false;
    }

    public boolean solveSimpleQuery(String query) {
        try {
            SolveInfo solveInfo = engine.solve(query);
            if(solveInfo.isSuccess()) return true;
            else return false;
        } catch (MalformedGoalException ex) {
            System.err.println("syntax error.");
            return false;
            //return false;
        } // end try
    }

}