package theAya.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theAya.AyaMod;
import theAya.actions.NewsBlitzAction;
import theAya.actions.NewspaperAction;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makeRelicOutlinePath;
import static theAya.AyaMod.makeRelicPath;

public class Newspaper extends CustomRelic {

    // ID, images, text.
    public static final String ID = AyaMod.makeID("Newspaper");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("news.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("news.png"));

    public Newspaper() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new NewspaperAction(player));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
