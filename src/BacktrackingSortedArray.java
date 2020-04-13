public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    int currentSize;
//     TODO: implement your code here

    //     Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        currentSize = 0;
    }

    @Override
    public Integer get(int index) {
//         TODO: implement your code here
        if (index <= currentSize)
            return arr[index];
        return null;
    }

    @Override
    public Integer search(int x) {
//         TODO: implement your code here
        int low = 0;
        int high = currentSize - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == x) {
                return mid;
            } else {
                if (arr[mid] > x) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    @Override
    public void insert(Integer x) {
//         TODO: implement your code here
        int index = currentSize;
        while (index > 0 && arr[index - 1] > x) {
            arr[index] = arr[index - 1];
            index = index - 1;
        }
        arr[index] = x;
        StackItem2 item = new StackItem2(index, x, "Insert");
        stack.push(item);
        currentSize = currentSize + 1;
    }

    @Override
    public void delete(Integer index) {
//         TODO: implement your code here
        if (index <= currentSize) {
            StackItem2 item = new StackItem2(index, arr[index], "Delete");
            stack.push(item);
            for (int i = index + 1; i <= currentSize; i = i + 1) {
                arr[i - 1] = arr[i];
            }
            currentSize = currentSize - 1;
        }
    }

    @Override
    public Integer minimum() {
//         TODO: implement your code here
        return 0;
    }

    @Override
    public Integer maximum() {
//         TODO: implement your code here
        return currentSize - 1;
    }

    @Override
    public Integer successor(Integer index) {
//         TODO: implement your code here
        if (index >= currentSize - 1) {
            return -1;
        }
        return index + 1;
    }

    @Override
    public Integer predecessor(Integer index) {
//         TODO: implement your code here
        if(index<=0){
            return -1;
        }
        return index - 1;
    }

    @Override
    public void backtrack() {
//         TODO: implement your code here
        if (!stack.isEmpty()) {
            StackItem2 item=(StackItem2) stack.pop();
            if (item.command.equals("Delete")) {
                int index = item.index;
                int value = item.value;
                for (int i = currentSize; i >= index; i = i - 1) {
                    arr[i] = arr[i - 1];
                }
                arr[index] = value;
                currentSize = currentSize + 1;
            }
            else {
                if (item.command.equals("Insert")) {
                    for (int i = item.index + 1; i <= currentSize; i = i + 1) {
                        arr[i - 1] = arr[i];
                    }
                    currentSize = currentSize - 1;
                }
            }
            System.out.print("backtracking performed");
        }
    }

    @Override
    public void retrack() {
//         Do not implement anything here!!
    }

    @Override
    public void print() {
//         TODO: implement your code here
        for (int i = 0; i < currentSize; i = i + 1) {
            System.out.print(arr[i] + " ");
        }
    }

    public class StackItem2 {
        private String command;
        private int index;
        private int value;

        private StackItem2(int index, int value, String command) {
            this.index = index;
            this.value = value;
            this.command = command;
        }
    }
}
