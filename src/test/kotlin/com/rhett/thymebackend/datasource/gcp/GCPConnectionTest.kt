package com.rhett.thymebackend.datasource.gcp

import com.rhett.thymebackend.recipe.RecipeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class GCPConnectionTest(
    @Autowired val gcpConn: GCPConnection
) {

    @Nested
    @DisplayName("Test GCP Connection")
    inner class GetRecipes {

        @Test
        fun `Will call the GCP function to get the storage object` () {
            // When/Then
            gcpConn.checkConnection()
        }
    }


}