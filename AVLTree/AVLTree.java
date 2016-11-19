package AVL;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
* Created by long on 2016/11/18.
*/
public class AVLTree<E> {
    private static final int LH = 1;	//左高
    private static final int EH = 0;    //等高
    private static final int RH = -1;	//右高
    /**
     * 根节点
     */
    private TreeNode<E> root=null;
    /**
     * 树中元素个数
     */
    private int size=0;
    public AVLTree(){

    }

    public int GetSize(){
        return size;
    }
    //将以p为节点的树左旋
    private void rotateLeft(TreeNode<E> p){
        System.out.println("绕"+p.element+"左旋");
        if(p!=null){
            TreeNode<E> r=p.right;
            p.right = r.left;

            if (r.left != null) //如果B的左节点BL不为空，把BL的父节点设为A
                r.left.parent = p;
            r.parent = p.parent;  //A的父节点设为B的父节点
            if (p.parent == null)         //如果p是根节点
                root = r;                 //r变为父节点，即B为父节点
            else if(p.parent.left ==p)
                p.parent.left=r;
            else
                p.parent.right=r;
            r.left=p;
            p.parent=r;

        }

    }
    //将以p为节点的树右旋
    private void rotateRight(TreeNode<E> p){
        System.out.println("绕"+p.element+"右旋");
        if(p!=null){
            TreeNode<E> r=p.left;
            p.left = r.right;
            if(r.right!=null)//如果r的右子数不为空，则将有r右子树的双亲p
                r.right.parent=p;
            r.parent=p.parent;
            if(p.parent==null)//如果p的双亲为空，那么p就是根节点，那么就将根节点变成r
                root=r;
            else if(p.parent.left==p)//p是父节点的左孩子，然后将父节点的左孩子变成r
                p.parent.left=r;
           else//p是父节点的右孩子，然后将父节点的右孩子变成r
                p.parent.right=r;
            r.right=p;
            p.parent=r;

        }

    }

