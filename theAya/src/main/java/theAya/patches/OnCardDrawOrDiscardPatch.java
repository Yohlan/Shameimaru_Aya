package theAya.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theAya.characters.TheAya;
import theAya.ui.WindSpeedDisplayUnit;

public class OnCardDrawOrDiscardPatch {
    // 与 风速 有关的代码
    @SpirePatch(clz = AbstractPlayer.class, method = "onCardDrawOrDiscard")
    public static class OnCardDrawOrDiscard {
        public static void Prefix(AbstractPlayer __instance) {
            if(__instance instanceof TheAya) {
                WindSpeedDisplayUnit.show();
                TheAya.gainWindSpeed(1);
            }
        }
    }
}
