package theAya.actions.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theAya.AyaMod;
import theAya.powers.FlightPower;

public class FlyingPotionAction extends AbstractGameAction {

    public FlyingPotionAction() {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE, true));
    }


    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new FlightPower(p, p, 3), 3));
        this.isDone = true;
    }
}
