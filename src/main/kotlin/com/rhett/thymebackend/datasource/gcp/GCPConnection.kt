package com.rhett.thymebackend.datasource.gcp

import com.google.cloud.storage.*
import org.springframework.stereotype.Component


@Component
class GCPConnection {

    fun checkConnection() {
        val storage: Storage = StorageOptions.getDefaultInstance().service

        val blobId = BlobId.of("thyme_image_bucket", "apple_cake.jpg")
        val blob: Blob = storage.get(blobId)
        println(storage)
        println(blob)
    }

}