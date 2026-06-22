-- name: get_all_recipes_base_1
SELECT r.recipe_id, r.name, r.description, r.hero_image_link, r.main_image_link, 
       r.created_date, r.updated_date, r.time_to_plate, array_agg(t.name) AS tags
FROM recipes r
JOIN recipe_tags rt ON r.recipe_id = rt.recipe_id
JOIN tags t ON rt.tag_id = t.id
WHERE r.is_active = TRUE

-- name: get_all_recipes_base_2
GROUP BY r.recipe_id, r.name, r.description, r.hero_image_link, r.main_image_link, 
         r.created_date, r.updated_date, r.time_to_plate

-- name: get_ingredient
select * 
from ingredients i

-- name: get_tag_id
SELECT id FROM tags WHERE name = :name;

-- name: get_full_recipe_by_id
SELECT
    r.recipe_id,
    r.name,
    r.description,
    r.hero_image_link,
    r.main_image_link,
    r.created_date,
    r.updated_date,
    r.time_to_plate,
    r.total_servings,
    r.serving_size,
    r.serving_form,
    r.calories,
    r.fat,
    r.saturated_fat,
    r.trans_fat,
    r.carbohydrate,
    r.fibre,
    r.sugars,
    r.protein,
    r.cholesterol,
    r.sodium,
    r.vitamin_d,
    r.iron,
    r.potassium,
    r.calcium,
    -- Tags
    array_agg(DISTINCT t.name) FILTER (WHERE t.name IS NOT NULL) AS tags,
    -- Notes
    json_agg(DISTINCT jsonb_build_object(
        'id', rnotes.id,
	    'display_name', rnotes.display_name,
	    'placement',    rnotes.placement,
	    'content',      rnotes.content
	)) FILTER (WHERE rnotes.id IS NOT NULL) AS notes,
    -- Ingredients (as JSON array, grouped by section)
    json_agg(DISTINCT jsonb_build_object(
        'section_id',   ins.id,
        'section_name', ins.section_name,
        'sort_order',   ins.sort_order,
        'ingredients',  (
            SELECT json_agg(
                jsonb_build_object(
                    'ingredient_id',   i.id,
                    'fdc_id',          i.fdc_id,
                    'name',            i.name,
                    'quantity',        ri.quantity,
                    'measurement',     ri.measurement,
                    'gram_weight',     ri.gram_weight,
                    'conversion_type', ri.conversion_type,
                    'sort_order',      ri.sort_order,
                    'calories', i.calories,
                    'fat', i.fat,
                    'saturated_fat', i.saturated_fat,
                    'trans_fat', i.trans_fat,
                    'carbohydrate', i.carbohydrate,
                    'fibre', i.fibre,
                    'sugars', i.sugars,
                    'protein', i.protein,
                    'cholesterol', i.cholesterol,
                    'sodium', i.sodium,
                    'vitamin_d', i.vitamin_d,
                    'iron', i.iron,
                    'potassium', i.potassium,
                    'calcium', i.calcium
                ) ORDER BY ri.sort_order
            )
            FROM recipe_ingredients ri
            JOIN ingredients i ON ri.ingredient_id = i.id
            WHERE ri.section_id = ins.id
        )
    )) FILTER (WHERE ins.id IS NOT NULL) AS ingredient_sections,
    -- Instructions (as JSON array, grouped by section)
    json_agg(DISTINCT jsonb_build_object(
        'section_id',   isc.id,
        'section_name', isc.section_name,
        'alt_text',     isc.alt_text,
        'image_link',   isc.image_link,
        'sort_order',   isc.sort_order,
        'steps',        (
            SELECT json_agg(
                jsonb_build_object(
                    'step_id',    s.id,
                    'section_id',  s.section_id,
                    'step_text',  s.step_text,
                    'sort_order', s.sort_order
                ) ORDER BY s.sort_order
            )
            FROM instruction_steps s
            WHERE s.section_id = isc.id
        )
    )) FILTER (WHERE isc.id IS NOT NULL) AS instruction_sections

FROM recipes r
LEFT JOIN recipe_tags rt         	ON r.recipe_id = rt.recipe_id
LEFT JOIN tags t                 	ON rt.tag_id = t.id
LEFT JOIN recipe_notes rnotes    	ON rnotes.recipe_id = r.recipe_id
LEFT JOIN ingredient_sections ins  	ON r.recipe_id = ins.recipe_id
LEFT JOIN instruction_sections isc	ON r.recipe_id = isc.recipe_id
WHERE r.recipe_id = :recipe_id
GROUP BY r.id;