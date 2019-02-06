package com.simecsystem.currencyconverter.internal

import android.view.View
import android.widget.TableRow
import androidx.constraintlayout.widget.Group

fun Group.setAllClickListener(listener: View.OnClickListener?){
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}
