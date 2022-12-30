package com.call.screen.themes.usecases

import android.graphics.drawable.Drawable
import com.call.screen.themes.R

object CheckboxUseCase {
    fun getCheckBox(isDownloaded:Boolean):Int{
        return if (isDownloaded){
            R.drawable.checked
        } else{
            R.drawable.unchecked
        }
    }
}