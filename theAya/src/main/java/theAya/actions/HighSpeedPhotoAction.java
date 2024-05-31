package theAya.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import theAya.characters.TheAya;

import java.util.Random;


public class HighSpeedPhotoAction
        extends AbstractGameAction {
    public static String cameraText2 = "消耗 .";
    public static String cameraText3 = "虚无 .";
    private int cost_up = 0;
    private final boolean cost_zero;

    private int times = 1;
    private boolean isOk;


    public HighSpeedPhotoAction(AbstractCreature source, int amount,boolean cost_zero,int cost_up,int times) {
        this.isOk = false;
        setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.cost_zero = cost_zero;
        this.cost_up = cost_up;
        this.times = times;
        this.duration = 0.15f;
    }
    public void update() {
        if(AbstractDungeon.player.hand.isEmpty()){
            isDone = true;
            return;
        }
        if (this.duration == 0.5F) {
            if (AbstractDungeon.player instanceof TheAya) {
                TheAya.gainWindSpeed(1);
            }
            addToBot(new WaitAction(0.15F));
            tickDuration();
            return;

        }
        if(!isOk) {
            Random random = new Random();
            AbstractPlayer p = AbstractDungeon.player;
            int size = p.hand.size();
            int index = random.nextInt(size);
            AbstractCard c = p.hand.group.get(index);
            //复制卡牌
            AbstractCard c2 = c.makeStatEquivalentCopy();
            if (!c2.isEthereal) {
                c2.isEthereal = true;
                c2.rawDescription += " NL " + cameraText3;
            }
            if (!c2.exhaust) {
                c2.exhaust = true;
                c2.rawDescription += " NL " + cameraText2;
            }
            if (c.upgraded) {
                c2.upgrade();
            }
            //改变c的费用
            if (cost_zero)
                c2.setCostForTurn(0);
            else
                c2.setCostForTurn(c2.cost + cost_up);
            //让c颜色改变
            c2.color = AbstractCard.CardColor.CURSE;
            c2.glowColor = CardHelper.getColor(255, 51, 51);
            //获得临时卡牌
            c2.initializeDescription();
            AbstractDungeon.player.limbo.addToBottom(c2);
            c2.current_x = Settings.WIDTH / 2.0F;
            c2.current_y = Settings.HEIGHT / 2.0F;
            c2.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            c2.target_y = Settings.HEIGHT / 2.0F;
            c2.targetAngle = 0.0F;
            c2.drawScale = 0.12F;
            if (c2.selfRetain || c2.isInnate) {
                c2.isInnate = false;
                c2.selfRetain = false;
            }
            c.isGlowing = true;
//            p.hand.addToTop(c);
            Compare(c2, p);
            while (times > 0) {
                AbstractCard c3 = c.makeStatEquivalentCopy();
                if (!c3.isEthereal) {
                    c3.isEthereal = true;
                    c3.rawDescription += " NL " + cameraText3;
                }
                if (!c3.exhaust) {
                    c3.exhaust = true;
                    c3.rawDescription += " NL " + cameraText2;
                }
                if (c.upgraded) {
                    c3.upgrade();
                }
                //改变c的费用
                if (cost_zero)
                    c3.setCostForTurn(0);
                else
                    c3.setCostForTurn(c3.cost + cost_up);
                //让c颜色改变
                c3.color = AbstractCard.CardColor.CURSE;
                c3.glowColor = CardHelper.getColor(255, 51, 51);
                //获得临时卡牌
                c3.initializeDescription();
                AbstractDungeon.player.limbo.addToBottom(c3);
                c3.current_x = Settings.WIDTH / 2.0F;
                c3.current_y = Settings.HEIGHT / 2.0F;
                c3.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                c3.target_y = Settings.HEIGHT / 2.0F;
                c3.targetAngle = 0.0F;
                c3.drawScale = 0.12F;
                if (c3.selfRetain || c3.isInnate) {
                    c3.isInnate = false;
                    c3.selfRetain = false;
                }
                Compare(c3, p);
            }
            isOk = true;
        }
        tickDuration();
    }
    private void Compare(AbstractCard card,AbstractPlayer p) {
        if (p.hand.size() >= 10) {
            p.hand.moveToExhaustPile(card);
        } else {
            card.isGlowing = true;
            p.hand.addToTop(card);
            if (p instanceof TheAya)
                TheAya.gainWindSpeed(1);
        }
        times--;
    }
}




