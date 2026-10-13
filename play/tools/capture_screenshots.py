"""Capture authentic 1080 × 1920 Play Store screenshots from an Android emulator.

Requires Pillow, a running emulator, and an installed debug build. Example:
    python3 play/tools/capture_screenshots.py emulator-5556
"""

import io
import re
import subprocess
import sys
import time
import xml.etree.ElementTree as ET
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parents[2]
PACKAGE = "com.togetherwords"
ADB = Path.home() / "Library/Android/sdk/platform-tools/adb"
BOUNDS = re.compile(r"\[(\d+),(\d+)\]\[(\d+),(\d+)\]")


def adb(serial: str, *args: str, binary: bool = False) -> bytes | str:
    result = subprocess.run(
        [str(ADB), "-s", serial, *args], capture_output=True, check=True
    )
    return result.stdout if binary else result.stdout.decode("utf-8", errors="replace")


def hierarchy(serial: str) -> ET.Element:
    adb(serial, "shell", "uiautomator", "dump", "/sdcard/window.xml")
    return ET.fromstring(adb(serial, "exec-out", "cat", "/sdcard/window.xml"))


def tap_text(serial: str, label: str) -> None:
    for node in hierarchy(serial).iter("node"):
        if label in node.attrib.get("text", "") or label in node.attrib.get("content-desc", ""):
            match = BOUNDS.fullmatch(node.attrib.get("bounds", ""))
            if match:
                left, top, right, bottom = map(int, match.groups())
                adb(serial, "shell", "input", "tap", str((left + right) // 2), str((top + bottom) // 2))
                time.sleep(1)
                return
    raise RuntimeError(f"Could not find visible control: {label}")


def capture(serial: str, locale: str, name: str) -> None:
    target = ROOT / "play" / "screenshots" / locale / name
    target.parent.mkdir(parents=True, exist_ok=True)
    screenshot = Image.open(io.BytesIO(adb(serial, "exec-out", "screencap", "-p", binary=True)))
    if screenshot.size != (1080, 1920):
        raise RuntimeError(f"Unexpected screenshot size: {screenshot.size}")
    screenshot.convert("RGB").save(target, format="PNG", optimize=True)
    print(target.relative_to(ROOT))


def capture_language(serial: str, locale: str, labels: tuple[str, str, str]) -> None:
    adb(serial, "shell", "cmd", "locale", "set-app-locales", PACKAGE, "--locales", locale[:2])
    adb(serial, "shell", "am", "force-stop", PACKAGE)
    adb(serial, "shell", "am", "start", "-n", f"{PACKAGE}/.MainActivity")
    time.sleep(2)

    capture(serial, locale, "01-home.png")
    tap_text(serial, labels[0])
    adb(serial, "shell", "input", "swipe", "540", "1600", "540", "1250", "400")
    time.sleep(1)
    capture(serial, locale, "02-words.png")
    adb(serial, "shell", "input", "keyevent", "4")
    time.sleep(1)
    tap_text(serial, labels[1])
    capture(serial, locale, "03-help.png")
    tap_text(serial, labels[2])
    capture(serial, locale, "04-balloon.png")


def main() -> None:
    if len(sys.argv) != 2:
        raise SystemExit("Usage: capture_screenshots.py <adb-serial>")
    serial = sys.argv[1]
    size_output = adb(serial, "shell", "wm", "size")
    original_override = re.search(r"Override size: (\d+x\d+)", size_output)
    locale_output = adb(serial, "shell", "cmd", "locale", "get-app-locales", PACKAGE)
    original_locale = re.search(r"\[([^\]]*)\]", locale_output)
    try:
        adb(serial, "shell", "wm", "size", "1080x1920")
        capture_language(serial, "en-US", ("Practise my words", "I need help now", "I want to calm down"))
        capture_language(serial, "es-ES", ("Practico mis palabras", "Necesito ayuda ahora", "Quiero calmarme"))
    finally:
        adb(serial, "shell", "wm", "size", original_override.group(1) if original_override else "reset")
        adb(serial, "shell", "cmd", "locale", "set-app-locales", PACKAGE, "--locales", original_locale.group(1) if original_locale else "")


if __name__ == "__main__":
    main()
