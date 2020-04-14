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
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
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
        StackItem3 item = new StackItem3(copyNode(z), copyNode(z.parent), copyNode(z.right), copyNode(z.left), "Insert");
        stack.push(item);
    }

    public void delete(Node x) {
        // TODO: implement your code here
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
        Node y;
        Node z;
        if (x.left == null | x.right == null) {
            y = x;
        } else {
            y = successor(x);
            StackItem3 item2 = new StackItem3(copyNode(y), copyNode(y.parent), copyNode(y.right), copyNode(y.left), "Delete");
            stack.push(item2);
        }
        StackItem3 item1 = new StackItem3(copyNode(x), copyNode(x.parent), copyNode(x.right), copyNode(x.left), "Delete");
        stack.push(item1);
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
            y.parent = x.parent;
            y.left = x.left;
            y.right = x.right;
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
            StackItem3 toPush=new StackItem3(item.node,item.father,item.rightSon,item.leftSon,item.command);
            redoStack.push(toPush);
            Node father = null;
            if (item.father != null) {
                father = search(item.father.key);
            }
            if (item.command.equals("Insert")) {
                if (father == null) {
                    root = null;
                } else {
                    if (father.key > item.node.key) {
                        father.left = null;
                    } else {
//                        search(item.father.key).right = null;
                        father.right=null;
                    }
                }
            } else {
                if (item.command.equals("Delete")) {
                    Node rightSon = null;
                    Node leftSon = null;
                    if (item.rightSon != null) {
                        rightSon = search(item.rightSon.key);
                    }
                    if (item.leftSon != null) {
                        leftSon = search(item.leftSon.key);
                    }

                    if (father == null) {
                        root = item.node;
                        if (leftSon != null) {
                            root.left = leftSon;
                            leftSon.parent = root;
                        }
                        if (rightSon != null) {
                            root.right = rightSon;
                            rightSon.parent = root;
                        }
                    }
                    else {
                        if (father.key > item.node.key) {
                            father.left=item.node;
                        } else {
                            father.right=item.node;
                        }
                        item.node.parent=father;
                        if(leftSon!=null){
                            item.node.left=leftSon;
                            leftSon.parent=item.node;
                        }
                        if(rightSon!=null){
                            item.node.right=rightSon;
                            rightSon.parent=item.node;
                        }
                    }
                    if (item.node.left != null & item.node.right != null) {
                        StackItem3 item2 = (StackItem3) stack.pop();
                        item2.setCommand("SecondDelete");
                        StackItem3 toPush2=new StackItem3(item2.node,item2.father,item2.rightSon,item2.leftSon,item2.command);
                        redoStack.push(toPush2);
                        if (item2.rightSon != null) {
                            item2.node.right = search(item2.rightSon.key);
                        }
                        if (item2.father.key > item2.node.key) {
                            search(item2.father.key).left = item2.node;
                        } else {
                            search(item2.father.key).right = item2.node;
                        }
                        item2.node.parent = search(item2.father.key);
                    }
                }
            }
//            System.out.println("backtracking performed");
        }
    }


    @Override
    public void retrack() {
        // TODO: implement your code here
        if (!redoStack.isEmpty()) {
            StackItem3 item = (StackItem3) redoStack.pop();
            StackItem3 toPush=new StackItem3(copyNode(item.node),copyNode(item.father),copyNode(item.rightSon),copyNode(item.leftSon),item.command);
            Node father=null;
            if(item.father!=null){
                father=search(item.father.key);
            }
            if (item.command.equals("Insert")) {
                stack.push(toPush);
                if (father == null) {
                    root = item.node;
                } else {
                    if (item.father.key > item.node.key) {
                        father.left = item.node;
                    }
                    else {
                        father.right = item.node;
                    }
                    item.node.parent=father;
                }
            } else {
                if (item.command.equals("Delete")) {
                    stack.push(toPush);
                    if (item.father == null) {
                        root = null;
                    } else {
                        if (item.father.key > item.node.key) {
                            if(item.leftSon!=null){
                                search(item.node.key).parent.left = search(item.leftSon.key);
                            }
                            else{
                                if(item.rightSon!=null){
                                    search(item.node.key).parent.left = search(item.rightSon.key);
                                }
                                else{
                                    search(item.node.key).parent.left = null;
                                }
                            }
                        } else {
                            if(item.leftSon!=null){
                                search(item.node.key).parent.right = search(item.leftSon.key);
                            }
                            else{
                                if(item.rightSon!=null){
                                    search(item.node.key).parent.right = search(item.rightSon.key);
                                }
                                else{
                                    search(item.node.key).parent.right = null;
                                }
                            }
                        }
                    }
                } else {
                    if (item.command.equals("SecondDelete")) {
                        StackItem3 item2 = (StackItem3) redoStack.pop();
                        toPush.setCommand("Delete");
                        stack.push(toPush);
                        StackItem3 toPush2=new StackItem3(copyNode(item2.node),copyNode(item2.father),copyNode(item2.rightSon),copyNode(item2.leftSon),item2.command);
                        stack.push(toPush2);
                        Node node = search(item.node.key);
                        Node node2 = search(item2.node.key);
                        if (node.parent.key > node.key) {
                            node.parent.left = node.right;
                        } else {
                            node.parent.right = node.right;
                        }
                        if(node.right!=null){
                            node.right.parent = node.parent;
                        }
                        node2.key = node.key;
                        node.parent = node2.parent;
                        node.right = node2.right;
                        node.left = node2.left;
                    }
                }
            }
        }
    }

    public void printPreOrder() {
        // TODO: implement your code here
        preOrder(root);
//        stack.push(copyNode(root));
//        boolean isFinish=false;
//        if(root==null){
//            isFinish=true;
//        }
//        while (!isFinish){
//            Node curr=(Node)stack.pop();
//            System.out.print(curr.key+" ");
//            Node right=copyNode(search(curr.key).right);
//            Node left=copyNode(search(curr.key).left);
//            if(right!=null){
//                stack.push(right);
//            }
//            if(left!=null){
//                stack.push(left);
//            }
//            if(curr.key==maximum().key){
//                isFinish=true;
//            }
//        }
    }

    public void preOrder(Node x) {
//        while (x != null) {
//            if (x.left == null) {
//                System.out.print(x.key + " ");
//                x = x.right;
//            } else {
//                Node curr = x.left;
//                while (curr.right != null && curr.right != x) {
//                    curr = curr.right;
//                }
//                if (curr.right == x) {
//                    curr.right = null;
//                    x = x.right;
//                } else {
//                    System.out.print(x.key + " ");
//                    curr.right = x;
//                    x = x.left;
//                }
//            }
//        }
        System.out.print(x.key + " ");
        if (x.left != null) {
            preOrder(x.left);
        }
        if (x.right != null) {
            preOrder(x.right);
        }
    }

    public static void main(String args[]) {
        Stack s1 = new Stack();
        Stack s2 = new Stack();
        BacktrackingBST tree = new BacktrackingBST(s1, s2);
        Node a = new Node(100, null);
        Node b = new Node(90, null);
        Node c = new Node(50, null);
        Node d = new Node(96, null);
        Node e = new Node(93, null);
        Node f = new Node(95, null);
        Node n = new Node(94, null);
        Node g = new Node(200, null);
        Node h = new Node(180, null);
        Node i = new Node(300, null);
        tree.insert(a);
        tree.insert(b);
        tree.insert(c);
        tree.insert(d);
        tree.insert(e);
        tree.insert(f);
        tree.insert(n);
        tree.insert(g);
        tree.insert(h);
        tree.insert(i);
        tree.printPreOrder();
        tree.delete(i);
        System.out.println();
        tree.printPreOrder();
        tree.delete(b);
        System.out.println();
        tree.printPreOrder();
        tree.insert(new Node(1, null));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(h.key));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(e.key));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(f.key));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(d.key));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(c.key));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(n.key));
        System.out.println();
        tree.printPreOrder();
        tree.insert(new Node(120, null));
        System.out.println();
        tree.printPreOrder();
        tree.delete(tree.search(a.key));
        System.out.println();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        System.out.println();
        System.out.println("Start backtracking");
        System.out.println();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
        tree.printPreOrder();
        System.out.println();
        tree.backtrack();
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
        tree.retrack();
        System.out.println();
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
        tree.retrack();
        System.out.println();
        tree.printPreOrder();
        tree.retrack();
        System.out.println();
        tree.printPreOrder();
        tree.retrack();
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
        private Node father;
        private String command;
        private Node rightSon;
        private Node leftSon;

        private StackItem3(Node node, Node father, Node rightSon, Node leftSon, String command) {
            this.node = node;
            this.father = father;
            this.command = command;
            this.rightSon = rightSon;
            this.leftSon = leftSon;
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
