package theAya.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Objects;

public class IndustrializationAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;

    public IndustrializationAction(final AbstractPlayer p,
                                   final boolean upgraded,
                                   final boolean freeToPlayOnce,
                                   final int energyOnUse) {
        this.freeToPlayOnce = false;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (upgraded) {
            ++effect;
        }
        if (effect > 0) {
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if(card.color == AbstractCard.CardColor.CURSE && card.isEthereal && card.exhaust && card.cost >= 0){
                    int newCost = Math.max(card.cost - effect, 0);
                    if(card.cost!= newCost){
                        card.costForTurn = newCost;
                    }
                }
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
