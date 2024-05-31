//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makePowerPath;

public class TrucePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = AyaMod.makeID("TrucePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex128 = TextureLoader.getTexture(makePowerPath("truce84.png"));
    private static final Texture tex48 = TextureLoader.getTexture(makePowerPath("truce32.png"));
    public AbstractCreature source;
    private int counter = 0;

    public TrucePower(final AbstractCreature owner, final AbstractCreature source, final int amount,final int addCount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex128, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex48, 0, 0, 32, 32);
        counter += addCount ;
        updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        TheAya.setCanGetWind();
        TheAya.gainWindSpeed(counter);
        counter = 0;
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(owner, owner, TrucePower.POWER_ID, amount));
        this.updateDescription();
    }
    @Override
    public void onRemove() {
        TheAya.setCanGetWind();
        counter = 0;
    }
    @Override
    public AbstractPower makeCopy() {
        return new TrucePower(owner, source, amount,counter);
    }
}

