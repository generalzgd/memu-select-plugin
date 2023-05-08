package cn.yongyetech.plugin;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MenuReader {
    private static final Logger LOG = Logger.getLogger(MenuReader.class.getName());

    public static TreeModel read(int sysId){

        List<MenuNode> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from ops_permission where id>10000 and system_id=? and level>0 and menu=1 and state=0");
            ps.setString(1, String.valueOf(sysId));
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                MenuBean data = new MenuBean(rs);
                MenuNode node = new MenuNode(data);
                list.add(node);
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        MenuNode root = new MenuNode();
        if(sysId==1){
            root.setUserObject("管理后台");
        } else {
            root.setUserObject("客服工作台");
        }
        //
        int level = 1;
        while (list.size() > 0) {
            List<MenuNode> nodes = getNodesByLevel(list, level);
            root.addNodes(nodes);
            level++;
        }
        return new DefaultTreeModel(root);
    }

    private static List<MenuNode> getNodesByLevel(List<MenuNode> all, int level) {
        List<MenuNode> out = new ArrayList<>();

        for(int i=0;i<all.size();i++) {
            MenuNode node = all.get(i);
            MenuBean data = node.getBean();
            if (data.level==level) {
                out.add(node);
            }
        }

        all.removeAll(out);
        return out;
    }


    public static TreeModel getDefaultTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("系统");
        DefaultMutableTreeNode      parent;

        parent = new DefaultMutableTreeNode("A");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("blue"));
        parent.add(new DefaultMutableTreeNode("violet"));
        parent.add(new DefaultMutableTreeNode("red"));
        parent.add(new DefaultMutableTreeNode("yellow"));

        parent = new DefaultMutableTreeNode("B");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("basketball"));
        parent.add(new DefaultMutableTreeNode("soccer"));
        parent.add(new DefaultMutableTreeNode("football"));
        parent.add(new DefaultMutableTreeNode("hockey"));

        parent = new DefaultMutableTreeNode("C");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("hot dogs"));
        parent.add(new DefaultMutableTreeNode("pizza"));
        parent.add(new DefaultMutableTreeNode("ravioli"));
        parent.add(new DefaultMutableTreeNode("bananas"));
        return new DefaultTreeModel(root);
    }

    public static TreeModel getDefaultTreeModel2() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("系统2");
        DefaultMutableTreeNode      parent;

        parent = new DefaultMutableTreeNode("Aa");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("blue"));
        parent.add(new DefaultMutableTreeNode("violet"));
        parent.add(new DefaultMutableTreeNode("red"));
        parent.add(new DefaultMutableTreeNode("yellow"));

        parent = new DefaultMutableTreeNode("Bb");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("basketball"));
        parent.add(new DefaultMutableTreeNode("soccer"));
        parent.add(new DefaultMutableTreeNode("football"));
        parent.add(new DefaultMutableTreeNode("hockey"));

        parent = new DefaultMutableTreeNode("Cc");
        root.add(parent);
        parent.add(new DefaultMutableTreeNode("hot dogs"));
        parent.add(new DefaultMutableTreeNode("pizza"));
        parent.add(new DefaultMutableTreeNode("ravioli"));
        parent.add(new DefaultMutableTreeNode("bananas"));
        return new DefaultTreeModel(root);
    }

}
