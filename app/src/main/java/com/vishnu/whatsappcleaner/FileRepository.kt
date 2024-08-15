package com.vishnu.whatsappcleaner

import android.content.Context
import android.text.format.Formatter.formatFileSize
import android.util.Log
import java.io.File

class FileRepository {
    companion object {

        @JvmStatic
        public suspend fun getDirectoryList(
            context: Context, homePath: String
        ): Pair<String, List<ListDirectory>> {
            Log.i(
                "vishnu", "FileRepository#getDirectoryList: $homePath"
            )

            var totalSize = 0L

            val directoryList = ListDirectory.getDirectoryList(homePath)

            directoryList.forEach { directoryItem ->

                val size = getSize(directoryItem.path)

                directoryItem.size = formatFileSize(context, size)

                totalSize += size
            }

            return Pair(
                formatFileSize(context, totalSize), directoryList
            )
        }

        @JvmStatic
        public suspend fun getFileList(context: Context, path: String): ArrayList<ListFile> {
            Log.e("vishnu", "FileRepository#getFileList: $path")

            val list = ArrayList<ListFile>()

            // flattening...
            if (path.contains("Media/WhatsApp Voice Notes") or path.contains("Media/WhatsApp Video Notes")) File(
                path
            ).walkTopDown().forEach { f ->
                if (!f.isDirectory && f.name != ".nomedia") list.add(
                    ListFile(
                        f.path, formatFileSize(context, getSize(f.path))
                    )
                )
            }
            else File(path).listFiles { dir, name ->

                val f = File("$dir/$name")

                if (!f.isDirectory && f.name != ".nomedia") list.add(
                    ListFile(
                        f.path, formatFileSize(context, getSize(f.path))
                    )
                )

                true
            }

            return list
        }

        @JvmStatic
        public suspend fun getDirectoryList(path: String): ArrayList<String> {
            Log.e("vishnu", "FileRepository#getDirectoryList: $path")

            val list = ArrayList<String>()

            File(path).listFiles { dir, name ->

                val f = File("$dir/$name")

                if (f.isDirectory) list.add(f.path)

                true
            }

            return list
        }

        @JvmStatic
        public fun getLoadingList(): ArrayList<ListFile> {
            val loadingList = ArrayList<ListFile>()

            for (i in 0 until 10) loadingList.add(
                ListFile(
                    Constants._LOADING, "0 B"
                )
            )

            return loadingList
        }

        private fun getSize(path: String): Long {
//            Log.i("vishnu", "getSize() called with: path = $path")
            return File(path).walkTopDown().map { it.length() }.sum()
        }

    }
}