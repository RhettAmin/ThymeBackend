package com.rhett.thymebackend.controllers

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
internal class RecipeControllerTest {
//     val mockMVC: MockMvc,
//     val objectMapper: ObjectMapper
//) {
//
//    val baseUrl = "/api/recipes"
//
//    @Nested
//    @DisplayName("GET /api/recipes")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class GetRecipes {
//        @Test
//        fun `Should return all recipes` () {
//            // When/Then
//            mockMVC.get(baseUrl)
//                .andDo { print() }
//                .andExpect {
//                    status { isOk() }
//                    jsonPath("$[0].name") {
//                        value("recipe1")
//                    }
//                }
//        }
//    }
//
//    @Nested
//    @DisplayName("GET /api/recipes/{id}")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class GetRecipe {
//        @Test
//        fun `Should return the recipe with the given id number`() {
//            // Given
//            val id = 2
//
//            // When
//            mockMVC.get("$baseUrl/$id")
//                .andDo { print() }
//                .andExpect { status { isOk() } }
//                .andExpect {
//                    content { contentType(MediaType.APPLICATION_JSON) }
//                    jsonPath("$.name") { value("recipe${id}") }
//                    jsonPath("$.notes") { isNotEmpty() }
//                }
//        }
//
//        @Test
//        fun `Should return Not Found when id does not exist` () {
//            // Given
//            val id = 5
//
//            // When
//            mockMVC.get("$baseUrl/$id")
//                .andDo { print() }
//                .andExpect { status { isNotFound() } }
//        }
//    }
//
//    @Nested
//    @DisplayName("POST /api/banks")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class PostNewRecipe {
//
//        @Test
//        fun `Should add new recipe` () {
//            // Given
//            val newRecipe = Recipe(ObjectId.get(), "testRecipe", "Fruity with oaky finnish")
//
//            // When
//            val response = mockMVC.post(baseUrl) {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(newRecipe)
//            }
//
//            response
//                .andDo { print() }
//                .andExpect {
//                    status { isCreated() }
//                    content {
//                        contentType(MediaType.APPLICATION_JSON)
//                        json(objectMapper.writeValueAsString(newRecipe))
//                    }
//                }
//            mockMVC.get("$baseUrl/${newRecipe.id}")
//                .andExpect { content { json(objectMapper.writeValueAsString(newRecipe)) } }
//        }
//
//        @Test
//        fun `Should return BAD REQUEST if recipe is given with the same name` () {
//            // Given
//            val invalidRecipe = Recipe(ObjectId.get(), "recipe1", "copyCat")
//
//            // When
//            val postResponse = mockMVC.post(baseUrl) {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(invalidRecipe)
//            }
//
//            // Then
//            postResponse
//                .andDo { print() }
//                .andExpect { status { isBadRequest() } }
//
//        }
//
//    }
//
//    @Nested
//    @DisplayName("PATCH /api/recipes")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class PatchingExistingRecipes {
//
//        @Test
//        fun `Should update an existing recipe` () {
//            // Given
//            val updatedRecipe = Recipe(ObjectId.get(), "recipe1", "updated recipe1 to this!")
//
//            // When
//            val patchResponse = mockMVC.patch(baseUrl) {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(updatedRecipe)
//            }
//
//            // Then
//            patchResponse
//                .andDo { print() }
//                .andExpect {
//                    status { isOk() }
//                    content {
//                        contentType(MediaType.APPLICATION_JSON)
//                        json(objectMapper.writeValueAsString(updatedRecipe))
//                    }
//                }
//            mockMVC.get("$baseUrl/${updatedRecipe.id}")
//                .andExpect { content { json(objectMapper.writeValueAsString(updatedRecipe)) } }
//        }
//
//        @Test
//        fun `Should return a BAD_REQUEST if no recipe with the ID exists` () {
//            // Given
//            val recipeDoesNotExist = Recipe(ObjectId.get(), name = "New Recipe name", notes = "updating a ghost recipe, ooooohhhh")
//
//            // When
//            val patchResponse = mockMVC.patch(baseUrl) {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(recipeDoesNotExist)
//            }
//
//            // Then
//            patchResponse
//                .andDo { print() }
//                .andExpect { status { isNotFound() } }
//
//        }
//
//    }
//
//    @Nested
//    @DisplayName("DELETE /api/recipes")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class DeleteExistingRecipe {
//
//        @Test
//        fun `Should delete an existing recipe` () {
//            // Given
//            val recipeIdThatExists = 2
//
//            // When
//            mockMVC.delete("$baseUrl/$recipeIdThatExists")
//                .andDo { print() }
//                .andExpect {
//                    status { isNoContent() }
//                }
//
//            // Then
//            mockMVC.get("$baseUrl/$recipeIdThatExists")
//                .andExpect { status { isNotFound() } }
//
//        }
//
//    }
//
}