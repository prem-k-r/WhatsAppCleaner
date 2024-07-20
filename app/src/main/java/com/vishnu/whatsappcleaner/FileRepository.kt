package com.vishnu.whatsappcleaner

import android.content.Context
import android.text.format.Formatter.formatFileSize
import android.util.Log
import java.io.File

class FileRepository {
    companion object {

        @JvmStatic
        public suspend fun getDirectoryList(
            context: Context,
            homePath: String
        ): Pair<String, List<ListDirectory>> {
            Log.i(
                "vishnu",
                "FileRepository#getDirectoryList: $homePath"
            )

            var totalSize = 0L

            val directoryList = ListDirectory.getDirectoryList(homePath)

            directoryList.forEach { directoryItem ->

                val size = getSize(directoryItem.path)

                directoryItem.size = formatFileSize(context, size)

                totalSize += size
            }

            return Pair(
                formatFileSize(context, totalSize),
                directoryList
            )
        }

        @JvmStatic
        public suspend fun getFileList(path: String): ArrayList<String> {
            Log.e("vishnu", "FileRepository#getFileList: $path")

            val list = ArrayList<String>()

            // flattening...
            if (path.contains("Media/WhatsApp Voice Notes") or path.contains("Media/WhatsApp Video Notes"))
                File(path)
                    .walkTopDown()
                    .forEach { f ->
                        if (!f.isDirectory && f.name != ".nomedia")
                            list.add(f.path)
                    }
            else
                File(path).listFiles { dir, name ->

                    val f = File("$dir/$name")

                    if (!f.isDirectory && f.name != ".nomedia")
                        list.add(f.path)

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

                if (f.isDirectory)
                    list.add(f.path)

                true
            }

            return list
        }

        @JvmStatic
        public fun getLoadingList(): ArrayList<String> {
            val loadingList = ArrayList<String>()

            for (i in 0 until 10)
                loadingList.add(Constants._LOADING)

            return loadingList
        }

        private suspend fun getSize(path: String): Long {
            Log.i("vishnu", "getSize() called with: path = $path")
            return File(path)
                .walkTopDown()
                .map { it.length() }
                .sum()
        }

    }
}