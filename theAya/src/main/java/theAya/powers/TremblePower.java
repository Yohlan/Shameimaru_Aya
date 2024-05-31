package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.util.TextureLoader;

public class TremblePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = AyaMod.makeID("TremblePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theAyaResources/images/powers/tremble84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theAyaResources/images/powers/tremble32.png");

    public TremblePower(final AbstractCreature owner, final AbstractCreature source, final int  amount) {
        name = NAME;
        ID = POWER_ID;
        int temp_amount = amount;
        if(amount >= 3 && owner instanceof AbstractMonster) {
            addToBot(new TextAboveCreatureAction(owner, TextAboveCreatureAction.TextType.STUNNED));
            AbstractMonster m = (AbstractMonster) owner;
            m.setMove((byte) 4, AbstractMonster.Intent.STUN);
            m.createIntent();
            temp_amount = 0;
        }
        this.owner = owner;
        this.amount = temp_amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void stackPower(int stackAmount) {
        int counter = this.amount + stackAmount;
        this.amount += stackAmount;
        if(counter >= 3 && owner instanceof AbstractMonster) {
            addToBot(new TextAboveCreatureAction(owner, TextAboveCreatureAction.TextType.STUNNED));
            AbstractMonster m = (AbstractMonster) owner;
            m.setMove((byte) 4, AbstractMonster.Intent.STUN);
            m.createIntent();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TremblePower(owner, source, amount);
    }
}
