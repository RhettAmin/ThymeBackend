from api.models.recipeModel import IngredientSectionRouter, InstructionSectionRouter, Recipe, RecipeNotes, RecipeTags, RecipeUpdate
from fastapi import APIRouter, Depends, HTTPException
from api.service.recipeService import RecipeService
from sqlalchemy.ext.asyncio import AsyncSession
from api.utils.utils import getHashedName
from api.dependencies import get_db
from typing import Optional

router = APIRouter()
recipeService = RecipeService()




# --- GET Calls ---
@router.get("/")
async def get_recipes_list_route(tags: Optional[str] = None, limit: Optional[int] = None, offset: Optional[int] = None, db: AsyncSession = Depends(get_db)):
    try:
        print("Call Initiatied, ", tags, limit, offset )
        recipeList = await recipeService.get_recipes_list(tags, limit, offset, db)
        return {
            "recipes": recipeList
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.get("/{recipe_id}")
async def get_recipe_by_id(recipe_id: str, db: AsyncSession = Depends(get_db)):
    try:
        recipe = await recipeService.get_recipe_by_id(recipe_id, db)
        return {
            "data": recipe
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@router.get("/name/{recipe_name}")
async def get_recipe_by_name(recipe_name: str, db: AsyncSession = Depends(get_db)):
    try:
        hashedId = getHashedName(recipe_name)
        recipe = await recipeService.get_recipe_by_id(hashedId, db)
        return {
            "data": recipe
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# --- POST Calls ---
@router.post("/", status_code=201)
async def create_recipe(body: Recipe, db: AsyncSession = Depends(get_db)):
    try:
        return await recipeService.create_recipe(body, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))
  
# --- PUT Calls ---
@router.put("/base", status_code=201)
async def update_recipe(body: RecipeUpdate, db: AsyncSession = Depends(get_db)):
    try:
        print("RECEIVED: ", body)
        return await recipeService.update_recipe_base(body, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))

@router.put("/tags", status_code=201)
async def update_recipe_tags(tags: RecipeTags, db: AsyncSession = Depends(get_db)):
    try:
        return await recipeService.update_recipe_tags(tags, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))
    
@router.put("/notes", status_code=201)
async def update_recipe_notes(recipe_notes: RecipeNotes, db: AsyncSession = Depends(get_db)):
    try:
        return await recipeService.update_recipe_notes(recipe_notes, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))
    
@router.put("/ingredient_sections", status_code=201)
async def update_recipe_ingredientSections(ingredientSectionRouter: IngredientSectionRouter, db: AsyncSession = Depends(get_db)):
    try:
        print("RECEIVED: ", ingredientSectionRouter)
        return await recipeService.update_ingredient_sections(ingredientSectionRouter.recipe_id, ingredientSectionRouter.ingredient_sections, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/instruction_sections", status_code=201)
async def update_recipe_instructionSections(instructionSectionRouter: InstructionSectionRouter, db: AsyncSession = Depends(get_db)):
    try:
        return await recipeService.update_instruction_sections(instructionSectionRouter.recipe_id, instructionSectionRouter.instruction_sections, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))

# --- PUT Calls --- 
@router.delete("/{recipe_id}", status_code=204)
async def delete_recipe(recipe_id: str, db: AsyncSession = Depends(get_db)):
    try:
        return await recipeService.delete_recipe(recipe_id, db)

    except Exception as e:
        await db.rollback()
        raise HTTPException(status_code=500, detail=str(e))

