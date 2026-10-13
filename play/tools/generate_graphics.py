"""Generate the Google Play icon and localized feature graphics.

Requires Pillow. Run from the repository root with:
    python3 play/tools/generate_graphics.py
"""

from pathlib import Path

from PIL import Image, ImageDraw, ImageFont


ROOT = Path(__file__).resolve().parents[2]
OUTPUT = ROOT / "play" / "graphics"
SCALE = 2

CREAM = "#F7F6F0"
INK = "#233E44"
TEAL = "#275B61"
MINT = "#D9EEE5"
BLUE = "#DCEAF1"
PEACH = "#F6E4D5"
LAVENDER = "#EAE4F2"


def scaled(value: int) -> int:
    return value * SCALE


def font(size: int, bold: bool = False) -> ImageFont.FreeTypeFont:
    name = "Arial Bold.ttf" if bold else "Arial.ttf"
    paths = [
        Path("/System/Library/Fonts/Supplemental") / name,
        Path("/usr/share/fonts/truetype/dejavu")
        / ("DejaVuSans-Bold.ttf" if bold else "DejaVuSans.ttf"),
    ]
    for path in paths:
        if path.exists():
            return ImageFont.truetype(str(path), scaled(size))
    raise FileNotFoundError("Install Arial or DejaVu Sans to regenerate Play graphics")


def rect(draw: ImageDraw.ImageDraw, box: tuple[int, int, int, int], radius: int, fill: str) -> None:
    draw.rounded_rectangle(tuple(scaled(value) for value in box), scaled(radius), fill=fill)


def text(draw: ImageDraw.ImageDraw, position: tuple[int, int], value: str, size: int, *, bold=False, fill=INK) -> None:
    draw.text(tuple(scaled(value) for value in position), value, font=font(size, bold), fill=fill)


def feature_graphic(locale: str, name: str, headline: tuple[str, ...], phrases: tuple[str, ...]) -> None:
    image = Image.new("RGB", (scaled(1024), scaled(500)), CREAM)
    draw = ImageDraw.Draw(image)

    # The soft corners echo the background used throughout the app.
    draw.ellipse((scaled(-170), scaled(295), scaled(285), scaled(750)), fill=PEACH)
    draw.ellipse((scaled(765), scaled(-215), scaled(1210), scaled(230)), fill=MINT)
    text(draw, (70, 74), name, 24, bold=True, fill=TEAL)
    for index, line in enumerate(headline):
        text(draw, (70, 153 + index * 57), line, 46, bold=True)
    rect(draw, (70, 153 + len(headline) * 57 + 8, 150, 153 + len(headline) * 57 + 16), 4, PEACH)

    card_specs = (
        (534, 102, 729, 211, MINT, phrases[0]),
        (749, 102, 944, 211, BLUE, phrases[1]),
        (534, 231, 729, 340, PEACH, phrases[2]),
        (749, 231, 944, 340, LAVENDER, phrases[3]),
    )
    for left, top, right, bottom, color, phrase in card_specs:
        rect(draw, (left + 2, top + 7, right + 2, bottom + 7), 23, "#E8E7E1")
        rect(draw, (left, top, right, bottom), 23, color)
        multi_line = "\n" in phrase
        text(draw, (left + 22, top + (22 if multi_line else 35)), phrase, 23 if multi_line else 29, bold=True)

    image = image.resize((1024, 500), Image.Resampling.LANCZOS)
    image.save(OUTPUT / f"feature-{locale}.png", optimize=True)


def main() -> None:
    OUTPUT.mkdir(parents=True, exist_ok=True)
    icon = Image.open(ROOT / "docs" / "design" / "icon-preview.png")
    icon.convert("RGBA").save(OUTPUT / "icon.png", optimize=True)
    feature_graphic(
        "en-US",
        "Words & Pause",
        ("Words for", "big feelings"),
        ("No", "Stop", "Help", "I need\na break"),
    )
    feature_graphic(
        "es-ES",
        "Palabras y pausa",
        ("Palabras para", "emociones", "intensas"),
        ("No", "Para", "Ayuda", "Necesito un\ndescanso"),
    )


if __name__ == "__main__":
    main()
