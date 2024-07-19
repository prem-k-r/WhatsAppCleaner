package com.vishnu.whatsappcleaner

import java.io.Serializable

data class ListDirectory(
    val name: String,
    val path: String,
    val icon: Int,
    val hasSent: Boolean = true,
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
                    R.drawable.image
                ),
                ListDirectory(
                    "Videos",
                    "$homePath/Media/WhatsApp Video",
                    R.drawable.video
                ),
                ListDirectory(
                    "Documents",
                    "$homePath/Media/WhatsApp Documents",
                    R.drawable.document
                ),

                ListDirectory(
                    "Audios",
                    "$homePath/Media/WhatsApp Audio",
                    R.drawable.audio
                ),
                ListDirectory(
                    "Statuses",
                    "$homePath/Media/.Statuses",
                    R.drawable.status,
                    false
                ),

                ListDirectory(
                    "Voice Notes",
                    "$homePath/Media/WhatsApp Voice Notes",
                    R.drawable.voice,
                    false
                ),
                ListDirectory(
                    "Video Notes",
                    "$homePath/Media/WhatsApp VIdeo Notes",
                    R.drawable.video_notes,
                    false
                ),

                ListDirectory(
                    "GIFs",
                    "$homePath/Media/WhatsApp Animated Gifs",
                    R.drawable.gif
                ),
                ListDirectory(
                    "Wallpapers",
                    "$homePath/Media/WallPaper",
                    R.drawable.wallpaper,
                    false
                ),
                ListDirectory(
                    "Stickers",
                    "$homePath/Media/WhatsApp Stickers",
                    R.drawable.sticker,
                    false
                ),
                ListDirectory(
                    "Profile Photos",
                    "$homePath/Media/WhatsApp Profile Photos",
                    R.drawable.profile,
                    false
                ),
            )
        }
    }
}
