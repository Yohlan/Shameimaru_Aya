package theAya.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theAya.characters.TheAya;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ViolentNewsAction extends AbstractGameAction {
    public static String cameraText = "获得快照.";
    public static String cameraText2 = "消耗 .";
    public static String cameraText3 = "虚无 .";
    private final AbstractPlayer p;
    private final int cost_up;
    private final AbstractMonster monster;
    private boolean haveCard = false;
    private final String[] TEXT = {
            "选择一张牌并生成1张它的快照",
            "大新闻!",
            "获得快照.",
            "消耗 .",
            "虚无 ."
    };
    private final List<AbstractCard> attackCards = new ArrayList<>();

    public ViolentNewsAction(final AbstractPlayer p,
                             final int cost_up,
                             final AbstractMonster monster) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        this.cost_up = cost_up;
        this.monster = monster;
        this.haveCard = false;
    }

    @Override
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.type == AbstractCard.CardType.ATTACK) {
                attackCards.add(card);
                haveCard = true;
            }
        }
        if(haveCard) {
            Random random = new Random();
            AbstractCard c = attackCards.get(random.nextInt(attackCards.size()));
            attackCards.clear();
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
            p.useCard(c2,monster,0);
        }
        isDone =true;
    }
}

