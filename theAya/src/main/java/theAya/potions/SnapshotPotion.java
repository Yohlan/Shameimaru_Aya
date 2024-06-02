package theAya.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAya.AyaMod;
import theAya.actions.potions.FlyingPotionAction;
import theAya.actions.potions.SnapshotPotionAction;

public class SnapshotPotion
        extends BasePotion {

    public static final String POTION_ID = AyaMod.makeID(SnapshotPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SnapshotPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.CARD, PotionColor.SMOKE);
        this.labOutlineColor = AyaMod.DEFAULT_GRAY.cpy();
    }


    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new SnapshotPotionAction());
    }


    public void initializeData() {
        this.isThrown = false;
        this.targetRequired = false;
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        initializeDescription();
    }


    public int getPotency(int level) {
        return 1;
    }


    public AbstractPotion makeCopy() {
        return new SnapshotPotion();
    }
}