package theAya.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theAya.characters.TheAya;

public class MapleLeafWaltzAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private int energyOnUse;
    private int damage;
    private boolean upgraded;
    private int magicNumber;

    public MapleLeafWaltzAction(final AbstractPlayer p,
                                final AbstractMonster m,
                                final boolean upgraded,
                                final boolean freeToPlayOnce,
                                final int energyOnUse,
                                final int damage,
                                final int magicNumber
    ) {
        this.freeToPlayOnce = false;
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        actionType = ActionType.DAMAGE;
        this.energyOnUse = energyOnUse;
        this.damage= damage;
        this.upgraded = upgraded;
        this.magicNumber = magicNumber;
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
            while(effect >0) {
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                TheAya.gainWindSpeed(magicNumber);
                effect--;
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
