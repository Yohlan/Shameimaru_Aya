package theAya.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theAya.AyaMod;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;

public class WindSpeedPanelPatch {
    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb")
    public static class PotionSackRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            if (AbstractDungeon.player instanceof TheAya) {
                AyaMod.windSpeedDisplayUnit.render(sb);
            }
        }
    }
}
