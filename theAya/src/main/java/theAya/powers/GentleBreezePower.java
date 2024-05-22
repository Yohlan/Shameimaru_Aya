package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.util.TextureLoader;

public class GentleBreezePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = AyaMod.makeID("GentleBreezePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theAyaResources/images/powers/heal84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theAyaResources/images/powers/heal32.png");

    public GentleBreezePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() { // At the start of your turn
        if(TheAya.windType == WindSpeedDisplayUnit.WindType.HEAL){
            AbstractPlayer p = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AddTemporaryHPAction((AbstractCreature)p, (AbstractCreature)p, 5));
            flash();
        }
    }
    @Override
    public void onEnergyRecharge() {
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0]+this.amount*5+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new GentleBreezePower(owner, source, amount);
    }
}
