# Thyme Backend API
# Main entry point to the API

from fastapi import FastAPI
from api.router import recipes, utils
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI(title="Thyme to Dine API")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"], 
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(recipes.router, prefix="/recipes", tags=["Recipes"])
app.include_router(utils.router, prefix="/utils", tags=["Utils"])

