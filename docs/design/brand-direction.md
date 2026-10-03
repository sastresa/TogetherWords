# Words & Pause / Palabras y pausa — visual direction

Status: implemented visual direction, updated 2 October 2026.

## Idea

The child can express a need or take a pause with an adult nearby. The brand line is **Your words, your pace / Tus palabras, a tu ritmo**. It makes no promise to stop distress or require speech or breathing at a set rhythm.

## Identity

- English name: **Words & Pause**
- Spanish name: **Palabras y pausa**
- Symbol: a single word balloon with a short string. It joins the app's two actions: expressing a need and taking a quiet pause. The pale balloon on deep teal stays distinct at launcher size.
- Voice: short, direct, and reassuring. Instructions invite rather than command.
- Artwork: abstract shapes only. No faces, rewards, alarms, or exaggerated emotions.

The name is provisional until store and trademark availability are checked.

## Design tokens

| Token | Value | Use |
| --- | --- | --- |
| Canvas | `#F7F6F0` | Warm screen background |
| Splash | `#EAF3ED` | Soft green launch background |
| Ink | `#243C43` | Main text |
| Deep teal | `#275B61` | Selected controls and brand mark |
| Mint | `#D9EEE5` | Words route |
| Peach | `#F6E4D5` | Pause/help route |
| Mist blue | `#DEEAF2` | Secondary cards |
| Lavender | `#EAE3F1` | Fourth phrase card |

Use a clear system sans serif, with medium-weight headings and regular body text. Keep at least 20 dp page margins, 16 dp between major elements, and 24 dp card corners. Home choices are approximately 132 dp tall and phrase cards approximately 96 dp tall, with layouts allowed to grow for larger text and Spanish copy. All actions remain at least 48 dp in both dimensions.

## Screens

- **Home:** an open, unboxed headline says “You’re by their side. That matters.” / “Estás a su lado. Eso importa.” beside a balloon illustration. It offers reassurance without promising an outcome. A short question leads to two large horizontal choices: practise words in calm moments or ask for help now. The app follows the device language by default. A discreet For adults/Para adultos action opens practical guidance and the language choice.
- **Help choice:** two large options, with no intervening menu or decision.
- **Words:** four large cards in mint, mist blue, peach, and lavender, each with a small distinct pictogram. Text remains the primary cue. One selected language at a time. A card can speak its phrase, but there is no recording, requirement to repeat, or success state. Back/Volver uses a 20 sp label, a 30 dp arrow, and a 56 dp touch target.
- **Balloon:** one large, touch-controlled shape. Tap to let it grow or rest. Use a gentle duration and honor reduced-motion settings.

The adult area and language action do not appear on the child-facing Help, Words, or Balloon screens. On Android 13 and newer, the selection should stay synchronized with Android's per-app language setting; on older devices, the in-app choice provides the same override. See `parent-guide-content.md` for bilingual parent copy and sources.

Broad, pale vector shapes sit behind every screen. They are decorative only and do not resemble controls; cards, text, and the balloon remain the focus.

## Launch and icon

Use the word-balloon symbol in the adaptive launcher icon and Android system splash screen. The icon in `icon-concept.svg` uses a full deep-teal background and keeps the foreground within Android's adaptive-icon safe area. `icon-monochrome.svg` is the themed-icon version. The splash enlarges the balloon within a peach disc on a soft green background, with no slogan or timed transition. Android 12 and newer use the system's centered-icon splash; older versions use the matching window background.

## Review criteria before implementation

1. Readability at small phone sizes and with larger system text.
2. English and Spanish copy fits naturally without reducing type size.
3. Icon still reads at launcher size and under circle and rounded-square masks.
4. Text and controls meet contrast and touch-target goals.
5. Motion and speech never block leaving a screen.

Open `brand-preview.html` for the icon, splash, child-facing screens, and the adult guide. Use its language control to compare English and Spanish. The current home is captured in `home-en.png` and `home-es.png`; the other static review sheets remain in `screens-en.png`, `screens-es.png`, `parent-guide-en.png`, and `parent-guide-es.png`. `icon-preview.png` and `launch-preview.png` show the two launch assets at full size. The adult copy and its sources are in `parent-guide-content.md`.
