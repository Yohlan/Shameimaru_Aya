//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makePowerPath;

public class FlightPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = AyaMod.makeID("FlightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex128 = TextureLoader.getTexture(makePowerPath("fly128.png"));
    private static final Texture tex48 = TextureLoader.getTexture(makePowerPath("fly48.png"));
    public AbstractCreature source;
    private int counter = 0;

    public FlightPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(tex48, 0, 0, 48, 48);
        counter = this.amount;
        updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        if(this.amount>1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }else{
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }

    public void atStartOfTurn() {
        this.updateDescription();
    }
    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage;
    }

    private float calculateDamageTakenAmount(float damage, DamageInfo.DamageType type) {
        if (counter>0) {
            return damage / 2.0F;
        }
        return damage;
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        int damage;
        Boolean willLive = this.calculateDamageTakenAmount((float) damageAmount, info.type) < (float) this.owner.currentHealth;
        if (counter>0&&info.owner != AbstractDungeon.player && info.owner != null && info.type != DamageType.HP_LOSS && info.type != DamageType.THORNS && damageAmount > 0 && willLive) {
            this.flash();
            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            damage = (int)this.calculateDamageTakenAmount((float) damageAmount, info.type);
            counter-=1;
            return damage;
        }
        return damageAmount;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        counter += power.amount;
    }
    @Override
    public void onRemove() {
        counter = 0;
    }
    @Override
    public AbstractPower makeCopy() {
        return new FlightPower(owner, source, amount);
    }
}

