package theAya;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAya.animation.AbstractAnimation;
import theAya.cards.*;
import theAya.characters.TheAya;
import theAya.potions.*;
import theAya.relics.*;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.util.AyaColor;
import theAya.util.IDCheckDontTouchPls;
import theAya.util.TextureLoader;
import theAya.variables.CardWindSpeed;
import theAya.variables.DefaultCustomVariable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.esotericsoftware.spine.AnimationState;
import theAya.variables.WindSpeed;

import static theAya.util.AyaColor.*;

@SpireInitializer
public class AyaMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        OnCardUseSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostBattleSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(AyaMod.class.getName());
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    // Colors (RGB)
    // Character Color
    // 这里定义了一些颜色
    public static final Color DEFAULT_GRAY = CardHelper.getColor(245.0f, 115.0f, 48.0f);
    public static final String THE_DEFAULT_SHOULDER_1 = "theAyaResources/images/char/Aya/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theAyaResources/images/char/Aya/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theAyaResources/images/char/Aya/corpse.png";
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theAyaResources/images/Badge.png";
    // Atlas and JSON files for the Animations
    public static final String AYA_SKELETON_ATLAS = "theAyaResources/images/char/Aya/Spine/AyaAnimation.atlas";
    public static final String AYA_SKELETON_JSON = "theAyaResources/images/char/Aya/Spine/AyaAnimation_AyaIdle.json";
    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Aya Mod";

    // =============== INPUT TEXTURE LOCATION =================
    private static final String AUTHOR = "Yohlan"; // OK, It's me.

    // Potion Colors in RGB

    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    private static final String DESCRIPTION = "Adds Aya Shameimaru（射命丸文） from Touhou Project as a new playable character.";
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theAyaResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theAyaResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theAyaResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theAyaResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theAyaResources/images/512/card_small_orb.png";
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theAyaResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theAyaResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theAyaResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theAyaResources/images/1024/card_default_gray_orb.png";
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theAyaResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theAyaResources/images/charSelect/DefaultCharacterPortraitBG.png";
    // 自定义的获取本地化文件的方法
    public static WindSpeedDisplayUnit windSpeedDisplayUnit;
    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties ayaDefaultSettings = new Properties();
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)
    private static String modID;

    public AyaMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        setModID("theAya");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:

        // 1. Go to your resources folder in the project panel, and refactor> rename theDefaultResources to
        // yourModIDResources.

        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project) and press alt+c (or mark the match case option)
        // replace all instances of theDefault with yourModID, and all instances of thedefault with yourmodid (the same but all lowercase).
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        // It's important that the mod ID prefix for keywords used in the cards descriptions is lowercase!

        // 3. Scroll down (or search for "ADD CARDS") till you reach the ADD CARDS section, and follow the TODO instructions

        // 4. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theDefault. They get loaded before getID is a thing.

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheAya.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheAya.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        ayaDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("AyaMod", "ayaConfig", ayaDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        InputStream in = AyaMod.class.getResourceAsStream("/PleaseEditIt.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        InputStream in = AyaMod.class.getResourceAsStream("/PleaseEditIt.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = AyaMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    public static void initialize() {
        logger.info("========================= Initializing Aya Mod. Hi. =========================");
        AyaMod ayaMod = new AyaMod();
        logger.info("========================= /Aya Mod Initialized. Hello World./ =========================");
    }

    // ====== YOU CAN EDIT AGAIN ======

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    private String GetAyaModLocalization() {
        String lang;
        switch (Settings.language) {
            case ZHS:
                lang = "zhs";
                break;
            case ZHT:
                lang = "zht";
                break;
            default:
                lang = "eng";
                break;
        }
        return lang;
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheAya.Enums.THE_AYA.toString());

        BaseMod.addCharacter(new TheAya("the Default", TheAya.Enums.THE_AYA),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheAya.Enums.THE_AYA);

        receiveEditPotions();
        logger.info("Added " + TheAya.Enums.THE_AYA.toString());
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD POTIONS ===================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {
                }, // thing??????? idk
                (button) -> { // The actual button:

                    enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("ayaMod", "ayaConfig", ayaDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
//        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
//            .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
//            .playerClass(TheAya.Enums.THE_AYA) // Character specific event
//            .create();
        windSpeedDisplayUnit = new WindSpeedDisplayUnit();
        // Add the event
//        BaseMod.addEvent(eventParams);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(FlyingPotion.class, AyaColor.FLYING_POTION_LIQUID, AyaColor.FLYING_POTION_HYBRID, null, FlyingPotion.POTION_ID, TheAya.Enums.THE_AYA);
        BaseMod.addPotion(SnapshotPotion.class, AyaColor.SNAPSHOT_POTION_LIQUID, SNAPSHOT_POTION_HYBRID, null, SnapshotPotion.POTION_ID, TheAya.Enums.THE_AYA);
        BaseMod.addPotion(WindPotion.class, WIND_POTION_LIQUID, AyaColor.FLYING_POTION_HYBRID, null, WindPotion.POTION_ID, TheAya.Enums.THE_AYA);
        BaseMod.addPotion(DizzyPotion.class, DIZZY_POTION_LIQUID, AyaColor.FLYING_POTION_HYBRID, null, DizzyPotion.POTION_ID, TheAya.Enums.THE_AYA);
        BaseMod.addPotion(ConversionPotion.class, CONVERSION_POTION_LIQUID, AyaColor.FLYING_POTION_HYBRID, null, ConversionPotion.POTION_ID, TheAya.Enums.THE_AYA);

        logger.info("Done editing potions");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        // Of note is that the bard mod uses it's own custom relic class (not dissimilar to our AbstractDefaultCard class for cards) that adds the 'color' field,
        // in order to automatically differentiate which pool to add the relic too.

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new AyaCamera(), TheAya.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new AyaNote(), TheAya.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Newspaper(), TheAya.Enums.COLOR_GRAY);
        BaseMod.addRelic(new Wing(), RelicType.SHARED);
        BaseMod.addRelic(new TenguMask(), RelicType.SHARED);

        // This adds a relic to the Shared pool. Every character can find this relic.

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        // (the others are all starters so they're marked as seen in the character file)
        logger.info("Done adding relics!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new CardWindSpeed());
        BaseMod.addDynamicVariable(new WindSpeed());

        logger.info("Adding cards");
        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // 这个方法自动添加任何卡片所以不需要一个一个地添加
        // 如果想要更明确的信息,包括如何让一些卡片不被添加,可以查阅:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        //TODO: Rename the "ayaMod" with the modid in your ModTheSpire.json file
        //TODO: The artifact mentioned in ModTheSpire.json is the artifactId in pom.xml you should've edited earlier
        new AutoAdd("AyaMod") // ${project.artifactId}
                .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        logger.info("Done adding cards!");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        String lang = GetAyaModLocalization();
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Event-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-Character-Strings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/" + lang + "/AyaMod-UI-Strings.json");

        // OrbStrings
        //BaseMod.loadCustomStringsFile(OrbStrings.class,
        //getModID() + "Resources/localization/"+lang+"/AyaMod-Orb-Strings.json");
    }

    @Override
    public void receiveEditKeywords() {
        String lang = GetAyaModLocalization();
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + lang + "/AyaMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================
    // ================ 卡片使用时 =====================
    public void receiveCardUsed(AbstractCard abstractCard) {

        AnimationState.TrackEntry a;
        if (abstractCard.type == AbstractCard.CardType.ATTACK) {
            a = TheAya.thisAya.state.setAnimation(1, "AyaAttackFan", false);
        } else {
            a = TheAya.thisAya.state.setAnimation(1, "AyaSpellCard", false);
        }
        a.setTime(a.getEndTime() * 0.3f);
    }

    // ================ /卡片使用时/ ===================
    public void receivePostBattle(AbstractRoom room) {
        WindSpeedDisplayUnit.hide();
        TheAya.refreshWindSpeed();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        WindSpeedDisplayUnit.hide();
        TheAya.refreshWindSpeed();
        TheAya.changeWindType(WindSpeedDisplayUnit.WindType.SOAR);
        AbstractAnimation.clearAll();
    }
}
