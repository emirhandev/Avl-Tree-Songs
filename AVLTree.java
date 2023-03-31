public class AVLTree {

    AVLNode root;

    public AVLTree() {
        root = null;
    }

    public int height(AVLNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.height;
        }
    }

    public AVLNode rotateMyLeft(AVLNode focus) {
        AVLNode k = focus.left;
        focus.left = k.right;
        k.right = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    public AVLNode rotateMyRight(AVLNode focus) {
        AVLNode k = focus.right;
        focus.right = k.left;
        k.left = focus;
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        k.height = Math.max(height(k.left), height(k.right)) + 1;
        return k;
    }

    public AVLNode doubleRotateLeftSide(AVLNode focus) {
        focus.left = rotateMyRight(focus.left);
        return rotateMyLeft(focus);
    }

    public AVLNode doubleRotateRightSide(AVLNode focus) {
        focus.right = rotateMyLeft(focus.right);
        return rotateMyRight(focus);
    }

    int getBalance(AVLNode focus) {
        if (focus == null) {
            return 0;
        }
        return height(focus.left) - height(focus.right);
    }

    public AVLNode insert(AVLNode focus, String data, int index) {
        if (focus == null) {
            focus = new AVLNode(data, index);
        } else if (data.compareTo(focus.data) < 0) {
            focus.left = insert(focus.left, data, index);
            if (getBalance(focus) == 2) {
                if (data.compareTo(focus.data) < 0) {
                    focus = rotateMyLeft(focus);
                } else {
                    focus = doubleRotateLeftSide(focus);
                }
            }
        } else if (data.compareTo(focus.data) > 0) {
            focus.right = insert(focus.right, data, index);
            if (getBalance(focus) == -2) {
                if (index > focus.right.index) {
                    focus = rotateMyRight(focus);
                } else {
                    focus = doubleRotateRightSide(focus);
                }
            }
        } else {
            focus.data = data;
        }

        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        return focus;
    }

    public void insert(String data, int index) {
        root = insert(root, data, index);
    }

    AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }


    AVLNode deleteNode(AVLNode focus, String data) {
        if (focus == null) {
            return focus;
        }
        
        if (data.compareTo(focus.data) < 0) { 
            focus.left = deleteNode(focus.left, data);
        }
        else if (data.compareTo(focus.data) > 0) { 
            focus.right = deleteNode(focus.right, data);
        }  
        else {
            if ((focus.left == null) || (focus.right == null)) {
                AVLNode temp = null;
                if (null == focus.left) {
                    temp = focus.right;
                } else { 
                    temp = focus.left;
                } 
                if (temp == null) {
                    temp = focus;
                    focus = null;
                } else{
                    focus = temp;  
                } 
            } else {
                AVLNode temp = minValueNode(focus.right);
                focus.data = temp.data;
                focus.index = temp.index;
                focus.right = deleteNode(focus.right, temp.data);
            }
        }
        if (focus == null) {
            return focus;
        } 
        focus.height = Math.max(height(focus.left), height(focus.right)) + 1;
        int balance = getBalance(focus);
        
        if (balance > 1 && getBalance(focus.left) >= 0) {
            return rotateMyLeft(focus);
        }
        
        if (balance > 1 && getBalance(focus.left) < 0) {
            focus.left = rotateMyRight(focus.left);
            return rotateMyLeft(focus);
        }

        if (balance < -1 && getBalance(focus.right) <= 0) {
            return rotateMyRight(focus);
        }

        if (balance < -1 && getBalance(focus.right) > 0) {
            focus.right = rotateMyLeft(focus.right);
            return rotateMyRight(focus);
        }
        return focus;
    }
    
}

class AVLNode {
    public int index; 
    public String data;
    public AVLNode left;
    public AVLNode right;
    public int height = 1; 
    
    public AVLNode(String data, int index){
        this.data= data;
        this.index = index;
        left = null;
        right = null;
        height = 1;
    }   
}