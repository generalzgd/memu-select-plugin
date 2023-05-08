package cn.yongyetech.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MenuSelectPlugin extends AnAction implements ActionListener {
    private static final Logger LOG = Logger.getLogger(MenuSelectPlugin.class.getName());

    private JBPopup pop;
    PopupPanel panel;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

        if (editor==null || project==null){
            return;
        }
//        SelectionModel selectionModel = editor.getSelectionModel();

        CaretModel caretModel=editor.getCaretModel();
        Document document = editor.getDocument();

        panel = new PopupPanel();
        panel.setCurProj(project);
        panel.setCurDoc(document);
        panel.setOffset(caretModel.getOffset());
        panel.ok.addActionListener(this);
        panel.cancel.addActionListener(this);

        JBPopupFactory instance = JBPopupFactory.getInstance();
        pop = instance.createComponentPopupBuilder(panel, new JBLabel())//参数说明：内容对象,优先获取
                .setTitle("客服菜单选择")
                .setMovable(true)
                .setResizable(true)
                .setNormalWindowLevel(false)
                .setMinSize(new Dimension(600,300))
                .createPopup();
        pop.showInFocusCenter();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e==null){
            return;
        }
        LOG.info(e.toString());
        switch (e.getActionCommand()){
            case "ok":
                onOkBtnClick();
                break;
            case "cancel":
                onCancelBtnClick();
                break;
        }
    }

    private void onOkBtnClick() {
        LOG.info("ok");

        try {
            String[] path = panel.getTreeSelected();
            for(int i=0;i<path.length;i++){
                path[i] = "\"" + path[i] + "\"";
            }
            String str = String.join(",", path);

            int sysId = panel.getCurSysId();

            int belong = panel.getBelong();

            if(path.length>0){

                String text = String.format("{system_id:%d, belong:%d, menus:[%s]};", sysId, belong, str);

                Runnable runnable = ()-> panel.getCurDoc().insertString(panel.getOffset(), text);
                WriteCommandAction.runWriteCommandAction(panel.getCurProj(), runnable);
            }
        } catch (Exception e) {
        }


        pop.cancel();
    }
    private void onCancelBtnClick() {
        LOG.info("cancel");
        pop.cancel();
    }
}
