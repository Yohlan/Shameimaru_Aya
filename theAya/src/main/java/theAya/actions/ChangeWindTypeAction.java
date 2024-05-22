//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package theAya.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import theAya.cards.AbstractDefaultCard;
import theAya.characters.TheAya;
import theAya.powers.ShiftingTenguPower;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.vfx.FlexibleCalmParticleEffect;

import java.util.Iterator;
import java.util.UUID;

public class ChangeWindTypeAction extends AbstractGameAction {

    public ChangeWindTypeAction(WindSpeedDisplayUnit.WindType windType) {
        if(AbstractDungeon.player.hasPower(ShiftingTenguPower.POWER_ID)){
            TheAya.gainWindSpeed(AbstractDungeon.player.getPower(ShiftingTenguPower.POWER_ID).amount * 10);
        }
        TheAya.windType = windType;
        WindSpeedDisplayUnit.changeWindType(windType);
        FlexibleCalmParticleEffect.changeWindType(windType);
    }

    public void update() {
        this.isDone = true;
    }
}
