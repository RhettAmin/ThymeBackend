from typing import Optional

from sqlalchemy.exc import IntegrityError
from fastapi import HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import bindparam, text

# --- GET Calls ---
from api.databaseConnection.queryLoader import load_query
from api.models.recipeModel import IngredientSection, InstructionSection, Recipe, RecipeNotes, RecipeTags, RecipeUpdate
from api.utils.utils import getHashedName

class RecipeService:
    async def get_recipes_list(self, tags: Optional[str], limit: Optional[int], offset: Optional[int], db: AsyncSession):
        try:
            print("getting into SERVICE")
            # Get SQL Parts
            final_sql = load_query("get_recipe_calls.sql", "get_all_recipes_base_1")
            sql_pt2 = load_query("get_recipe_calls.sql", "get_all_recipes_base_2")
            tag_list = [t.strip() for t in tags.split(",")] if tags else None

            print("tag_list: ", tags, tag_list)

            params = {}

            if tag_list:
                final_sql += """
                    AND r.recipe_id IN (
                        SELECT rt2.recipe_id FROM recipe_tags rt2
                        JOIN tags t2 ON rt2.tag_id = t2.id
                        WHERE t2.name IN :tags
                    )
                """
                params["tags"] = tag_list

            final_sql += "\n" + sql_pt2

            if limit:
                final_sql += " LIMIT :limit OFFSET :offset"
                params["limit"] = limit
                params["offset"] = offset

            print("final_query: ", final_sql)
            stmt = text(final_sql)
            if tag_list:
                stmt = stmt.bindparams(bindparam("tags", expanding=True))
            response = await db.execute(stmt, params)
            recipeList = response.mappings().all()
            return recipeList
        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
        
    async def get_recipe_by_id(self, recipe_id: str, db: AsyncSession):
        try:
            sql = load_query("get_recipe_calls.sql", "get_full_recipe_by_id")
            response = await db.execute(text(sql), {"recipe_id": recipe_id})
            return response.mappings().all()[0]
        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
        
    async def get_ingredient(self, name: Optional[str], db: AsyncSession):
        try:
            sql = load_query("get_recipe_calls.sql", "get_ingredient")
            print("SQL: ", sql)
            if name:
                sql += f" WHERE name = '{name}'"
            response = await db.execute(text(sql), {"i_name": name})
            print("RESPONSE: ", response)
            ingredients = response.mappings().all()

            return ingredients if len(ingredients) > 1 else ingredients[0]
        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
        
    async def create_recipe(self, body: Recipe, db: AsyncSession):
        try:
            # Generate recipe_id
            recipe_id = getHashedName(body.name)
            print("Recipe_id: ", recipe_id)
            tags = body.tags if body.tags else []
            notes = body.notes if body.notes else []
            ingredientSections = body.ingredient_sections if body.ingredient_sections else []
            instructionSections = body.instruction_sections if body.instruction_sections else []

            await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe")), {
                "recipe_id":       recipe_id,
                "name":            body.name,
                "description":     body.description,
                "hero_image_link": body.hero_image_link,
                "main_image_link": body.main_image_link,
                "created_date":    body.created_date,
                "updated_date":    body.updated_date,
                "time_to_plate":   body.time_to_plate,
                "total_servings":  body.total_servings,
                "serving_size":    body.serving_size,
                "serving_form":    body.serving_form,
                "calories": body.calories,
                "fat": body.fat,
                "saturated_fat": body.saturated_fat,
                "trans_fat": body.trans_fat,
                "carbohydrate": body.carbohydrate,
                "fibre": body.fibre,
                "sugars": body.sugars,
                "protein": body.protein,
                "cholesterol": body.cholesterol,
                "sodium": body.sodium,
                "vitamin_d": body.vitamin_d,
                "iron": body.iron,
                "potassium": body.potassium,
                "calcium": body.calcium
            })

            # ── Tags ───────────
            for tag_name in tags:
                result = await db.execute(text(load_query("add_recipe_calls.sql", "insert_tag")), {"name": tag_name})
                tag_id = result.scalar_one()
                await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe_tag")), {
                    "recipe_id": recipe_id,
                    "tag_id":    tag_id
                })

            # ── Recipe Notes ───────────
            for note in notes:
                await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe_notes")), {
                    "recipe_id": recipe_id,
                    "content": note.content,
                    "display_name": note.display_name,
                    "placement": note.placement
                })

            # ── Insert ingredient sections + ingredients ───────────
            await insert_ingredientsections(recipe_id, ingredientSections, db)

            # ── Insert instruction sections + steps ────────────────

            await insert_instructionsections(recipe_id, instructionSections, db)

            await db.commit()

            return {
                "status_code": 200,
                "data": f"recipe {recipe_id} created successfully"
            }
        except IntegrityError:
            return {
                "status_code": 409,
                "data": f"recipe already exists"
            }
        except Exception:
            return {
                "status_code": 500,
                "data": f"Internal Error - see logs"
            }
    
    async def update_recipe_base(self, body: RecipeUpdate, db: AsyncSession):
        result = await db.execute(text(load_query("update_recipe_calls.sql", "update_recipe")), 
            {
                "name": body.name,
                "description": body.description,
                "hero_image_link": body.hero_image_link,
                "main_image_link": body.main_image_link,
                "updated_date": body.updated_date,
                "time_to_plate": body.time_to_plate,
                "total_servings": body.total_servings,
                "serving_size": body.serving_size,
                "serving_form": body.serving_form,
                "calories": body.calories,
                "fat": body.fat,
                "saturated_fat": body.saturated_fat,
                "trans_fat": body.trans_fat,
                "carbohydrate": body.carbohydrate,
                "fibre": body.fibre,
                "sugars": body.sugars,
                "protein": body.protein,
                "cholesterol": body.cholesterol,
                "sodium": body.sodium,
                "vitamin_d": body.vitamin_d,
                "iron": body.iron,
                "potassium": body.potassium,
                "calcium": body.calcium,
                "recipe_id": body.recipe_id
            }
        )

        await db.commit()
        print("RESULT: ", result.one_or_none())
        return result

    async def update_recipe_tags(self, tags: RecipeTags, db: AsyncSession): 

        # Delete all recipe_tags
        await db.execute(text(load_query("delete_recipe_calls.sql", "delete_recipe_tags")), {"recipe_id": tags.recipe_id})

        # Upload tags, on conflict return tag id for recipe tag upload
        for tag_name in tags.tags:
            # Capitalize first letter of tag_name
            tag_name = tag_name.capitalize()
            result = await db.execute(text(load_query("add_recipe_calls.sql", "insert_tag")), {"name": tag_name})
            tag_id = result.scalar_one()
            print("TAG: ", tag_id)
            # Upload new recipe_tags
            await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe_tag")), {
                "recipe_id": tags.recipe_id,
                "tag_id":    tag_id
            })
        
        await db.commit()

        return {
            "data": f"tags for recipe: {tags.recipe_id} updated successfully"
        }

    async def update_recipe_notes(self, recipe_notes: RecipeNotes, db: AsyncSession): 

        # Delete all recipe_notes
        await db.execute(text(load_query("delete_recipe_calls.sql", "delete_recipe_notes")), {"recipe_id": recipe_notes.recipe_id})

        # Upload tags, on conflict return tag id for recipe tag upload
        for note in recipe_notes.notes:

            await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe_notes")), 
                {"recipe_id": recipe_notes.recipe_id, "content": note.content, "display_name": note.display_name, "placement": note.placement})
        
        await db.commit()

        return {
            "data": f"notes for recipe: {recipe_notes.recipe_id} updated successfully"
        }
        
    async def update_ingredient_sections(self, recipe_id: str, ingredient_sections: list[IngredientSection], db: AsyncSession):
        # Delete values from the DB
        await db.execute(
            text(load_query("delete_recipe_calls.sql", "delete_ingredient_sections")),
            {"recipe_id": recipe_id}
        )

        await insert_ingredientsections(recipe_id, ingredient_sections, db)
        await db.commit()
        return {
            "data": f"ingredient sections for recipe: {recipe_id} updated successfully"
        }
    
    async def update_instruction_sections(self, recipe_id: str, instruction_sections: list[InstructionSection], db: AsyncSession):
        # Delete values from the DB
        await db.execute(
            text(load_query("delete_recipe_calls.sql", "delete_instruction_sections")),
            {"recipe_id": recipe_id}
        )

        await insert_instructionsections(recipe_id, instruction_sections, db)
        await db.commit()
        return {
            "data": f"instruction sections for recipe: {recipe_id} updated successfully"
        }

    async def delete_recipe(self, recipe_id: str, db: AsyncSession):
        await db.execute(text(load_query("delete_recipe_calls.sql", "delete_recipe")), {"recipe_id": recipe_id})
        await db.commit()
        return True


