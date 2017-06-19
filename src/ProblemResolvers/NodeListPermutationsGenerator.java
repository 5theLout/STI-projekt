package ProblemResolvers;

import com.xiantrimble.combinatorics.Combinatoric;
import com.xiantrimble.combinatorics.CombinatoricFactory;
import com.xiantrimble.combinatorics.CombinatoricFactoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeListPermutationsGenerator {

    public static List<List<Node>> getPermutations(int amount, List<Node> listToGeneratePermutationsOf) {
        CombinatoricFactory factory = new CombinatoricFactoryImpl();
        List<List<Node>> permututationsList = new ArrayList<>();

        Combinatoric<Node> permutations = factory.createPermutations(amount,
                listToGeneratePermutationsOf.toArray(
                        new Node[listToGeneratePermutationsOf.size()]
                ));

        for( Node[] permutation : permutations ) {
            permututationsList.add(Arrays.asList(permutation));
        }

        return permututationsList;
    }
}
