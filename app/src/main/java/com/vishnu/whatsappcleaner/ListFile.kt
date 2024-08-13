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

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ListFile

        if (filePath != other.filePath) return false
        if (size != other.size) return false
        if (isSelected != other.isSelected) return false

        return true
    }
}
