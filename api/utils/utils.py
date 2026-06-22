
import uuid

def getHashedName(name: str) -> str:
    THYME_NAMESPACE = uuid.UUID("1f0bdcf7-7bb0-5307-b10e-1dce378d1473")
    return str(uuid.uuid5(THYME_NAMESPACE, name))

