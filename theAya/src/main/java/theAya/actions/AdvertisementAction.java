package theAya.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class AdvertisementAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final int energyOnUse;
    private final boolean upgraded;
    private final int cost_up;
    private final String[] TEXT = {
            "选择一张牌并生成1张它的快照",
            "大新闻!",
            "获得快照.",
            "消耗 .",
            "虚无 ."
    };

    public AdvertisementAction(final AbstractPlayer p,
                               final boolean upgraded,
                               final boolean freeToPlayOnce,
                               final int energyOnUse,
                               final int cost_up) {
        this.freeToPlayOnce = false;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.cost_up = cost_up;
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
            while(effect>0) {
                AbstractDungeon.actionManager.addToBottom(new AyaCameraAction(AbstractDungeon.player, 1, TEXT, false, cost_up));
                effect--;
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
