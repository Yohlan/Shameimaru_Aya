package theAya.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theAya.AyaMod;
import theAya.cards.*;
import theAya.powers.ConvectionPower;
import theAya.powers.ShiftingTenguPower;
import theAya.relics.AyaCamera;
import theAya.relics.AyaNote;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.vfx.FlexibleCalmParticleEffect;

import java.util.ArrayList;

import static theAya.AyaMod.*;
import static theAya.characters.TheAya.Enums.COLOR_GRAY;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources


/// 这个文件是射命丸文角色文件,与角色贴图和动画,选取界面和音效,能量球贴图和人物相关事件,
/// 初始牌组和初始遗物还有初始数值以及获取卡片和遗物等有关.





public class TheAya extends CustomPlayer {
    //TODO:单例,可能产生Bug
    public static TheAya thisAya;
    //==================
    public static final Logger logger = LogManager.getLogger(AyaMod.class.getName());
    public static WindSpeedDisplayUnit.WindType windType = WindSpeedDisplayUnit.WindType.SOAR;

    // =============== 角色枚举成员 =================

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_AYA;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== 角色枚举成员  =================


    // =============== 初始状态 =================
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 6;
    public static final int ORB_SLOTS = 0;
    public static float particleTimer = 0.00F;
    private static int windSpeed = 0;
    private static int windSpeedCounter = 0;

    // =============== /初始状态/ =================


    // =============== 字符串 =================

    private static final String ID = makeID("Aya");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /字符串/ =================


    // =============== 大能量球的材质 ===============

    public static final String[] orbTextures = {
            "theAyaResources/images/char/Aya/orb/layer1.png",
            "theAyaResources/images/char/Aya/orb/layer2.png",
            "theAyaResources/images/char/Aya/orb/layer3.png",
            "theAyaResources/images/char/Aya/orb/layer4.png",
            "theAyaResources/images/char/Aya/orb/layer5.png",
            "theAyaResources/images/char/Aya/orb/layer6.png",
            "theAyaResources/images/char/Aya/orb/layer1d.png",
            "theAyaResources/images/char/Aya/orb/layer2d.png",
            "theAyaResources/images/char/Aya/orb/layer3d.png",
            "theAyaResources/images/char/Aya/orb/layer4d.png",
            "theAyaResources/images/char/Aya/orb/layer5d.png",};

    // =============== /大能量球的材质/ ===============

    // =============== 角色类开始 =================

    public TheAya(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theAyaResources/images/char/Aya/orb/vfx.png", null,
                new SpineAnimation("theAyaResources/images/char/Aya/Spine/AyaAnimation.atlas","theAyaResources/images/char/Aya/Spine/AyaAnimation_AyaIdle.json",1.3f));
        //TODO:可能产生Bug的单例使用
        thisAya = this;
        //======================
        // =============== 人物贴图,能量,加载 =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /人物贴图,能量,加载/ =================


        // =============== 角色动画 =================

        loadAnimation(
                AYA_SKELETON_ATLAS,
                AYA_SKELETON_JSON,
                1f);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "AyaIdle", true);
        e.setTime(e.getEndTime() * MathUtils.random());



        // =============== /角色动画/ =================


        // =============== 对话气泡位置 =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /对话气泡位置/ =================

    }

    // =============== /角色类结束/ =================

    // 起始描述和加载
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // 起始卡组
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        logger.info("Begin loading starter Deck Strings");

        retVal.add(Strike_Aya.ID);
        retVal.add(Strike_Aya.ID);
        retVal.add(Strike_Aya.ID);
        retVal.add(Strike_Aya.ID);

        retVal.add(Defend_Aya.ID);
        retVal.add(Defend_Aya.ID);
        retVal.add(Defend_Aya.ID);
        retVal.add(Defend_Aya.ID);

        retVal.add(MapleDance.ID);
        retVal.add(SpeedUp.ID);

        return retVal;
    }

    // 起始遗物
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(AyaCamera.ID);
        UnlockTracker.markRelicAsSeen(AyaCamera.ID);
        UnlockTracker.markRelicAsSeen(AyaNote.ID);


        return retVal;
    }

    // 角色选取界面的效果
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_WHIFF_1", 1F); // 音效
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // 屏幕效果
    }

    // 角色选取按钮按下时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MAP_SELECT_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return AyaMod.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Aya();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheAya(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return AyaMod.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return AyaMod.DEFAULT_GRAY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }
    public static void changeWindType(WindSpeedDisplayUnit.WindType windType){
            if(AbstractDungeon.player.hasPower(ShiftingTenguPower.POWER_ID)){
                TheAya.gainWindSpeed(AbstractDungeon.player.getPower(ShiftingTenguPower.POWER_ID).amount * 10);
            }
            TheAya.windType = windType;
            WindSpeedDisplayUnit.changeWindType(windType);
            FlexibleCalmParticleEffect.changeWindType(windType);
    }
    public static void gainWindSpeed(int amount){
        AbstractPlayer p = AbstractDungeon.player;
        TheAya.windSpeed += amount;
            if(p.hasPower(ConvectionPower.POWER_ID)){
                int damage = p.getPower(ConvectionPower.POWER_ID).amount * 3;
                windSpeedCounter += amount;
                while(windSpeedCounter >= 30){
                    windSpeedCounter -= 30;
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }
            }

    }
    public static int getWindSpeed(){return TheAya.windSpeed;}
    public static void loseWindSpeed(int amount){
        if(TheAya.windSpeed >= amount) {
            windSpeed -= windSpeed;
        }
        else {
            windSpeed = 0;
        }
    }
    public static void refreshWindSpeed(){
        TheAya.windSpeed = 0;
        TheAya.windSpeedCounter = 0;
    }

}
