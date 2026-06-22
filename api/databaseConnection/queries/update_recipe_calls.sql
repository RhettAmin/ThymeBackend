-- name: update_recipe
UPDATE recipes SET
    name            = :name,
    description     = :description,
    hero_image_link = :hero_image_link,
    main_image_link = :main_image_link,
    updated_date    = :updated_date,
    time_to_plate   = :time_to_plate,
    total_servings  = :total_servings,
    serving_size    = :serving_size,
    serving_form    = :serving_form,
    calories        = :calories,
    fat             = :fat,
    saturated_fat   = :saturated_fat,
    trans_fat       = :trans_fat,
    carbohydrate    = :carbohydrate,
    fibre           = :fibre,
    sugars          = :sugars,
    protein         = :protein,
    cholesterol     = :cholesterol,
    sodium          = :sodium,
    vitamin_d       = :vitamin_d,
    iron            = :iron,
    potassium       = :potassium,
    calcium         = :calcium
WHERE recipe_id = :recipe_id
RETURNING id;

-- name: update_tag
UPDATE tags SET 
    name = :name
WHERE id = :id;

-- name: update_recipe_notes
UPDATE recipe_notes SET 
    content = :content,
    display_name = :display_name,
    placement = :placement
WHERE recipe_id = :recipe_id AND id = :id;

-- name: update_ingredient_section
UPDATE ingredient_sections SET
    section_name = :section_name,
    sort_order   = :sort_order
WHERE id = :id;