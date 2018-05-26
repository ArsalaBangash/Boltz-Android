package com.arsalabangash.boltz.practice.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioButton
import android.widget.TableLayout
import android.widget.TableRow


class MultipleChoiceGridView(context: Context, attrs: AttributeSet) : TableLayout(context, attrs), View.OnClickListener {
    private var activeRadioButton: RadioButton? = null

    val checkedRadioButtonId: Int
        get() = if (activeRadioButton != null) {
            activeRadioButton!!.id
        } else -1

    override fun onClick(v: View) {
        val rb = v as RadioButton
        if (activeRadioButton != null) {
            activeRadioButton!!.isChecked = false
        }
        rb.isChecked = true
        activeRadioButton = rb
    }

    override fun addView(child: View, index: Int, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as TableRow)
    }


    override fun addView(child: View, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, params)
        setChildrenOnClickListener(child as TableRow)
    }


    private fun setChildrenOnClickListener(tr: TableRow) {
        val c = tr.childCount
        (0 until c)
                .map { tr.getChildAt(it) }
                .forEach { (it as RadioButton).setOnClickListener(this) }
    }
}