package theAya.potions;

import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAya.AyaMod;
import theAya.characters.TheAya;

public class DizzyPotion
        extends BasePotion {

    public static final String POTION_ID = AyaMod.makeID(DizzyPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DizzyPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPHERE,PotionColor.SKILL);
        this.labOutlineColor = AyaMod.DEFAULT_GRAY.cpy();
        this.targetRequired = true;
    }


    public void use(AbstractCreature m) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new TextAboveCreatureAction(m, TextAboveCreatureAction.TextType.STUNNED));
            AbstractMonster monster = (AbstractMonster) m;
            monster.setMove((byte) 4, AbstractMonster.Intent.STUN);
            monster.createIntent();
        }
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
        return new DizzyPotion();
    }
}