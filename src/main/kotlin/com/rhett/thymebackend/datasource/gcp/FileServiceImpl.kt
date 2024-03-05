package com.rhett.thymebackend.datasource.gcp

import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.blobstore.BlobstoreService
import com.google.appengine.api.blobstore.BlobstoreServiceFactory
import com.google.appengine.api.images.ImagesService
import com.google.appengine.api.images.ImagesServiceFactory
import com.google.appengine.api.images.ServingUrlOptions
import com.google.appengine.tools.cloudstorage.GcsFilename
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class FileServiceImpl : FileService {

    @Value("thyme_image_bucket")
    private lateinit var bucketName: String

    override fun listOfFiles(): String {

        return "test"
    }

//    override fun listOfFiles(): String {
//        var servingUrl = ""
//        try {
//            val storage: Storage = StorageOptions.getDefaultInstance().service
//            println(storage.list("thyme_image_bucket").values)
//
//            val gcsFilename = GcsFilename("thyme_image_bucket", "apple_cake.jpg")
//            println(gcsFilename)
//
//            val bucket = storage.get("thyme_image_bucket")
//            val imageObj = bucket.get("apple_cake.jpg")
//            var image = imageObj.asBlobInfo()
//
//
//            val imageService: ImagesService = ImagesServiceFactory.getImagesService()
//
//            val blobstore: BlobstoreService = BlobstoreServiceFactory.getBlobstoreService()
//            val blobKey: BlobKey = blobstore.createGsBlobKey("/gs/thyme_image_bucket/apple_cake.jpg")
//            val options = ServingUrlOptions.Builder
//                .withBlobKey(blobKey).
//                secureUrl(true)
//
//            servingUrl = imageService.getServingUrl(options)
//        } catch (e: Exception) {
//            servingUrl = e.toString()
//        }
//
//        return servingUrl
//    }

}