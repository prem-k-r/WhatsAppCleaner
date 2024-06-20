package com.vishnu.whatsappcleaner

data class ListDirectory(val name: String, val path: String, val icon: Int) {
    companion object {
        fun getDirectoryList(homePath: String): List<ListDirectory> {
            return listOf(
                ListDirectory(
                    "Images",
                    "$homePath/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Video",
                    "$homePath/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Images",
                    "$homePath/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Video",
                    "$homePath/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Images",
                    "$homePath/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Video",
                    "$homePath/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Images",
                    "$homePath/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Video",
                    "$homePath/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Images",
                    "$homePath/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Video",
                    "$homePath/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
            )
        }
    }
}
