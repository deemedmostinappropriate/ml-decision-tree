import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by aidandoak on 3/04/17.
 */
public class InstanceSet {
    public int numCategories;
    public int numAtts;
    public List<String> categoryNames;
    public List<String> attNames;
    public Set<Instance> allInstances;


    public void readDataFile(String fname){
    /* format of names file:
     * names of categories, separated by spaces
     * names of attributes
     * category followed by true's and false's for each instance
     */
        System.out.println("Reading data from file "+fname);
        try {
            File data = new File(fname);
            BufferedReader read = new BufferedReader(new FileReader(data));

            categoryNames = new ArrayList<String>();
            for(String s : read.readLine().split(" ")){
                categoryNames.add(s);
            }
            numCategories = categoryNames.size();

            attNames = new ArrayList<String>();
            for(String s : read.readLine().split(" ")) {
                attNames.add(s);
            }
            numAtts = attNames.size();

            // Read the rest of the file as new instances:
            allInstances = readInstances(read);
        }
        catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }


    private Set<Instance> readInstances(BufferedReader reader){
    /* instance = classname and space separated attribute values */
        Set<Instance> instances = new HashSet<Instance>();

        try {
            String line = reader.readLine();
            while (line != null) {
                // Split the line by spaces
                String[] sp = line.split(" ");
                boolean[] values = new boolean[sp.length - 1];

                // Turn all strings after the catagory name into booleans:
                for(int i = 1; i < sp.length; ++i){
                    values[i - 1] = Boolean.parseBoolean(sp[i]);
                }

                // Create the instance:
                instances.add(new Instance(categoryNames.indexOf(sp[0]), values));
            }
            System.out.println("Read " + instances.size() + " instances");
        } catch(IOException e){
            System.out.println("Error creating instances from file.");
        }
        return instances;
    }

    /**
     * A representation of an instance of data.
     */
    public class Instance {

        private int category;
        private Map<Integer,Boolean> vals;

        public Instance(int cat, boolean[] values){
            category = cat;
            vals = new TreeMap<Integer, Boolean>();

            for(int i = 0; i < values.length; ++i)
                vals.put(i,values[i]);
        }

        public boolean getAtt(int index){
            return vals.get(index);
        }

        public int getCategory(){
            return category;
        }

        public String toString(){
            StringBuilder ans = new StringBuilder(categoryNames.get(category));
            ans.append(" ");
            for (Boolean val : vals.values())
                ans.append(val?"true  ":"false ");
            return ans.toString();
        }

    }
}
