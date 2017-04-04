import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;

/**
 * Created by aidandoak on 28/03/17.
 */
public class DTNode {
    /** An instance class from the data file. */
    public int classification;

    public double probability;

    /* The left and right nodes of the decision tree */
    public DTNode left = null;
    public DTNode right = null;

    public Set<InstanceSet.Instance> instances;

    public void add(InstanceSet.Instance i){
        instances.add(i);
    }
}
