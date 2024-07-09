package com.vishnu.whatsappcleaner

import java.io.Serializable

data class ListDirectory(
    val name: String,
    val path: String,
    val icon: Int,
    var size: String = "0 B"
) :
    Serializable {
    companion object {
        private const val serialVersionUID: Long = -5435756175248173106L

        fun getDirectoryList(homePath: String): List<ListDirectory> {
            return listOf(
                ListDirectory(
                    "Images",
                    "$homePath/Media/WhatsApp Images",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Videos",
                    "$homePath/Media/WhatsApp Video",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/Media/WhatsApp Documents",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Statuses",
                    "$homePath/Media/.Statuses",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Audios",
                    "$homePath/Media/WhatsApp Audio",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Wallpapers",
                    "$homePath/Media/WhatsApp WallPaper",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "GIFs",
                    "$homePath/Media/WhatsApp Animated Gifs",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Stickers",
                    "$homePath/Media/WhatsApp Stickers",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Profile Photos",
                    "$homePath/Media/WhatsApp Profile Photos",
                    R.drawable.ic_launcher_foreground
                ),
                ListDirectory(
                    "Voice Notes",
                    "$homePath/Media/WhatsApp Voice Notes",
                    R.drawable.ic_launcher_foreground
                ),
            )
        }
    }
}
