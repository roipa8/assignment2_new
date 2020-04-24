public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }

    public Node getRoot() {
        return root;
    }

    public Node copyNode(Node x) {
        if (x == null) {
            return null;
        }
        Node a = new Node(x.key, x.value);
        a.parent = x.parent;
        return a;
    }

    public Node search(int x) {
        // TODO: implement your code here
        Node curr = root;
        while (curr != null && x != curr.key) {
            if (x < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return curr;
    }

    public void insert(BacktrackingBST.Node z) {
        // TODO: implement your code here
        redoStack.clear();
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            root = z;
        } else {
            if (z.key < y.key) {
                y.left = z;
            } else {
                y.right = z;
            }
        }
        StackItem3 item = new StackItem3(z, z.key, "Insert");
        stack.push(item);
    }

    public void delete(Node x) {
        // TODO: implement your code here
        redoStack.clear();
        Node y;
        Node z;
        StackItem3 item1 = new StackItem3(x, x.key, "Delete");
        stack.push(item1);
        if (x.left == null | x.right == null) {
            y = x;
        } else {
            y = successor(x);
            StackItem3 item2 = new StackItem3(y, y.key, "SecondDelete");
            stack.push(item2);
        }
        if (y.left != null) {
            z = y.left;
        } else {
            z = y.right;
        }
        if (z != null) {
            z.parent = y.parent;
        }
        if (y.parent == null) {
            root = z;
        } else {
            if (y == (y.parent).left) {
                y.parent.left = z;
            } else {
                y.parent.right = z;
            }
        }
        if (y != x) {
            x.key = y.key;
//            y.parent = x.parent;
//            y.left = x.left;
//            y.right = x.right;
        }
    }

    public Node minimum() {
        // TODO: implement your code here
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    public Node maximum() {
        // TODO: implement your code here
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    public Node successor(Node x) {
        // TODO: implement your code here
        if (x.right != null) {
            x = x.right;
            while (x.left != null) {
                x = x.left;
            }
            return x;
        }
        Node curr = x.parent;
        while (curr != null && x == curr.right) {
            x = curr;
            curr = curr.parent;
        }
        return curr;
    }

    public Node predecessor(Node x) {
        if (x.left != null) {
            x = x.left;
            while (x.right != null) {
                x = x.right;
            }
            return x;
        }
        Node curr = x.parent;
        while (curr != null && x == curr.left) {
            x = curr;
            curr = curr.parent;
        }
        return curr;
        // TODO: implement your code here
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
        if (!stack.isEmpty()) {
            StackItem3 item = (StackItem3) stack.pop();
            if (item.command.equals("Insert")) {
                redoStack.push(item);
                if (item.node.parent == null) {
                    root = null;
                } else {
                    if (item.node.parent.key > item.node.key) {
                        item.node.parent.left = null;
                    } else {
                        item.node.parent.right = null;
                    }
                }
            } else {
                if (item.command.equals("Delete")) {
                    redoStack.push(item);
                    if (item.node.parent == null) {
                        root = item.node;
                        if (item.node.left != null) {
                            root.left = item.node.left;
                            root.left.parent = root;
                        } else {
                            if (item.node.right != null) {
                                root.right = item.node.right;
                                root.right.parent = root;
                            }
                        }
                    } else {
                        if (item.node.key > item.node.parent.key) {
                            item.node.parent.right = item.node;
                        } else {
                            item.node.parent.left = item.node;
                        }
                        if (item.node.left != null) {
                            item.node.left.parent = item.node;
                        }
                        if (item.node.right != null) {
                            item.node.right.parent = item.node;
                        }
                    }
                } else {
                    if (item.command.equals("SecondDelete")) {
                        StackItem3 item2= (StackItem3) stack.pop();
                        redoStack.push(item2);
                        redoStack.push(item);
                        item2.node.key = item2.key;
                        if (item2.node.right != null) {
                            item2.node.right.parent = item2.node;
                        }
                        if (item2.node.left != null) {
                            item2.node.left.parent = item2.node;
                        }
                        if (item.node.parent.key > item.node.key) {
                            item.node.parent.left = item.node;
                        } else {
                            item.node.parent.right = item.node;
                        }
                        if (item.node.right != null) {
                            item.node.right.parent = item.node;
                        }
                    }
                }
            }
            System.out.println("backtracking performed");
        }
    }


    @Override
    public void retrack() {
        // TODO: implement your code here
        if (!redoStack.isEmpty()) {
            StackItem3 item = (StackItem3) redoStack.pop();
            if (item.command.equals("Insert")) {
                stack.push(item);
                if (item.node.parent == null) {
                    root = item.node;
                } else {
                    if (item.node.parent.key > item.node.key) {
                        item.node.parent.left = item.node;
                    } else {
                        item.node.parent.right = item.node;
                    }
                }
            } else {
                if (item.command.equals("Delete")) {
                    stack.push(item);
                    if (item.node.parent == null) {
                        if(item.node.left!=null){
                            root=item.node.left;
                        }
                        else{
                            root=item.node.right;
                        }
                        root.parent=null;
                    } else {
                        if (item.node.parent.key > item.node.key) {
                            if (item.node.left != null) {
                                item.node.parent.left = item.node.left;
                                item.node.left.parent=item.node.parent;
                            } else {
                                if (item.node.right != null) {
                                    item.node.parent.left = item.node.right;
                                    item.node.right.parent=item.node.parent;
                                } else {
                                    item.node.parent.left = null;
                                }
                            }
                        }
                        else{
                            if (item.node.left != null) {
                                item.node.parent.right = item.node.left;
                                item.node.left.parent=item.node.parent;
                            } else {
                                if (item.node.right != null) {
                                    item.node.parent.right = item.node.right;
                                    item.node.right.parent=item.node.parent;
                                } else {
                                    item.node.parent.right = null;
                                }
                            }
                        }
                    }
                }
                else{
                    if(item.command.equals("SecondDelete")){
                        StackItem3 item2=(StackItem3)redoStack.pop();
                        stack.push(item2);
                        stack.push(item);
                        if(item.node.parent.key>item.node.key){
                            item.node.parent.left=item.node.right;
                        }
                        else{
                            item.node.parent.right=item.node.right;
                        }
                        if(item.node.right!=null){
                            item.node.right.parent=item.node.parent;
                        }
                        item2.node.key=item.key;
                    }
                }
            }
        }
    }

    public void printPreOrder() {
        // TODO: implement your code here
        if (root != null) {
            preOrder(root);
        }
    }

    public void preOrder(Node x) {
        System.out.print(x.key + " ");
        if (x.left != null) {
            preOrder(x.left);
        }
        if (x.right != null) {
            preOrder(x.right);
        }
    }

    // TODO remove
    public void treeFormPrint() {
        if (root != null) treeFormPrint(root, "");
        else System.out.println("Empty tree");
    }

    // TODO remove
    private void treeFormPrint(Node node, String acc) {
        String signSpace = acc + "            ";
        if (node.right != null) {
            treeFormPrint(node.right, acc + "               ");
            if (node.right.parent == node)
                System.out.println(signSpace + "/");
            else System.out.println(signSpace + "$");
        }
        System.out.println(acc + "| key: " + node.key);
        System.out.println(acc + "| par: " + node.parent);
        if (node.left != null) {
            if (node.left.parent == node)
                System.out.println(signSpace + "\\");
            else System.out.println(signSpace + "$");
            treeFormPrint(node.left, acc + "               ");
        }
    }

    public static void main(String args[]) {
        Stack s1 = new Stack();
        Stack s2 = new Stack();
        BacktrackingBST tree = new BacktrackingBST(s1, s2);
        Node node100 = new Node(100, null);
        tree.insert(node100);
        Node node90 = new Node(90, null);
        tree.insert(node90);
        Node node50 = new Node(50, null);
        tree.insert(node50);
        Node node96 = new Node(96, null);
        tree.insert(node96);
        Node node93 = new Node(93, null);
        tree.insert(node93);
        Node node95 = new Node(95, null);
        tree.insert(node95);
        Node node94 = new Node(94, null);
        tree.insert(node94);
        Node node200 = new Node(200, null);
        tree.insert(node200);
        Node node180 = new Node(180, null);
        tree.insert(node180);
        Node node300 = new Node(300, null);
        tree.insert(node300);
        tree.printPreOrder();
        tree.delete(node100);
        System.out.println();
//        System.out.println("delete 100");
        tree.printPreOrder();
        tree.delete(tree.search(180));
        System.out.println();
//        System.out.println("delete 180");
        tree.printPreOrder();
        tree.insert(new Node(91,null));
        System.out.println();
//        System.out.println("insert 91");
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
//        System.out.println("undo 91");
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
//        System.out.println("undo 180");
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
//        System.out.println("undo 100");
        tree.printPreOrder();
        tree.retrack();
        System.out.println();
        tree.printPreOrder();
        tree.retrack();
        System.out.println();
        tree.printPreOrder();
        tree.retrack();
        System.out.println();
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
        tree.printPreOrder();
        tree.backtrack();
        System.out.println();
        tree.printPreOrder();
    }

    @Override
    public void print() {
        // TODO: implement your code here
        printPreOrder();
    }

    public class StackItem3 {
        private Node node;
        private String command;
        private int key;

        private StackItem3(Node node, int key, String command) {
            this.node = node;
            this.command = command;
            this.key = key;
        }

        private void setCommand(String command) {
            this.command = command;
        }
    }


    public static class Node {
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;

        private BacktrackingBST.Node parent;
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

}
