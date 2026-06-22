from fastapi import APIRouter, Depends, HTTPException

from api.utils.utils import getHashedName

router = APIRouter()

@router.get("/health")
async def returnHealth():
    return {"status": "ok"}

@router.get("/hashedName/{name}")
async def returnHashedName(name: str):
    hashedName = getHashedName(name)
    return { 
        "hashed_name": hashedName
    }

