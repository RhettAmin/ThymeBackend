from sqlalchemy.ext.asyncio import create_async_engine, AsyncSession, async_sessionmaker
from sqlalchemy.orm import DeclarativeBase

from api.databaseConnection.config import settings

engine = create_async_engine(
    settings.DATABASE_URL,
    echo=settings.DEBUG,   # logs SQL in dev, silent in prod
    pool_size=10,
    max_overflow=20,
)

AsyncSessionLocal = async_sessionmaker(
    bind=engine,
    class_=AsyncSession,
    expire_on_commit=False,  # avoids lazy-load errors after commit
)

class Base(DeclarativeBase):
    pass