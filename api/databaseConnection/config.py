from pathlib import Path
from pydantic_settings import BaseSettings, SettingsConfigDict
from urllib.parse import quote_plus

BASE_DIR = Path(__file__).resolve().parent.parent.parent

class Settings(BaseSettings):
    # Database
    DB_HOST: str
    DB_PORT: int = 5432
    DB_USER: str
    DB_PASSWORD: str
    DB_NAME: str


    # App
    APP_ENV: str = "development"
    DEBUG: bool = False

    model_config = SettingsConfigDict(
        env_file=BASE_DIR / ".env",
        env_file_encoding="utf-8",
        case_sensitive=True,
    )

    @property
    def DATABASE_URL(self) -> str:
        return (
            f"postgresql+asyncpg://{self.DB_USER}:{quote_plus(self.DB_PASSWORD)}"
            f"@{self.DB_HOST}:{self.DB_PORT}/{self.DB_NAME}"
        )
print("Looking for .env at:", BASE_DIR / ".env")
print("File exists:", (BASE_DIR / ".env").exists())

settings = Settings() # type: ignore
print("DB_HOST:", settings.DB_HOST)