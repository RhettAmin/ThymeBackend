package com.rhett.thymebackend.datasource.gcp

import org.springframework.core.io.ByteArrayResource
import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun listOfFiles(): String

}