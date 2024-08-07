package com.vishnu.whatsappcleaner

import java.io.File

data class ListFile(
    val filePath: String,
    var size: String = "0 B",
    var isSelected: Boolean = false,
) : File(filePath) {
    companion object {
        private const val serialVersionUID: Long = 8425722975465458623L
    }
}
