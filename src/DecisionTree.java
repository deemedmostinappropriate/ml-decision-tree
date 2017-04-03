import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aidandoak on 28/03/17.
 */
public class DecisionTree {

    public DTNode buildTree(List<InstanceSet.Instance> instances, List<String> attributes){
        if(instances.isEmpty())
            // TODO: return the baseline predictor:
            return new DTNode();
        // Else if instances are pure:
            // return node containing the name of the class of instances in the node and prob = 1

        // Else if attributes empty
            // return node containing the name and prob of the majority class of the instances in this node
            // choose randomly if classes are equal

        else { // Find best attribute:
            for(int i = 0; i < attributes.size(); ++i){
                Set<InstanceSet.Instance> trueSet = new HashSet<>();
                Set<InstanceSet.Instance> falseSet = new HashSet<>();

                for(InstanceSet.Instance instance : instances){
                    boolean attributeTrue = instance.getAtt(i);

                    // Add it to a set depending on its bool value:
                    if(attributeTrue)
                        trueSet.add(instance);
                    else
                        falseSet.add(instance);
                }

                // TODO: compute purity of each set

                // If weighted avg of the sets is best so far:
                    // bestAtt = attributes.get(i);
                    // set of best true instances
                    // set of best false instances
            }

            // TODO: build subtree using the remaining attributes (recursive!)

            // Return the node with the best attribute, and its left and right
            return new DTNode();
        }
    }

    /* Main Method: */

    public static void main(String[] args){
        DecisionTree dt = new DecisionTree();

        // Create the training data and build the tree from it:
        InstanceSet trainingData = new InstanceSet();
        trainingData.readDataFile(args[0]);
        dt.buildTree(trainingData.allInstances, trainingData.attNames);

    }
}
