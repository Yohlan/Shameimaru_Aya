package theAya.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theAya.characters.TheAya;
import theAya.powers.FlightPower;
import theAya.ui.WindSpeedDisplayUnit;
import theAya.vfx.FlexibleCalmParticleEffect;


public class TurnStartAndEndPatch
{
    @SpirePatch(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
    public static class EndTurn
    {
        public static void Prefix(AbstractCreature __instance) {
            if(__instance instanceof TheAya){
                AbstractPlayer p = AbstractDungeon.player;
                WindSpeedDisplayUnit.WindType windType = TheAya.windType;
                switch (windType){
                    case SOAR:
                        if(TheAya.getWindSpeed() >= 30){
                            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new FlightPower((AbstractCreature)p,p,1), 1));
                            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.ATTACK);
                        }
                        break;
                    case HEAL:
                        if(TheAya.getWindSpeed() >= 30){
                            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AddTemporaryHPAction((AbstractCreature)p, (AbstractCreature)p, 5));
                            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.SOAR);
                        }
                        break;
                    case ATTACK:
                        if(TheAya.getWindSpeed() >= 30){
                            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new StrengthPower((AbstractCreature) p, 1),1));
                            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.DEFEND);
                        }
                        break;
                    case DEFEND:
                        if(TheAya.getWindSpeed() >= 30){
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 15));
                            TheAya.changeWindType(WindSpeedDisplayUnit.WindType.HEAL);
                        }
                        break;
                }
            }
        }
    }
    @SpirePatch(clz = AbstractCreature.class, method = "applyStartOfTurnPostDrawPowers")
    public static class StartTurn
    {
        public static void Prefix(AbstractCreature __instance) {
                if(__instance instanceof TheAya){
                    int windSpeed = TheAya.getWindSpeed();
                    TheAya.loseWindSpeed(Math.min(windSpeed, 15));
            }
        }
    }
}
