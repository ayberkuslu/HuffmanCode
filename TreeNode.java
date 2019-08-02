public class TreeNode{
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;
        
        
        private int key = -1;
        private int freq = -1;
        private boolean isLeaf = false;
        
        public TreeNode() { 
        	
        	this.key = -1;
        	this.freq = -1;
        }
        
        public TreeNode(int freq, int i) {  
                this.key = i;
                this.freq = freq;
        }
        
        public TreeNode getParent() {
                return parent;
        }

        public void setParent(TreeNode parent) {
                this.parent = parent;
        }

        public TreeNode getLeft() {
                return left;
        }

        public void setLeft(TreeNode left) {
                this.left = left;
        }

        public TreeNode getRight() {
                return right;
        }

        public void setRight(TreeNode right) {
                this.right = right;
        }

        public int getKey() {
                return key;
        }

        public void setKey(int key) {
                this.key = key;
        }

        public void setFreq(int freq) {
                this.freq = freq;
        }

        public int getFreq() {
                return freq;
        }
        
        public void setToLeaf() {
                isLeaf = true;
        }
        
        public boolean isLeaf() {
                return isLeaf;
        }
}