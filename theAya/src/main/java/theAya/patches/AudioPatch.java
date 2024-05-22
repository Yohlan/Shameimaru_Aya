package theAya.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;

import java.util.HashMap;

public class AudioPatch {
    public static final String Karen_OnSelect = "Karen_OnSelect.ogg";
    public static final String Revue = "Revue.ogg";

    private static void load(SoundMaster instance, HashMap<String, Sfx> ___map, String key) {
        ___map.put(key, new Sfx("theAyaResources/sound/" + key, false));
    }

    @SpirePatch(clz = SoundMaster.class, method = "<ctor>")
    public static class SoundPatch {
        public static void Postfix(SoundMaster instance, HashMap<String, Sfx> ___map) {
            AudioPatch.load(instance, ___map, "MapleDance.ogg");
        }
    }
}