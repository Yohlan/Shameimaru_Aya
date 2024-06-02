package theAya.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAya.AyaMod;
import theAya.actions.AyaCameraAction;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makeRelicOutlinePath;
import static theAya.AyaMod.makeRelicPath;

public class AyaNote extends CustomRelic implements ClickableRelic {
    // ID, images, text.
    public static final String ID = AyaMod.makeID("AyaNote");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("aya_note.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("aya_note.png"));
    private boolean cameraUsed = false;
    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.

    public AyaNote() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(AyaCamera.ID)) {
            instantObtain(AbstractDungeon.player, 0, false);
        } else {
            super.obtain();
        }
    }

    @Override
    public void onEquip() {

    }

    @Override
    public void onUnequip() {

    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(AyaCamera.ID);
    }

    @Override
    public void onRightClick() {// On right click,当至少有一张手牌时，右键点击时触发
        if (AbstractDungeon.player.hand.isEmpty() || !isObtained || cameraUsed || !isPlayerTurn || AbstractDungeon.player.hand.size() == 10) {
            // If it has been used this turn, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return; // Don't do anything.
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // Only if you're in combat
            cameraUsed = true;
            addToBot((AbstractGameAction) new AyaCameraAction(AbstractDungeon.player, 1, DESCRIPTIONS,true));
            stopPulse(); // And stop the pulsing animation (which is started in atPreBattle() below)
        }
        // See that talk action? It has DESCRIPTIONS[1] instead of just hard-coding "You are mine" inside.
        // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

        /*
         * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
         * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
         *
         * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
         * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
         *
         * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
         *
         */
    }

    @Override
    public void atPreBattle() {
        cameraUsed = false;  // At the start of battle, it's ready to be used.
        beginLongPulse();     // Pulse while the player can click on it.
    }

    public void atTurnStart() {
        isPlayerTurn = true; // It's our turn!
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
    }


    @Override
    public void onVictory() {
        stopPulse(); // Don't keep pulsing past the victory screen/outside of combat.
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
