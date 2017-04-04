import java.util.*;

/**
 * Created by aidandoak on 28/03/17.
 */
public class DecisionTree {

    public DTNode buildTree(Set<InstanceSet.Instance> instances, List<String> attributes){
        DTNode root = new DTNode();
        if(instances.isEmpty()) {
            return root;
        }

        int pureCategory = getPureCategory(instances);
        if(pureCategory != -1) {
            // return node containing the name of the class of instances in the node and prob = 1
            DTNode pureNode = new DTNode();
            pureNode.classification = pureCategory;
            pureNode.probability = 1;
            pureNode.instances = instances;

            return pureNode;
        }

        if(attributes.isEmpty()) {
            // return node containing the name and prob of the majority class of the instances in this node
            // choose randomly if classes are equal
            double[] p = getClassProbabilites(instances);
            double current = 0.0;
            int classification = -1;

            // Get highest probable classification:
            for(int i = 0; i<p.length; ++i){
                if(p[i] > current){
                    current = p[i];
                    classification = i;
                }
            }

            assert classification > -1;

            DTNode majorClassNode = new DTNode();
            majorClassNode.classification = classification;
            majorClassNode.probability = current;
            majorClassNode.instances = instances;

            return majorClassNode;
        }
        else { // Find best attribute:
            for(int i = 0; i < attributes.size(); ++i){
                Set<InstanceSet.Instance> trueSet = new HashSet<InstanceSet.Instance>();
                Set<InstanceSet.Instance> falseSet = new HashSet<InstanceSet.Instance>();

                for(InstanceSet.Instance instance : instances){
                    boolean attributeTrue = instance.getAtt(i);

                    // Add it to a set depending on its bool value:
                    if(attributeTrue)
                        trueSet.add(instance);
                    else
                        falseSet.add(instance);
                }

                // Compute impurity of the t/f sets:
                double impurityTrueSet = calcImpurity(trueSet);
                double impurityFalseSet = calcImpurity(falseSet);

                // If weighted avg of the sets is best so far:
                // TODO: Track purities of each attribute?
                    // bestAtt = attributes.get(i);
                    // set of best true instances
                    // set of best false instances
            }
            // TODO: build subtree using the remaining attributes (recursive!)

            // Return the node with the best attribute, and its left and right
            return new DTNode();
        }
    }

    /**
     * Calculates the impurity of a set of instances by counting the different
     * possible categories and which instances have which category. Returns a product
     * of counts over the total number of instances to the power of the number of
     * categories. If this returns 0 then the set is pure.
     * @param instances
     * @return
     */
    public double calcImpurity(Set<InstanceSet.Instance> instances){
        Map<Integer, Integer> counts = getClassificationCounts(instances);

        // Make a product of all the counts:
        int productOfCounts = 1;
        for(int i : counts.values()){
            productOfCounts = productOfCounts * i;
        }

        return productOfCounts / Math.pow(instances.size(), counts.size());
    }

    /**
     * Returns the pure category of a given set, or a value > 0 if impure.
     * @param instances
     * @return
     */
    public int getPureCategory(Set<InstanceSet.Instance> instances){
        int category = -1;

        for(InstanceSet.Instance instance : instances){
            if(category == -1)
                category = instance.getCategory();
            else {
                // This indecates that the method was given an impure set:
                if(category != instance.getCategory())
                    return -1;
            }
        }

        // If the set is pure, return the pure category:
        return category;
    }

    /**
     * Returns the probabilities for each possible class in a set.
     * @param instances
     * @return
     */
    public double[] getClassProbabilites(Set<InstanceSet.Instance> instances){
        Map<Integer, Integer> counts = getClassificationCounts(instances);

        double[] probabilities = new double[counts.size()];

        for(Map.Entry<Integer, Integer> e : counts.entrySet()){
            probabilities[e.getKey()] = e.getValue() / instances.size();
        }

        return probabilities;
    }

    private Map<Integer, Integer> getClassificationCounts(Set<InstanceSet.Instance> instances){
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();

        // Read instances and increase the count at the category:
        for(InstanceSet.Instance instance : instances){
            int i = instance.getCategory();
            if(counts.get(i) != null)
                counts.put(i, counts.get(i) + 1);
            else
                counts.put(i, 1);

        }

        return counts;
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
