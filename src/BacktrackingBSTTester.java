import java.util.*;

/**
 * @author      Yaniv Krol
 * @version     1.0
 */

public class BacktrackingBSTTester {

    public static void main(String[] args) {
        BacktrackingBSTTester testBST = new BacktrackingBSTTester(0, 10);
        testBST.start();
    }


    /**
     * You can change the method call inside to whatever printing method you want.
     * The method treeFormPrint() is a method I wrote and posted on facebook.
     */
    private void printTree() {
        bst.treeFormPrint();
    }

    private Random rand = new Random();
    private Scanner reader = new Scanner(System.in);
    private BacktrackingBST.Node node;
    private BacktrackingBST bst = new BacktrackingBST(new Stack(),new Stack());
    private List<BacktrackingBST.Node> availableToInsert;
    private List<BacktrackingBST.Node> insertedNodes;
    private List<BacktrackingBST.Node> insertedButNotAvailable;
    private String[] randomOptions = {"Insert random", "Delete random", "", "", "", "", "Predecessor of random", "Successor of random"};
    private String[] specificOptions = {"Search", "Insert", "Delete", "Backtrack", "Retrack", "Minimum", "Maximum",
                                        "Predecessor", "Successor", "print preOrder"};
    private List blockedOptionsWhenEmpty = new ArrayList<>(Arrays.asList(2, 22, 7, 77, 8, 88));

    /**
     * @param firstNode key of the first node
     * @param LastNode key of the last node
     *
     * LastNode - firstNode + 1 will be the number of nodes to play with
     */
    public BacktrackingBSTTester(int firstNode, int LastNode) {
        int initialCapacity = LastNode - firstNode + 1;
        availableToInsert = new ArrayList<>(initialCapacity);
        insertedNodes = new ArrayList<>(initialCapacity);
        insertedButNotAvailable = new ArrayList<>(initialCapacity);
        for (int i=firstNode; i<=LastNode; i++)
            availableToInsert.add(new BacktrackingBST.Node(i, null));
    }

    public void start() {

        printOptions();

        while (true) {
            System.out.print("Enter option number: ");
            int whatToDo = reader.nextInt();
            if (whatToDo == 10) {
                whatToDo = rand.nextInt(8) + 1;
                if (!(whatToDo >= 3 && whatToDo <= 6))
                    whatToDo*=11;
                System.out.println("Random operation: "+whatToDo);
            }
            if (bst.root == null && blockedOptionsWhenEmpty.contains(whatToDo)) {
                System.out.println("The tree is empty, can't do this operation");
                continue;
            }
            switch (whatToDo) {
                case (1): // Insert
                    insertCommon(inputForInsert());
                    break;
                case (11): // Insert random
                    insertCommon(availableToInsert.get(rand.nextInt(availableToInsert.size())));
                    break;
                case (2): // Delete
                    deleteCommon(inputForDelete());
                    break;
                case (22):  // Delete random
                    deleteCommon(deleteRandom());
                    break;
                case (3): // Backtrack
                    System.out.println("Backtracking\n");
                    bst.backtrack();
                    printTree();
                    break;
                case (4): // Retrack
                    System.out.println("Retracking\n");
                    bst.retrack();
                    printTree();
                    break;
                case (5): // Minimum
                    System.out.println("Minimum: "+bst.minimum());
                    break;
                case (6): // Maximum
                    System.out.println("Maximum: "+bst.maximum());
                    break;
                case (7): // Predecessor
                    successive("Predecessor");
                    break;
                case (77): // Predecessor random
                    successiveRandom("Predecessor");
                    break;
                case (8): // Successor
                    successive("Successor");
                    break;
                case (88): // Successor random
                    successiveRandom("Successor");
                    break;
                case (0): // Search
                    System.out.print("Enter value to find: ");
                    System.out.println(bst.search(reader.nextInt()) != null ? "Found" : "not found");
                    break;
                case (9): // Print preOrder
                    bst.printPreOrder();
                    break;
            }
            System.out.println();
            System.out.println("---------------------------------------\n");
            insertedNodes.addAll(insertedButNotAvailable);
            insertedButNotAvailable.clear();
        }
    }


    private void deleteCommon(BacktrackingBST.Node node){
        System.out.println("Deleting " + node+"\n");
        bst.delete(node);
        printTree();
    }

//    private void printTree() {

//    }

    private BacktrackingBST.Node inputForDelete() {
        System.out.print("Enter value to delete: ");
        node = bst.search(reader.nextInt());
        while (node == null) {
            System.out.print("Value doesn't exist, enter value to delete: ");
            node = bst.search(reader.nextInt());
        }
        return node;
    }

    private BacktrackingBST.Node deleteRandom() {
        node = insertedNodes.get(rand.nextInt(insertedNodes.size()));
        while (bst.search(node.getKey()) == null) {
            insertedNodes.remove(node);
            insertedButNotAvailable.add(node);
            node = insertedNodes.get(rand.nextInt(insertedNodes.size()));
        }
        return node;
    }

    private void insertCommon(BacktrackingBST.Node node) {
        availableToInsert.remove(node);
        insertedNodes.add(node);
        System.out.println("Inserting " + node+"\n");
        bst.insert(node);
        printTree();
    }

    private BacktrackingBST.Node inputForInsert() {
        String toPrint = "Available nodes: ";
        for (BacktrackingBST.Node _node : availableToInsert)
            toPrint += _node.getKey() + " ";
        System.out.println(toPrint);
        System.out.print("Enter value to insert: ");
        node = findNodeFromAvailable(reader.nextInt());
        while (node == null) {
            System.out.print("Value doesn't exist, enter value to insert: ");
            node = findNodeFromAvailable(reader.nextInt());
        }
        return node;
    }

    // predecessor and successor
    private void successive(String method) {
        System.out.print("Enter value to find "+method+" of: ");
        node = bst.search(reader.nextInt());
        while (node == null) {
            System.out.print("Value doesn't exist, enter value to find \"+method+\" of: ");
            node = bst.search(reader.nextInt());
        }
        System.out.println(method+" of "+node+" is "+
                (method.equals("Predecessor") ? bst.predecessor(node) : bst.successor(node)));
    }

    // random for predecessor and successor
    private void successiveRandom(String method) {
        node = insertedNodes.get(rand.nextInt(insertedNodes.size()));
        while (bst.search(node.getKey()) == null) {
            insertedNodes.remove(node);
            insertedButNotAvailable.add(node);
            node = insertedNodes.get(rand.nextInt(insertedNodes.size()));
        }
        System.out.println(method+" of "+node+" is "+
                (method.equals("Predecessor") ? bst.predecessor(node) : bst.successor(node)));
    }

    private void printOptions() {
        System.out.println("Options:");
        for (int i = 0; i < specificOptions.length; i++)
            System.out.println(i + "  - " + specificOptions[i]);
        System.out.println("10 - Random operation");
        for (int i = 1; i <= randomOptions.length; i++)
            if (!randomOptions[i-1].isEmpty())
                System.out.println(i+""+i + " - " + randomOptions[i-1]);
        System.out.println("---------------------------------------\n");
    }

    private BacktrackingBST.Node findNodeFromAvailable(int value) {
        for (BacktrackingBST.Node node : availableToInsert)
            if (node.getKey() == value)
                return node;
        return null;
    }

}
