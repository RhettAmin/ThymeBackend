from enum import Enum

from pydantic import BaseModel
from datetime import date
from typing import Any, Optional


# ── Ingredients ──────────────────────────────────────────
class ConversionType(str, Enum):
    WEIGHT = "WEIGHT"
    VOLUME = "VOLUME"
    OTHER = "OTHER"

class Ingredient(BaseModel):
    id: Optional[int] = None
    fdc_id: Optional[int] = None
    name: str
    quantity: float
    measurement: str
    gram_weight: float
    conversion_type: ConversionType
    calories: float 
    fat: float 
    saturated_fat: float 
    trans_fat: float 
    carbohydrate: float 
    fibre: float 
    sugars: float 
    protein: float 
    cholesterol: float 
    sodium: float 
    vitamin_d: float 
    iron: float 
    potassium: float 
    calcium: float 

class IngredientSection(BaseModel):
    section_id: int
    section_name: Optional[str] = ""
    sort_order: int
    ingredients: list[Ingredient] = []

class IngredientSectionRouter(BaseModel):
    recipe_id: str
    ingredient_sections: list[IngredientSection]

# ── Tags  ────────────────────────────────────────────────
class RecipeTags(BaseModel):
    recipe_id: str
    tags: list[str]

# ── Notes ────────────────────────────────────────────────
class Notes(BaseModel):
    id: Optional[int] = -1
    content: str
    display_name: str
    placement: str

class RecipeNotes(BaseModel):
    recipe_id: str
    notes: list[Notes]

# ── Instructions ─────────────────────────────────────────

class InstructionStep(BaseModel):
    step_text: str

class InstructionSection(BaseModel):
    section_name: Optional[str] = "" 
    alt_text: Optional[str] = "" 
    image_link: Optional[str] = ""  
    steps: list[InstructionStep] = []

class InstructionSectionRouter(BaseModel):
    recipe_id: str
    instruction_sections: list[InstructionSection]

# ── Recipe ────────────────────────────────────────────────

class Recipe(BaseModel):
    id: Optional[int] = 0
    recipe_id: Optional[str] = ""
    name: str
    description: str 
    hero_image_link: str 
    main_image_link: str 
    created_date: Optional[date] = None
    updated_date: Optional[date] = None 
    time_to_plate: int 
    total_servings: int 
    serving_size: int 
    serving_form: str 
    calories: float 
    fat: float 
    saturated_fat: float 
    trans_fat: float 
    carbohydrate: float 
    fibre: float 
    sugars: float 
    protein: float 
    cholesterol: float 
    sodium: float 
    vitamin_d: float 
    iron: float 
    potassium: float 
    calcium: float 
    tags: list[str] = []
    notes: list[Notes] = []
    ingredient_sections: list[IngredientSection] = []
    instruction_sections: list[InstructionSection] = []

class RecipeUpdate(BaseModel):
    recipe_id: str
    name: Optional[str] = ""
    description: Optional[str] = "" 
    hero_image_link: Optional[str] = "" 
    main_image_link: Optional[str] = "" 
    created_date: Optional[date] = None
    updated_date: Optional[date] = None 
    time_to_plate: Optional[int] = 0 
    total_servings: Optional[int] = 0  
    serving_size: Optional[int] = 0  
    serving_form: Optional[str] = "" 
    calories: Optional[float] = 0 
    fat: Optional[float] = 0 
    saturated_fat: Optional[float] = 0 
    trans_fat: Optional[float] = 0 
    carbohydrate: Optional[float] = 0 
    fibre: Optional[float] = 0 
    sugars: Optional[float] = 0 
    protein: Optional[float] = 0 
    cholesterol: Optional[float] = 0 
    sodium: Optional[float] = 0 
    vitamin_d: Optional[float] = 0 
    iron: Optional[float] = 0 
    potassium: Optional[float] = 0 
    calcium: Optional[float] = 0