    //插入算法
    public boolean add(E element){
        TreeNode<E> t = root;
        if(t==null){
            root=new TreeNode<E>(element,null);
            size=1;
            return true;
        }
        int cmp;
        TreeNode<E> parent;
        Comparable<? super E> e = (Comparable<? super  E>)element;
        do{
            parent=t;
            cmp=e.compareTo(t.element);
            if(cmp<0)
                t=t.left;
            else if(cmp>0)
                t=t.right;
            else {
                return  false;
            }

        }while (t!=null);
        TreeNode<E> child= new TreeNode<E>(element,parent);
        if(cmp>0){
            parent.right=child;
        }else{
            parent.left=child;
        }
        while(parent!=null){
            cmp = e.compareTo(parent.element);
            if(cmp<0)
                parent.balance++;
            else
                parent.balance--;
            if(parent.balance==0){
                break;
            }
            if(Math.abs(parent.balance)==2){//最小不平衡根子树节点
                fixAfterInsertion(parent);
                break;
            }
            parent=parent.parent;
        }
        size++;

        return true;



    }
    public  void fixAfterInsertion(TreeNode<E> p){
        if(p.balance==2){
            leftBalance(p);
        }else {
            rightBalance(p);
        }


    }
    public boolean leftBalance(TreeNode<E> p){
        boolean heightLower=true;
        TreeNode<E> l = p.left;
        switch (l.balance){
            case LH: //左边高
                l.balance=p.balance=EH;
                rotateRight(p);
                break;
            case RH:
                TreeNode<E> rd=l.right;
                switch (rd.balance){

                    case LH:
                        p.balance=RH;
                        l.balance=EH;
                        break;
                    case RH:
                        l.balance=EH;
                        p.balance=LH;

                        break;
                    case EH:
                        l.balance=  p.balance=EH;

                        break;
                }
                rd.balance = EH;
                rotateLeft(p.left);
                rotateRight(p);



            break;
            case EH:
                p.balance=LH;
                l.balance=RH;
                rotateRight(p);
                heightLower=false;

                break;

        }
        return heightLower;

    }
    private boolean rightBalance(TreeNode<E> t){
        boolean heightLower = true;//看高度是否发生变化，若变化则继续回溯
        TreeNode<E> r = t.right;
        switch (r.balance) {
            case LH:            //左高，分情况调整
                TreeNode<E> ld = r.left;
                switch (ld.balance) {   //调整各个节点的BF
                    case LH:    //情况1
                        t.balance = EH;
                        r.balance = RH;
                        break;
                    case EH:    //情况2
                        t.balance = r.balance = EH;
                        break;
                    case RH:    //情况3
                        t.balance = LH;
                        r.balance = EH;
                        break;
                }
                ld.balance = EH;
                rotateRight(t.right);
                rotateLeft(t);
                break;
            case RH:            //右高，左旋调整
                t.balance = r.balance = EH;
                rotateLeft(t);
                break;
            case EH:       //特殊情况4
                r.balance = LH;
                t.balance = RH;
                rotateLeft(t);
                heightLower = false;
                break;
        }
        return heightLower;
    }
    /**
     * 查找指定元素，AVL.TreeNode对象，否则返回null
     */
    private TreeNode<E> getTreeNode(Object element) {
        TreeNode<E> tmp = root;
        Comparable<? super E> e = (Comparable<? super E>) element;
        int c;
        while (tmp != null) {
            c = e.compareTo(tmp.element);
            if (c == 0) {
                return tmp;
            } else if (c < 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return null;
    }


    //删除节点
    public boolean remove(Object o){
        TreeNode<E> e =getTreeNode(o);

        if(e!=null){
            deleteTreeNode(e);
            return true;
        }
        return false;
    }

    public void deleteTreeNode(TreeNode<E> p){
        size--;
        if(p.left!=null&&p.right!=null){
            TreeNode<E> b=successor(p);
            p.element=b.element;
            p=b;

        }
        TreeNode<E> replacement = (p.left != null ? p.left : p.right);
        if(replacement!=null) {//左或者右子树有一个不为空
            replacement.parent=p.parent;
            if(p.parent==null)
                root=replacement;
            if(p.parent.left==p)//是左孩子
                p.parent.left=p;
            if(p.parent.right==p)//是右孩子
                p.parent.right=p;
            p.left = p.right = p.parent = null;     //p的指针清空
            fixAfterDeletion(replacement);


        }else if(p.parent==null){
            root=null;
        }else {//p没有左右孩子
            fixAfterDeletion(p);
            if(p.parent!=null){
                if(p==p.parent.left)
                    p.parent.left=null;
                if(p==p.parent.right)
                    p.parent.right=null;
                p.parent=null;
            }

        }



    }
    /**
     * 删除某节点p后的调整方法：
     * 1.从p开始向上回溯，修改祖先的BF值，这里只要调整从p的父节点到根节点的BF值，
     * 调整原则为，当p位于某祖先节点(简称A)的左子树中时，A的BF减1，当p位于A的
     * 右子树中时A的BF加1。当某个祖先节点BF变为1或-1时停止回溯，这里与插入是相反的，
     * 因为原本这个节点是平衡的，删除它的子树的某个节点并不会改变它的高度
     *
     * 2.检查每个节点的BF值，如果为2或-2需要进行旋转调整，调整方法如下文，
     * 如果调整之后这个最小子树的高度降低了，那么必须继续从这个最小子树的根节点(假设为B)继续
     * 向上回溯，这里和插入不一样，因为B的父节点的平衡性因为其子树B的高度的改变而发生了改变，
     * 那么就可能需要调整，所以删除可能进行多次的调整。
     *
     */
    private void fixAfterDeletion(TreeNode<E> p){
        boolean heightLower = true;
        TreeNode<E> t = p.parent;
        Comparable<? super E> e = (Comparable<? super E>)p.element;
        int cmp;
        //自下向上回溯，查找不平衡的节点进行调整
        while (t!=null&&heightLower){
            cmp=e.compareTo(t.element);

            if(cmp >= 0 ){
                t.balance ++;
            }else{
                t.balance --;
            }
            if(Math.abs(t.balance) == 1){   //父节点经过调整平衡因子后，如果为1或-1，说明调整之前是0，停止回溯。
                break;
            }
            TreeNode<E> r = t;
            if(t.balance==2) {
                heightLower = leftBalance(r);
            }
            if(t.balance==-2) {
                heightLower = rightBalance(r);
            }
            t=t.parent;

        }



    }
    /**
     * 返回以中序遍历方式遍历树时，t的直接后继
     */
    static <E> TreeNode<E> successor(TreeNode<E> t) {
       if(t==null)
           return null;
        else if(t.right!=null){
           TreeNode<E> p=t.right;
           while (p.left!=null)
               p=p.left;
           return p;
       }else {
           TreeNode<E> p=t.parent;
           TreeNode<E> ch=t;
           while (p!=null&&ch==p.right){
               ch=p;
               p=p.parent;
           }
           return p;

       }


    }

    //返回中序遍历此树的迭代器,返回的是一个有序列表
    private class BinarySortIterator implements Iterator<E>{
        TreeNode<E> next;
        TreeNode<E> lastReturned;

        public BinarySortIterator(){
            TreeNode<E> s = root;
            if(s !=null){
                while(s.left != null){	//找到中序遍历的第一个元素
                    s = s.left;
                }
            }
            next = s;	//赋给next
        }

        @Override
        public boolean hasNext() {
            return next!=null;
        }

        @Override
        public E next() {
            TreeNode<E> e = next;
            if (e == null)
                throw new NoSuchElementException();
            next = successor(e);
            lastReturned = e;
            return e.element;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            // deleted entries are replaced by their successors
            if (lastReturned.left != null && lastReturned.right != null)
                next = lastReturned;
            deleteTreeNode(lastReturned);
            lastReturned = null;
        }
    }

    public Iterator<E> itrator(){
        return new BinarySortIterator();
    }



    //测试，你也可以任意测试
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        System.out.println("------添加------");
        tree.add(50);
        System.out.print(50+" ");
        tree.add(66);
        System.out.print(66+" ");
        for(int i=0;i<10;i++){
            int ran = (int)(Math.random() * 100);
            System.out.print(ran+" ");
            tree.add(ran);
        }
        System.out.println("------删除------");
        tree.remove(50);
        tree.remove(66);

        System.out.println();
        Iterator<Integer> it = tree.itrator();
        while(it.hasNext()){
            System.out.print(it.next()+" ");
        }
    }




}
