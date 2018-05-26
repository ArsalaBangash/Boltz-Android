package com.arsalabangash.boltz.practice.ui.views

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.EditText


/**
 * A custom view for entering answers for a challenge.
 */
class ChallengeTextInput(context: Context, attrs: AttributeSet) : EditText(context, attrs) {


    /**
     * We suppress the super class's behaviour of showing the keyboard on focusing since our input
     * originates from our custom views, but we still want the cursor to appear upon initialization
     */
    init {
        showSoftInputOnFocus = false
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        this.performClick()
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
    }


    /**
     * Delete's a block of text within the cursor's selection or calls [backspace] if only a
     * single character is to be deleted.
     */
    fun deleteText() {
        val currentText = text.toString()
        val preCursorText = currentText.substring(0, this.selectionStart)
        val postCursorText = currentText.substring(this.selectionEnd, currentText.length)
        if (this.selectionStart == this.selectionEnd) {
            return backspace(preCursorText, postCursorText)
        }
        val cursor = this.selectionStart
        //newText does not contain anything within the cursor's selection
        val newText = StringBuilder(preCursorText)
        newText.append(postCursorText)
        this.setText(newText.toString())
        this.setSelection(cursor)
    }

    /**
     * If the call to [deleteText] was made with no block of text selected, a simple backspace
     * is performed with the character before the cursor erased and the cursor moved one space back
     */
    private fun backspace(preCursorText: String, postCursorText: String) {
        if (preCursorText.isEmpty()) return
        var cursor = this.selectionStart
        val newText = StringBuilder(preCursorText.substring(0, preCursorText.length - 1))
        cursor--
        newText.append(postCursorText)
        this.setText(newText.toString())
        this.setSelection(cursor)
    }

    /**
     * Inserts [txt] after the cursor
     */
    fun insertText(txt: String) {
        val currentText = text.toString()
        val preCursor = currentText.substring(0, this.selectionStart)
        val postCursor = currentText.substring(this.selectionEnd, currentText.length)
        val newText = StringBuilder(preCursor)
        newText.append(txt)
        newText.append(postCursor)
        this.setText(newText.toString())
        this.setSelection(selectionStart + preCursor.length + txt.length)
    }

}