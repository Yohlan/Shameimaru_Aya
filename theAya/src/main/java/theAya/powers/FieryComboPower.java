//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit.WindType;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makePowerPath;

public class FieryComboPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = AyaMod.makeID("FieryComboPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex128 = TextureLoader.getTexture(makePowerPath("fiery84.png"));
    private static final Texture tex48 = TextureLoader.getTexture(makePowerPath("fiery32.png"));
    public AbstractCreature source;

    public FieryComboPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex128, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex48, 0, 0, 32, 32);
        updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
            flash(); // Makes the power icon flash.
            AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(owner, owner, POWER_ID, amount));
    }
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(AbstractDungeon.player instanceof TheAya && TheAya.windType == WindType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, 1), 1));
        }
    }
    @Override
    public AbstractPower makeCopy() {
        return new FieryComboPower(owner, source);
    }
}

