package theAya.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.AyaMod;
import theAya.util.TextureLoader;

import java.util.Iterator;

import static theAya.AyaMod.makeRelicOutlinePath;
import static theAya.AyaMod.makeRelicPath;

public class TenguMask extends CustomRelic {


    // ID, images, text.
    public static final String ID = AyaMod.makeID("TenguMask");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tengu.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tengu.png"));

    public TenguMask() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var1.next();
            this.addToBot(new RelicAboveCreatureAction(mo, this));
            addToBot(new TextAboveCreatureAction(mo, TextAboveCreatureAction.TextType.STUNNED));
            mo.setMove((byte) 4, AbstractMonster.Intent.STUN);
            mo.createIntent();
        }

    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