#  Shared function(s) ==================================================================

async def insert_ingredientsections(recipe_id: str, ingredient_sections: list[IngredientSection], db: AsyncSession):
    for index, section in enumerate(ingredient_sections):
        result = await db.execute(text(load_query("add_recipe_calls.sql", "insert_ingredient_section")), {
            "recipe_id":    recipe_id,
            "section_name": section.section_name,
            "sort_order":   index+1
        })
        section_id = result.scalar_one()
        
        for i_index, ingredient in enumerate(section.ingredients):
            print("INCOM ING: ", ingredient)
            # Insert ingredient first, as we'll use it's id for the recipe_ingredient insertion
            ing_id = await db.execute(text(load_query("add_recipe_calls.sql", "insert_ingredient")), {
                "i_name": ingredient.name,
                "fdc_id": ingredient.fdc_id,
                "calories": ingredient.calories,
                "fat": ingredient.fat,
                "saturated_fat": ingredient.saturated_fat,
                "trans_fat": ingredient.trans_fat,
                "carbohydrate": ingredient.carbohydrate,
                "fibre": ingredient.fibre,
                "sugars": ingredient.sugars,
                "protein": ingredient.protein,
                "cholesterol": ingredient.cholesterol,
                "sodium": ingredient.sodium,
                "vitamin_d": ingredient.vitamin_d,
                "iron": ingredient.iron,
                "potassium": ingredient.potassium,
                "calcium": ingredient.calcium,
            })
            ing_id = ing_id.scalar_one()
            print("conversion_type: ", ingredient.name, ingredient.conversion_type)
            await db.execute(text(load_query("add_recipe_calls.sql", "insert_recipe_ingredient")), {
                "section_id":    section_id,
                "ingredient_id": ing_id,
                "quantity":      ingredient.quantity,
                "measurement":   ingredient.measurement,
                "conversion_type": ingredient.conversion_type,
                "sort_order":    i_index+1,
                "gram_weight":   ingredient.gram_weight
            })

async def insert_instructionsections(recipe_id: str, instruction_sections: list[InstructionSection], db: AsyncSession):
     for index, section in enumerate(instruction_sections):
        result = await db.execute(text(load_query("add_recipe_calls.sql", "insert_instruction_section")), {
            "recipe_id":    recipe_id,
            "section_name": section.section_name,
            "alt_text":     section.alt_text,
            "image_link":   section.image_link,
            "sort_order":   index+1
        })
        section_id = result.scalar_one()

        for index, step in enumerate(section.steps):
            await db.execute(text(load_query("add_recipe_calls.sql", "insert_instruction_step")), {
                "section_id": section_id,
                "step_text":  step.step_text,
                "sort_order": index+1
            })


recipeService = RecipeService()