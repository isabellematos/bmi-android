package br.senai.sp.jandira.bmicompose.utils

import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import br.senai.sp.jandira.bmicompose.R
import org.w3c.dom.Text

fun getText(bmi: Double): Text {

    return if (bmi <= 18.5) {
        R.string.bmi_status_under)
    } else if (bmi > 18.5 && bmi < 25){
        R.string.bmi_status_ideal)
    }else {
        R.string.bmi_status_more)
    }
}

