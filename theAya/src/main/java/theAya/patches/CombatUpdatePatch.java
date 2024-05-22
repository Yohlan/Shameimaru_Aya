package theAya.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theAya.AyaMod;
import theAya.actions.AyaCameraAction;
import theAya.characters.TheAya;

import theAya.vfx.FlexibleCalmParticleEffect;

public class CombatUpdatePatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class CombatUpdate {
        public static void Prefix() {
            if (AbstractDungeon.player instanceof TheAya) {
                TheAya.particleTimer -= Gdx.graphics.getDeltaTime();
                    if (TheAya.particleTimer < 0.0F) {
                        TheAya.particleTimer = 0.1f -MathUtils.clamp(TheAya.getWindSpeed(), 0F, 400F)*0.0002f;
                        AbstractDungeon.effectsQueue.add(new FlexibleCalmParticleEffect(AbstractDungeon.player));
                    }
                    AyaMod.windSpeedDisplayUnit.update();
            }
        }

    }
}
