package com.rhett.thymebackend.datasource.gcp

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class FileServiceImplTest (
    @Autowired var fileService: FileServiceImpl
){

    @Test
    fun testFileService() {
        var list = fileService.listOfFiles()
        println(list)
    }

}