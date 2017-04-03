package Controllers;

import alice.tuprolog.*;
import alice.tuprolog.event.QueryEvent;
import alice.tuprolog.scriptengine.PrologScriptEngine;
import alice.tuprologx.pj.annotations.PrologMethod;
import alice.tuprologx.pj.model.Atom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class JPLTest{

    @PrologMethod( clauses = {
            "permutation([],[]).",
            "permutation(U,[X|V]):-remove(U,X,Z),permutation(Z,V).",
            "remove([X|T],X,T).",
            "remove([X|U],E,[X|V]):-remove(U,E,V)."
    })
    public abstract < $X extends String, $Y extends String >
    Iterable<$Y> permutation($X list);

    static void test3a(String sent) {
        // a variable which will get the output.
        //Var X = new Var("X");
        ////creating query object to make a query to prolog code.
        //QueryEvent q3 = new QueryEvent("parent", new Term[] {X,new Struct(sent)});
        //System.out.println("Parent of "+ sent + " is " + q3.oneSolution().get("X"));//get the value stored in X
        Var varX = new Var("X"), varY = new Var("Y");
        Struct atomP = new Struct("p");

        Var heniek = new Var();
        Var jan = new Var();

        Atom joanna = new Atom("kobieta");


        Struct list = new Struct(atomP, varY); // should be [p|Y]
        System.out.println(list); // prints the list [p|Y]
        Struct fact = new Struct("p", new Struct("a"), new Int(5));
        Struct goal = new Struct("p", varX, new Var("Z"));

        Struct fact2 = new Struct("f2", new Struct("heniek"), new Struct("mezczyzna"));
        Struct goal2 = new Struct("f2", new Struct("halina"), new Struct("mezczyzna"));


        Prolog engine = new Prolog();
        boolean res = goal.unify(engine,fact); // should be X/a, Y/5

        boolean res2 = goal2.unify(engine, fact2);


        System.out.println(res);
        System.out.println(res2);

        System.out.println(goal); // prints the unified term p(a,5)
        System.out.println(varX); // prints the variable binding X/a
        Var varW = new Var("W");
        res = varW.unify(engine,varY); // should be Z=Y
        System.out.println(varY); // prints just Y, since it is unbound
        System.out.println(varW); // prints the variable binding W / Y
        Struct st = new Struct("q", new Var("Y"), new Var("Y")); // unresolved
        System.out.println(st.getArg(0)==st.getArg(1)); // prints false
        st.resolveTerm(); // now the term is resolved
        alternatively: res = st.match(new Struct());
        alternatively: res = st.unify(engine, new Struct());
        System.out.println(st.getArg(0) == st.getArg(1)); // prints true

        try {
            Theory theory = new Theory(new FileInputStream("b.pl"));
            //engine.addTheory(theory);
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (InvalidTheoryException e) {
        //  e.printStackTrace();
        //}
    }

}