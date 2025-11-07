package org.example

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder

/**
 * Действие плагина для перевода текста через Google Translate.
 *
 * Логика работы:
 * 1. Если выделенного текста нет или отсутствует браузер:
 *      - Показывается окно с сообщением об ошибке
 * 2. Если в редакторе выделен текст:
 *      - Текст кодируется в URL
 *      - Открывается браузер с Google Translate и этим текстом
 *
 * Горячие клавиши: Ctrl+\ + Ctrl+T
 * Контекстное меню: ПКМ → "Перевести Выделенный Текст"
 */

class TranslateViaGoogleTranslateAction : AnAction("Перевести Выделенный Текст") {

    /**
     * Класс действия плагина для перевода текста через Google Translate
     * Использует выделенный пользователем текст
     */

    override fun actionPerformed(e: AnActionEvent) {

        // Реализация: проверка выделения, кодирование текста, открытие браузера или окна ошибки

        val project: Project = e.project ?: return
        val editor: Editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val selectedText = editor.selectionModel.selectedText

        if (selectedText.isNullOrBlank()) {
            Messages.showInfoMessage("Выделите текст для перевода!", "Google Translate")
            return
        }

        try {
            val encoded = URLEncoder.encode(selectedText, "UTF-8")
            val url = "https://translate.google.com/?sl=auto&tl=ru&text=$encoded"
            Desktop.getDesktop().browse(URI(url))
        } catch (ex: Exception) {
            Messages.showErrorDialog("Ошибка при открытии браузера", "Ошибка")
        }
    }
}
