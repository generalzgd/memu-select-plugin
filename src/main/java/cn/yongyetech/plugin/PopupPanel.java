package cn.yongyetech.plugin;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.jdesktop.swingx.JXRadioGroup;
import org.jdesktop.swingx.JXTree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PopupPanel extends JPanel implements ActionListener {
    private static final Logger LOG = Logger.getLogger(JXTree.class.getName());
    private JXRadioGroup<JRadioButton> radioGroup;

    public JTree tree;

    public JButton ok;
    public JButton cancel;

    private int curSysId = 1;

    private Project curProj;
    private Document curDoc;
    private int offset;

    private List<MyCheckBox> boxes = new ArrayList<>();

    public PopupPanel(){
        super();
        drawUI();
        addListeners();

        //
        TreeModel model = MenuReader.read(curSysId);
//        TreeModel model = MenuReader.getDefaultTreeModel();
        tree.setModel(model);
    }

    public void setCurProj(Project project) {
        this.curProj = project;
    }

    public Project getCurProj(){
        return curProj;
    }

    public void setCurDoc(Document document) {
        this.curDoc = document;
    }

    public Document getCurDoc(){
        return curDoc;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public int getCurSysId() {
        return curSysId;
    }

    public int getBelong() {
        int out = 0;
        for(MyCheckBox box:boxes){
            if(box.isSelected()){
                out += box.getVal();
            }
        }
        return out;
    }

    public String[] getTreeSelected() {
        TreePath path = tree.getSelectionPath();
        if(path==null) {
            return new String[]{};
        }

        DefaultMutableTreeNode last = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (!last.isLeaf()){
            Notifier.notify(getCurProj(), "请选择最后一级菜单");
            throw new RuntimeException("not last menu");
        }

        Object[] list = path.getPath();

        List<String> out = new ArrayList<>();
        for(int i=1;i<list.length;i++) {
            DefaultMutableTreeNode obj = (DefaultMutableTreeNode) list[i];
            out.add((String) obj.getUserObject());
        }
        return out.toArray(new String[0]);
    }

    private void drawUI(){
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);

        this.setLayout(layout);

        makeSystemChoose();

        add(Box.createVerticalStrut(10));

        makeRoleChoose();

        add(Box.createVerticalStrut(10));

        makeTree();

        add(Box.createVerticalStrut(10));

        makeBtn();


    }

    private void makeSystemChoose() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setHgap(5);

        JPanel contain = new JPanel();
        add(contain);
        contain.setLayout(layout);


        JLabel label = new JLabel("系统选择: ");
        contain.add(label);

        radioGroup = new JXRadioGroup<>();
        contain.add(radioGroup);

        JRadioButton ops_radio = new JRadioButton("管理后台");
        ops_radio.setSelected(true);
        JRadioButton workbench_radio = new JRadioButton("客服工作台");
        radioGroup.add(ops_radio);
        radioGroup.add(workbench_radio);

        ops_radio.setActionCommand("ops");
        workbench_radio.setActionCommand("workbench");

    }

    private void makeRoleChoose() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        layout.setHgap(5);

        JPanel contain = new JPanel();
        add(contain);
        contain.setLayout(layout);

        JLabel label = new JLabel("角色类型：");
        contain.add(label);

        String[] boxName = {"管理员","人工客服","工单客服","质检客服"};
        int[] boxVal = {1,2,4,8};
        for(int i=0; i<boxName.length; i++) {
            MyCheckBox box = new MyCheckBox(boxName[i]);
            box.setVal(boxVal[i]);
            contain.add(box);
            boxes.add(box);
        }
    }

    private void makeTree() {
        BorderLayout layout = new BorderLayout(5,5);

        JPanel contain = new JPanel();
        contain.setLayout(layout);
        add(contain);



        JBScrollPane scrollPane = new JBScrollPane();
        contain.add(scrollPane,BorderLayout.CENTER);

        tree = new JXTree(new DefaultTreeModel(null));
        scrollPane.setViewportView(tree);

    }

    private void makeBtn(){
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.RIGHT);
        layout.setHgap(10);
        layout.setVgap(10);

        JPanel contain = new JPanel();
        add(contain);
        contain.setLayout(layout);

        ok = new JButton("确定");
        ok.setName("ok");
        ok.setFocusable(true);
        ok.setActionCommand("ok");
        contain.add(ok);

        cancel = new JButton("取消");
        cancel.setName("cancel");
        cancel.setActionCommand("cancel");
        contain.add(cancel);

//        contain.getRootPane().setDefaultButton(ok);

    }

    private void addListeners() {
        radioGroup.addActionListener(this);

//        ok.addActionListener(this);
//        cancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e==null){
            return;
        }
        LOG.info(e.toString());
        switch (e.getActionCommand()){
            case "ops":
                onOpsSelect();
                break;
            case "workbench":
                onWorkSelect();
                break;
        }
    }

    private void onOpsSelect() {
        LOG.info("ops");
        if (curSysId==1) {
            return;
        }
        curSysId=1;

        TreeModel model = MenuReader.read(curSysId);
//        TreeModel model = MenuReader.getDefaultTreeModel();
        tree.setModel(model);

//        tree.setModel(root);

    }
    private void onWorkSelect() {
        LOG.info("work");
        if (curSysId==3) {
            return;
        }
        curSysId=3;

        TreeModel model = MenuReader.read(curSysId);
//        TreeModel model = MenuReader.getDefaultTreeModel2();
        tree.setModel(model);

//        MenuNode root = MenuReader.read(curSysId);
//        tree.setModel(root);
    }

}
