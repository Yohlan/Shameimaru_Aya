package theAya.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theAya.characters.TheAya;


public class HandIsFullPatch {
    // 与 风速 有关的代码
    @SpirePatch(clz = AbstractPlayer.class, method = "createHandIsFullDialog",paramtypez = {})
    public static class HandIsFull{
        public static void Prefix() {
            if(AbstractDungeon.player instanceof TheAya) {
                if (AbstractDungeon.player.hand.size() == 10) {
                    TheAya.gainWindSpeed(1);
                }
            }
        }

    }
}
