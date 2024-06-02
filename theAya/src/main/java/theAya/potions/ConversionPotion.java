package theAya.potions;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.util.AyaColor;

public class ConversionPotion
        extends BasePotion {

    public static final String POTION_ID = AyaMod.makeID(ConversionPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ConversionPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.EYE, PotionColor.NONE);
        this.labOutlineColor = AyaMod.DEFAULT_GRAY.cpy();
    }


    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.player instanceof TheAya) {
                AbstractPlayer p = AbstractDungeon.player;
                WindSpeedDisplayUnit.WindType windType = TheAya.windType;
                switch (windType) {
                    case SOAR:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlightPower(p, p, 2), 2));
                        TheAya.changeWindType(WindSpeedDisplayUnit.WindType.ATTACK);
                        break;
                    case HEAL:
                        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, 5));
                        TheAya.changeWindType(WindSpeedDisplayUnit.WindType.SOAR);
                        break;
                    case ATTACK:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
                        TheAya.changeWindType(WindSpeedDisplayUnit.WindType.DEFEND);
                        break;
                    case DEFEND:
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 15));
                        TheAya.changeWindType(WindSpeedDisplayUnit.WindType.HEAL);
                        break;
                }
            }
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(AyaMod.DEFAULT_GRAY, true));
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
        return new ConversionPotion();
    }
}