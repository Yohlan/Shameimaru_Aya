package theAya.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theAya.AyaMod;
import theAya.powers.FlightPower;
import theAya.util.TextureLoader;

import static theAya.AyaMod.makeRelicOutlinePath;
import static theAya.AyaMod.makeRelicPath;

public class Wing extends CustomRelic {


    // ID, images, text.
    public static final String ID = AyaMod.makeID("Wing");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("wings.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("wings.png"));

    public Wing() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        this.addToBot(new RelicAboveCreatureAction(player, this));
        addToBot(new ApplyPowerAction(player, player, new FlightPower(player, player, 3), 3));

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
