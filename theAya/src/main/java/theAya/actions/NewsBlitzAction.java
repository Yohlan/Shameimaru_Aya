package theAya.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theAya.characters.TheAya;
import theAya.powers.NewspaperUpgradePower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsBlitzAction extends AbstractGameAction {
    public static String cameraText = "获得快照.";
    public static String cameraText2 = "消耗 .";
    public static String cameraText3 = "虚无 .";
    private final AbstractPlayer p;
    private boolean haveCard = false;
    private final String[] TEXT = {
            "选择一张牌并生成1张它的快照",
            "大新闻!",
            "获得快照.",
            "消耗 .",
            "虚无 ."
    };
    private final List<AbstractCard> cards = new ArrayList<>();

    public NewsBlitzAction(final AbstractPlayer p
                          ) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        this.haveCard = false;
    }

    @Override
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
                cards.add(card);
                haveCard = true;
        }
        if(haveCard) {
            Random random = new Random();
            AbstractCard c = cards.get(random.nextInt(cards.size()));
            cards.clear();
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
            int cost = c2.cost;
            if(p.hasPower(NewspaperUpgradePower.POWER_ID)){
                cost = c2.cost - p.getPower(NewspaperUpgradePower.POWER_ID).amount;
                cost = Math.max(cost,0);
            }
            //改变c的费用
            c2.setCostForTurn(cost);
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
            if (p.hand.size() >= 10) {
                p.hand.moveToExhaustPile(c2);
            } else {
                c2.isGlowing = true;
                p.hand.addToTop(c2);
                if (p instanceof TheAya)
                    TheAya.gainWindSpeed(1);
            }

        }
        isDone =true;
    }
}

