package theAya.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;


public abstract class AbstractGIFCard extends AbstractCard {

    public AbstractCard makeCopy() {
        try {
            return (AbstractCard)this.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException var2) {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for card: " + this.cardID);
        }
    }

    private GifAnimation gifAnimation;


    public AbstractGIFCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, "GIF/gif", "GIF/gif", cost, rawDescription, type, color, rarity, target);
        this.gifAnimation = new GifAnimation(img);
    }
    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        float drawX = this.current_x - 125.0F;
        float drawY = this.current_y - 23.0F;//this.current_y - 95.0F + 72.0F;
        gifAnimation.render(sb, drawX, drawY, 125.0F, 23.0F, 250.0F, 190.0F, this.drawScale * Settings.scale, this.drawScale * Settings.scale, this.angle, 0, 0, 250, 190, false, false);

    }


}
