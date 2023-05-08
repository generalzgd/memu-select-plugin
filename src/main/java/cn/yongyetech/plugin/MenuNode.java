package cn.yongyetech.plugin;

import javax.swing.tree.*;
import java.util.List;

public class MenuNode extends DefaultMutableTreeNode  {

//    private Date ctime;
//    private Date mtime;

    private MenuBean bean;

    public MenuNode() {}

    public MenuNode(MenuBean bean) {
        setBean(bean);

        this.setUserObject(bean.name);
    }

    public void setBean(MenuBean bean) {
        this.bean = bean;
    }

    public MenuBean getBean(){
        return this.bean;
    }

    @Override
    public void setUserObject(Object userObject) {
        super.setUserObject(userObject);
    }

    @Override
    public Object getUserObject(){
        return super.getUserObject();
    }

    public void addNodes(List<MenuNode> list) {
        for (MenuNode node : list) {
            add(node);
        }
    }

    @Override
    public void add(MutableTreeNode newChild) {
        if(matchPid((MenuNode) newChild)) {
            super.add(newChild);
        } else {
            if(super.getChildCount()<1){
                return;
            }
            for(TreeNode child: super.children){
                ((DefaultMutableTreeNode)child).add(newChild);
            }
        }
    }

    private boolean matchPid(MenuNode newChild) {
        int id = this.getBean()!=null?this.getBean().id:0;

        int pid = newChild.getBean()!=null?newChild.getBean().pid:0;

        return id == pid;
    }

}
