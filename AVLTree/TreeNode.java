package AVL;

/**
 * Created by long on 2016/11/18.
 */
public class TreeNode<E> {
    private static final int LH = 1;	//左高
    private static final int EH = 0;    //等高
    private static final int RH = -1;	//右高

    E element;
    TreeNode<E> parent;
    TreeNode<E> left;
    TreeNode<E> right;
    int balance = EH;	//平衡因子，只能为1或0或-1

    public TreeNode(E element,TreeNode<E> parent) {
        this.element = element;
        this.parent = parent;
    }
}
