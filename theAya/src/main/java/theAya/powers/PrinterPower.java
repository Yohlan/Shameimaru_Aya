//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theAya.AyaMod;
import theAya.actions.AyaCameraAction;
import theAya.characters.TheAya;
import theAya.util.TextureLoader;

import java.awt.*;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

import static theAya.AyaMod.makePowerPath;

public class PrinterPower extends AbstractPower implements CloneablePowerInterface, Observer {
    public static final String POWER_ID = AyaMod.makeID("PrinterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex128 = TextureLoader.getTexture(makePowerPath("print84.png"));
    private static final Texture tex48 = TextureLoader.getTexture(makePowerPath("print32.png"));
    public AbstractCreature source;


    //监听事件
    private EventListener listener;

    public PrinterPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

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

    public void atStartOfTurnPostDraw() {
        addToBot(new AyaCameraAction(owner,1,false,1,this.amount));
    }
    @Override
    public void onRemove() {
        TheAya.setCanGetWind();
    }
    @Override
    public AbstractPower makeCopy() {
        return new PrinterPower(owner, source, amount);
    }

    public void OnWindSpeedZero(){

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

