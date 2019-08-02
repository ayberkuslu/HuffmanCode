public class PQ {
        private TreeNode[] heap;
        private int size = 0;
        
        public int getSize() {
                return size;
        }

        public PQ(int i){
                 heap = new TreeNode[i];
                 size = 0;
        }
                
        public void insert(TreeNode k) {
                size++;
                int i = size;
                while( (i > 1 && heap[parent(i)].getFreq() > k.getFreq()) 
                		
                		
                		|| ( i > 1 && heap[parent(i)].getFreq() == k.getFreq() &&  heap[parent(i)].getKey() > k.getKey() )
               		
                		) {
                        heap[i] = heap[parent(i)];
                        i = parent(i);
                }
                heap[i] = k;
        }

        public TreeNode getMin(){
                if(size != 0)
                        return heap[1];
                return null;
        }
        
        public TreeNode delMin() {
                if(size != 0) {
                        TreeNode min = heap[1];
                        heap[1] = heap[size];
                        size--;
                        heapify(1);
                        return min;
                }
                return null;
        }
        
        public void heapify(int i) {
                int l = left(i);
                int r = right(i);
                int smallest;
                if(r <= size) {
                        if(heap[l].getFreq() < heap[r].getFreq()  
                        	
                        		 ||(heap[l].getFreq() == heap[r].getFreq() && 
                        		 heap[l].getKey() < heap[r].getKey())
                        		
                        		)
                                smallest = l;
                        else
                                smallest = r;
                        if(	heap[i].getFreq() > heap[smallest].getFreq()
                        	||  (  heap[i].getFreq() == heap[smallest].getFreq()  && 
                        	heap[i].getKey() > heap[smallest].getKey()   )    	)
                        {
                                swap(i, smallest);
                                heapify(smallest);
                        }
                }
                else if( (l == size && heap[i].getFreq() > heap[l].getFreq() )
                		
                		|| (l == size && heap[i].getFreq() == heap[l].getFreq() &&
                		heap[i].getKey() > heap[l].getKey() )
                		
                		) {
                        swap(i, l);
                }               
        }

        private void swap(int i, int l) {
                TreeNode tmp = heap[i];
                heap[i] = heap[l];
                heap[l] = tmp;
        }
        
        public int parent(int i) {
                return i/2;
        }
        
        public int left(int i) {
                return 2*i;
        }
        
        public int right(int i) {
                return 2*i+1;
        }
        
//      public static  void printInorder(TreeNode node)
//        {
//            if (node == null)
//                return;
//     
//            /* first recur on left child */
//            printInorder(node.getLeft());
//     
//            /* then print the data of node */
//            System.out.print(node.getKey() + " ");
//     
//            /* now recur on right child */
//            printInorder(node.getRight());
//        }
        
        
        public static void main(String[] args) {
        	
        	
        	PQ heap = new PQ(255+1);
        	heap.insert(new TreeNode(20,20));
        	heap.insert(new TreeNode(20,19));
        	heap.insert(new TreeNode(20,21));
        	heap.insert(new TreeNode(20,1));
        	heap.insert(new TreeNode(20,7));
        	heap.insert(new TreeNode(20,35));
        	heap.insert(new TreeNode(20,34));
        	heap.insert(new TreeNode(20,2));
        	
//        	heap.insert(new TreeNode(1,(int)'b'));
//        	heap.insert(new TreeNode(1,(int)'e'));
//        	heap.insert(new TreeNode(2,(int)'c'));
        //	heap.insert(new TreeNode((int)'b',1));

//        	System.out.println(heap.size +" " + heap.heap.length);
        	
        	//printInorder(heap.heap[1]);
        	
//        	System.out.println((char)heap.heap[1].getKey());
//        	System.out.println((char)heap.heap[2].getKey());
//        	System.out.println((char)heap.heap[3].getKey());

        	
        	




//        System.out.println( heap.delMin().getFreq());
//        System.out.println( heap.delMin().getFreq());
//        System.out.println( heap.delMin().getFreq());
//        System.out.println( heap.delMin().getFreq());
//        System.out.println( heap.delMin().getFreq());


        System.out.println( heap.delMin().getKey());
        System.out.println( heap.delMin().getKey());
        System.out.println( heap.delMin().getKey());
        System.out.println( heap.delMin().getKey());
        System.out.println( heap.delMin().getKey());
        System.out.println( heap.delMin().getKey());

        


      
        	
        }
        
        
}