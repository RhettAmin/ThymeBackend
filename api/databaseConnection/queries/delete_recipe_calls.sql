-- name: delete_recipe
DELETE FROM recipes 
WHERE recipe_id = :recipe_id

-- name: delete_recipe_tags
DELETE FROM recipe_tags 
WHERE recipe_id = :recipe_id

-- name: delete_recipe_notes
DELETE FROM recipe_notes
WHERE recipe_id = :recipe_id

-- name: delete_ingredient_sections
DELETE FROM ingredient_sections 
WHERE recipe_id = :recipe_id;

-- name: delete_instruction_sections
DELETE FROM instruction_sections 
WHERE recipe_id = :recipe_id;