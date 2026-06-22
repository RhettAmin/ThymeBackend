-- name: insert_recipe
INSERT INTO recipes (
    recipe_id, name, description, hero_image_link, main_image_link,
    created_date, updated_date, time_to_plate, total_servings,
    serving_size, serving_form, calories, fat, saturated_fat,
    trans_fat, carbohydrate, fibre, sugars, protein, cholesterol,
    sodium, vitamin_d, iron, potassium, calcium
) VALUES (
    :recipe_id, :name, :description, :hero_image_link, :main_image_link,
    :created_date, :updated_date, :time_to_plate, :total_servings,
    :serving_size, :serving_form, :calories, :fat, :saturated_fat,
    :trans_fat, :carbohydrate, :fibre, :sugars, :protein, :cholesterol,
    :sodium, :vitamin_d, :iron, :potassium, :calcium
)

-- name: insert_tag
INSERT INTO tags (name) VALUES (:name)
ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name
RETURNING id;

-- name: insert_recipe_tag
INSERT INTO recipe_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id);

-- name: insert_recipe_notes
INSERT INTO recipe_notes (recipe_id, content, display_name, placement)
VALUES (:recipe_id, :content, :display_name, :placement)
RETURNING id;

-- name: insert_ingredient_section
INSERT INTO ingredient_sections (recipe_id, section_name, sort_order)
VALUES (:recipe_id, :section_name, :sort_order)
RETURNING id;

-- name: insert_recipe_ingredient
INSERT INTO recipe_ingredients (section_id, ingredient_id, quantity, measurement, conversion_type, sort_order, gram_weight)
VALUES (:section_id, :ingredient_id, :quantity, :measurement, :conversion_type, :sort_order, :gram_weight);

-- name: insert_ingredient
WITH existing_by_fdc AS (
    SELECT id FROM ingredients
    WHERE fdc_id = NULLIF(:fdc_id, 0)
),
upsert AS (
    INSERT INTO ingredients (name, fdc_id, calories, fat, saturated_fat, trans_fat, carbohydrate, fibre, sugars, protein, cholesterol, sodium, vitamin_d, iron, potassium, calcium)
    SELECT :i_name, NULLIF(:fdc_id, 0), :calories, :fat, :saturated_fat, :trans_fat, :carbohydrate, :fibre, :sugars, :protein, :cholesterol, :sodium, :vitamin_d, :iron, :potassium, :calcium
    WHERE NOT EXISTS (SELECT 1 FROM existing_by_fdc)
    ON CONFLICT (name) DO UPDATE SET
        fdc_id        = EXCLUDED.fdc_id,
        calories      = EXCLUDED.calories,
        fat           = EXCLUDED.fat,
        saturated_fat = EXCLUDED.saturated_fat,
        trans_fat     = EXCLUDED.trans_fat,
        carbohydrate  = EXCLUDED.carbohydrate,
        fibre         = EXCLUDED.fibre,
        sugars        = EXCLUDED.sugars,
        protein       = EXCLUDED.protein,
        cholesterol   = EXCLUDED.cholesterol,
        sodium        = EXCLUDED.sodium,
        vitamin_d     = EXCLUDED.vitamin_d,
        iron          = EXCLUDED.iron,
        potassium     = EXCLUDED.potassium,
        calcium       = EXCLUDED.calcium
    RETURNING id
)
SELECT id FROM upsert
UNION ALL
SELECT id FROM existing_by_fdc
LIMIT 1;

-- name: insert_instruction_section
INSERT INTO instruction_sections (recipe_id, section_name, alt_text, image_link, sort_order)
VALUES (:recipe_id, :section_name, :alt_text, :image_link, :sort_order)
RETURNING id;

-- name: insert_instruction_step
INSERT INTO instruction_steps (section_id, step_text, sort_order)
VALUES (:section_id, :step_text, :sort_order);