public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int currentSize;
    // TODO: implement your code here

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        currentSize = 0;
    }

    @Override
    public Integer get(int index) {
        // TODO: implement your code here
        if(index<=currentSize)
            return arr[index];
        return null;
    }

    @Override
    public Integer search(int x) {
        for (int i = 0; i < currentSize; i = i + 1) {
            if (arr[i] == x) {
                return i;
            }
        }
        return -1;
        // TODO: implement your code here
    }

    @Override
    public void insert(Integer x) {
        // TODO: implement your code here
        arr[currentSize] = x;
        StackItem1 item = new StackItem1(currentSize, x, "Insert");
        stack.push(item);
        currentSize = currentSize + 1;
    }


    @Override
    public void delete(Integer index) {
        // TODO: implement your code here
        if(index<=currentSize){
            StackItem1 item = new StackItem1(index, arr[index], "Delete");
            stack.push(item);
            for (int i = index + 1; i <= currentSize; i = i + 1) {
                arr[i - 1] = arr[i];
            }
            currentSize = currentSize - 1;
        }
    }

    @Override
    public Integer minimum() {
        int min =0;
        for (int i = 1; i <= currentSize; i = i + 1) {
            if (arr[i] < arr[min]) {
                min = i;
            }
        }
        return min;
        // TODO: implement your code here
    }

    @Override
    public Integer maximum() {
        // TODO: implement your code here
        int max = 0;
        for (int i = 1; i <= currentSize; i = i + 1) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public Integer successor(Integer index) {
        // TODO: implement your code here
        int curr = arr[index];
        int index_ans = -1;
        boolean isFound = false;
        for (int i = 0; i <= currentSize & !isFound; i = i + 1) {
            if (arr[i] > curr) {
                index_ans = i;
                isFound = true;
            }
        }
        if (isFound) {
            for (int i = 0; i <= currentSize; i = i + 1) {
                if (arr[i] > curr & arr[i] < arr[index_ans]) {
                    index_ans = i;
                }
            }
        }
        return index_ans;
    }

    @Override
    public Integer predecessor(Integer index) {
        // TODO: implement your code here
        int curr = arr[index];
        int index_ans = -1;
        boolean isFound = false;
        for (int i = 0; i <= currentSize & !isFound; i = i + 1) {
            if (arr[i] < curr) {
                index_ans = i;
                isFound=true;
            }
        }
        if (isFound) {
            for (int i = 0; i <= currentSize; i = i + 1) {
                if (arr[i] < curr & arr[i] > arr[index_ans]) {
                    index_ans = i;
                }
            }
        }
        return index_ans;
    }

    @Override
    public void backtrack() {
        // TODO: implement your code here
        if(!stack.isEmpty()){
            StackItem1 item = (StackItem1) stack.pop();
            if (item.command.equals("Delete")) {
                int index = item.index;
                int value = item.value;
                for (int i = currentSize; i>=index; i = i - 1) {
                    arr[i] = arr[i - 1];
                }
                arr[index]=value;
                currentSize = currentSize + 1;
            } else {
                if (item.command.equals("Insert")) {
                    arr[currentSize-1] = 0;
                    currentSize = currentSize - 1;
                }
            }
            System.out.print("backtracking performed");
        }
    }

    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    @Override
    public void print() {
        // TODO: implement your code here
        for (int i = 0; i < currentSize; i = i + 1) {
            System.out.print(arr[i] + " ");
        }
    }

    public class StackItem1 {
        private String command;
        private int index;
        private int value;

        private StackItem1(int index, int value, String command) {
            this.index = index;
            this.value = value;
            this.command = command;
        }
    }
}

