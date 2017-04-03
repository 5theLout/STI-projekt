package PrologIntegrations;

import Models.Environment;
import alice.tuprolog.*;

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
                //System.out.println(info.getTerm("house").toString());
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

    //public static void testingMethod(List<String> args) throws Exception {
//
    //    Prolog engine = new Prolog();
    //    engine.setTheory(new Theory(new FileInputStream(args.get(0))));
    //    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    //    while (true) { // interpreter main loop
    //        String goal;
    //        do {
    //            System.out.print("?- ");
    //            goal = stdin.readLine();
    //        } while (goal.equals(""));
    //        try {
    //            SolveInfo info = engine.solve(goal);
    //            if (engine.isHalted()) break;
    //            else if (!info.isSuccess()) System.out.println("no.");
    //            else if (!engine.hasOpenAlternatives()) {
    //                System.out.println(info);
    //            } else { // main case
    //                System.out.println(info + " ?");
    //                String answer = stdin.readLine();
    //                while (answer.equals(";") && engine.hasOpenAlternatives()) {
    //                    info = engine.solveNext();
    //                    if (!info.isSuccess()) {
    //                        System.out.println("no.");
    //                        break;
    //                    } else {
    //                        System.out.println(info + " ?");
    //                        answer = stdin.readLine();
    //                    } // endif
    //                }// endwhile
    //                if (answer.equals(";") && !engine.hasOpenAlternatives())
    //                    System.out.println("no.");
    //            } // end main case
    //        } catch (MalformedGoalException ex) {
    //            System.err.println("syntax error.");
    //        } // end try
    //    } // end main loop
    //    if (args.size() > 1) {
    //        Theory curTh = engine.getTheory(); // save current theory to file
    //        new FileOutputStream(args.get(1)).write(curTh.toString().getBytes());
    //    }
    //}
}