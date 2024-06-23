package icu.xwj.ojhelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class CopyOJCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }

        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        PsiJavaFileImpl javaFile = (PsiJavaFileImpl) psiFile;
        PsiClass[] classes = javaFile.getClasses();
        if (classes.length == 0) {
            return;
        }

        PsiClass mainClass = classes[0];
        String oldClassName = mainClass.getName();
        if (oldClassName == null) {
            return;
        }

        String fileText = psiFile.getText();
        String newText = fileText.replaceFirst("package\\s+.*?;", "");
        newText = newText.replaceAll("\\b" + oldClassName + "\\b", "Main");

        String finalNewText = newText;

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(finalNewText);
        clipboard.setContents(stringSelection, null);
    }
}
