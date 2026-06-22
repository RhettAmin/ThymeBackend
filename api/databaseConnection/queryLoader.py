from pathlib import Path

QUERIES_DIR = Path(__file__).resolve().parent / "queries"

def load_query(file: str, name: str) -> str:
    path = QUERIES_DIR / file
    sql = path.read_text()
    
    for block in sql.split("-- name:"):
        lines = block.strip().splitlines()
        if not lines:
            continue
        if lines[0].strip() == name:
            return "\n".join(lines[1:]).strip()
     
    raise ValueError(f"Query '{name}' not found in {file}")